package pro.sky.telegrambot.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.Schema;
import pro.sky.telegrambot.service.SchemaService;

import java.io.IOException;
//todo Как класс будет закончен сделать swagger документацию на api

@RestController
@RequestMapping("/schema")
@Slf4j
public class SchemaController {
    public SchemaController(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    private final SchemaService schemaService;

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

    public ResponseEntity<Schema> addSchema(@RequestBody MultipartFile file,
                                            @RequestParam Long shelterId) throws IOException {
        Schema schema;
        if (file.getSize()>1024*1000*2){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        log.debug("Вызван метод SchemaController.addSchema,file,shelterId={}",shelterId);
             schema = schemaService.addSchema(file, shelterId);// todo ошибка не обработана
//        try {
//             schema = schemaService.addSchema(file, shelterId);
//        }catch (IOException e){
//            return ResponseEntity.status(HttpStatusCode.valueOf(422)).build();
//        }
        return ResponseEntity.ok(schema);
    }
    /**
     * Есть есть попытка создать приют с типом животных, который уже заведен ранее этим методом ловим ошибку и возвращаем статус 406- NOT_ACCEPTABLE
     */
    @ExceptionHandler(IOException.class)//todo Как заставить работать ? хочу вернуть строку в теле ответа
    public ResponseEntity<String> handleException(HttpServletRequest request, IOException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(422)).build();
    }
}


