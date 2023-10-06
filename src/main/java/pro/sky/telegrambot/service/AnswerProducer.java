package pro.sky.telegrambot.service;


import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractMultipartRequest;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Schema;
import pro.sky.telegrambot.model.Shelter;

import java.util.Optional;

/**
 * Класс обработывает логику генерации ответов на комманды и передает ответы в MESSAGESUPPLIER
 */
@Service
@Slf4j
public class AnswerProducer {
    private final SchemaService schemaService;
    private final ShelterService shelterService;

    public AnswerProducer(SchemaService schemaService, ShelterService shelterService) {
        this.schemaService = schemaService;
        this.shelterService = shelterService;
    }

/**Метод*///todo
    public AbstractSendRequest<?> getSchema(Update update, String animalType) {
        log.debug("Вызван метод AnswerProducer.getSchema, update={},animalType={}",update,animalType);
        Optional<Shelter> optionalShelter = shelterService.findShelterByAnimalType(animalType);
        Long chatId = update.message().chat().id();
        if (optionalShelter.isEmpty()) {
            log.debug("Приют не найден, клиенту будет направлен wrongMessage");
            return new SendMessage(chatId, "Извините, схема проезда не найдена");}
        Shelter shelter = optionalShelter.get();
        Optional<Schema> optionalSchema = schemaService.findByShelter_id(shelter.getId());
        if (optionalSchema.isEmpty()){
            log.debug("Схема проезда не найдена, клиенту будет направлен wrongMessage");
            return new SendMessage(chatId, "Извините, схема проезда не найдена");}
        byte[] data = optionalSchema.get().getData();
        log.debug("Все ок,направляем схему клиенту");
        return new SendPhoto(chatId, data);
    }

}
