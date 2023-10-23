package pro.sky.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.Picture;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.PictureRepository;
import pro.sky.telegrambot.repository.ShelterRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис реализует методы для работы со схемами проезда
 */
@Service
@Slf4j
public class PictureService {
    private final PictureRepository pictureRepository;
    private final ShelterRepository shelterRepository;


    public PictureService(PictureRepository pictureRepository, ShelterRepository shelterRepository) {
        this.pictureRepository = pictureRepository;
        this.shelterRepository = shelterRepository;
    }

    /**
     * Метод реализует логику добавления в БД схемы проезда
     * PARAMS file- файл со схемой,
     * Long ShelterId - Id приюта для которого добавляется схема
     * Throws- IOException -
     * <p>
     * Описание работы:создает путь к месту где будут храниться схемы, получает расширение переданного файла, формирует название нового файла
     * (ID приюта+ расширение), ищет схему для заданного приюта, если такого нет выбрасывает ShelterNotFoundException,
     * билдит сущность, сохраняет ее в БД
     */
    @Transactional
    public Picture addSchema(MultipartFile file, Long shelterId) throws IOException {
        log.debug("Вызван метод SchemaService.addSchema, file, shelterId={}", shelterId);
        Optional<Shelter> optionalShelter = shelterRepository.findById(shelterId);
        if (optionalShelter.isEmpty()) {
            log.debug("Не найден указанный приют");
            throw new ShelterNotFoundException("Не удается загрузить схема проезда к приюту, тк указанный приют не найден");
        }
        Path pathToFile = Path.of("/src/main/resources/defaultReportPicture.jpg", shelterId + "." + getExtensions((file.getOriginalFilename())));
        log.debug("pathToFile={}", pathToFile);
        Files.createDirectories(pathToFile.getParent());
        Files.deleteIfExists(pathToFile);
        try (BufferedInputStream is = new BufferedInputStream(file.getInputStream(), 1024);
             BufferedOutputStream os = new BufferedOutputStream(Files.newOutputStream(pathToFile, CREATE_NEW), 1024)) {
            long l = is.transferTo(os);
            log.debug("Было загружено {} байт", l);
        }
        Picture picture = pictureRepository.findSchemaByShelter_Id(shelterId).orElse(new Picture());
        log.debug("Получена с БД schema={}(hashcode)", picture.hashCode());
        picture = new Picture().builder()
                .filePath(pathToFile.toString())
                .fileSize(file.getSize())
                .shelter(optionalShelter.get())
                .data(file.getBytes())
                .mediaType(file.getContentType())
                .build();
        log.debug("В итоге сформирована schema={}(hashcode)", picture.hashCode());
        return pictureRepository.save(picture);


    }

    /**
     * Метод получает расширение файла
     * PARAMS= String s (название файла)
     */
    private String getExtensions(String s) {
        log.info("Вызван метод SchemaService.getExtensions");
        return s.substring(s.lastIndexOf(".") + 1);
    }

    /**
     * Метод находит Schema в БД по ID
     * Params Long id - id схемы проезда
     * Return Optional<Schema>
     */
    @Transactional(readOnly = true)
    public Optional<Picture> findById(Long id) {
        return pictureRepository.findById(id);
    }

    /**
     * Метод находит Schema в БД по ShelterID
     * Params Long id - id приюта
     * Return Optional<Schema>
     */
    @Transactional(readOnly = true)
    public Optional<Picture> findByShelter_id(Long shelterId) {
        return pictureRepository.findSchemaByShelter_Id(shelterId);
    }
}
