package pro.sky.telegrambot.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.Picture;
import pro.sky.telegrambot.service.PictureService;

import java.io.IOException;
//todo Как класс будет закончен сделать swagger документацию на api

@RestController
@RequestMapping("/schema")
@Slf4j
public class PictureController {
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    private final PictureService pictureService;

/**Метод используется для загрузки файла со схемой проезда к приюту
 * PARAMS= file- файл со схемой,
 * shelterID- id приюта для которого загружается схема
 *
 * HTTP статусы :
 * 404- если переданный файл слишком большой
 * 422- если в процессе добавления в БД возникли проблемы
 * 200- если схема успешно добавлена в БД
 * 500- ошибка на стороне сервера*///PSQLException
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<Picture> addSchema(@RequestBody MultipartFile file,
                                             @RequestParam Long shelterId) throws IOException {
        Picture picture;
        if (file.getSize()>1024*1000*2){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        log.debug("Вызван метод SchemaController.addSchema,file,shelterId={}",shelterId);
             picture = pictureService.addSchema(file, shelterId);// todo ошибка не обработана
//        try {
//             schema = schemaService.addSchema(file, shelterId);
//        }catch (IOException e){
//            return ResponseEntity.status(HttpStatusCode.valueOf(422)).build();
//        }
        return ResponseEntity.ok(picture);
    }
    /**
     * Есть есть попытка создать приют с типом животных, который уже заведен ранее этим методом ловим ошибку и возвращаем статус 406- NOT_ACCEPTABLE
     */
    @ExceptionHandler(ShelterNotFoundException.class)//todo Как заставить работать ? хочу вернуть строку в теле ответа
    public ResponseEntity<String> handleException(HttpServletRequest request, ShelterNotFoundException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(422)).build();
    }
}


