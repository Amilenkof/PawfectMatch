package pro.sky.telegrambot.service;


import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractMultipartRequest;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Recommendation;
import pro.sky.telegrambot.model.Schema;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.RecommendationRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Класс обрабатывает логику генерации ответов на комманды и передает ответы в MESSAGESUPPLIER
 */
@Service
@Slf4j
public class AnswerProducer<T extends AbstractSendRequest> {
    private final SchemaService schemaService;
    private final ShelterService shelterService;
    private final RecommendationService recommendationService;

    public AnswerProducer(SchemaService schemaService, ShelterService shelterService, RecommendationService recommendationService) {
        this.schemaService = schemaService;
        this.shelterService = shelterService;
        this.recommendationService = recommendationService;
    }


    /**
     * Метод проверяет если ли приют по указанному типу животных
     * Params = String animalType- вид животного
     * Return boolean
     */
    public boolean checkShelter(String animalType) {
        Optional<Shelter> optionalShelter = shelterService.findShelterByAnimalType(animalType);
        if (optionalShelter.isEmpty()) {
            log.debug("Приют не найден, клиенту будет направлен wrongMessage");
        }
        return optionalShelter.isEmpty();
    }

    /**
     * Метод проверяет если ли схема для приюта по указанному типу животных
     * Params = String animalType- вид животного
     * Return boolean
     */
    public boolean checkSchema(String animalType) {
        Shelter shelter = shelterService.findShelterByAnimalType(animalType).get();
        Optional<Schema> optionalSchema = schemaService.findByShelter_id(shelter.getId());
        return optionalSchema.isEmpty();
    }

    /**
     * Метод генерирует ответ на запрос схемы проезда
     * Params = Update update -данные полученные от пользователя, String AnimalType- тип приюта для которого нужно вернуть схему
     * Return= SendMessage -если схема не найдена
     * Return SendPhoto- если схема найдена, возвращаем схему
     */
    public AbstractSendRequest<? extends AbstractSendRequest<?>> getSchema(Update update, String animalType) {
        log.debug("Вызван метод AnswerProducer.getSchema, update={},animalType={}", update, animalType);
        Long chatId = update.message().chat().id();
        if (checkShelter(animalType) && checkSchema(animalType)) {
            return new SendMessage(chatId, "Извините, схема проезда не найдена");
        }
        Shelter shelter = shelterService.findShelterByAnimalType(animalType).get();
        Optional<Schema> optionalSchema = schemaService.findByShelter_id(shelter.getId());
        byte[] data = optionalSchema.get().getData();
        log.debug("Все ок,направляем схему клиенту");
        return new SendPhoto(chatId, data);
    }

    /**
     * Метод получает описание приюта,формирует сообщение для отправки пользователю
     * Params = Update update -данные полученные от пользователя , String AnimalType- тип приюта для которого нужно вернуть схему
     * Return SendMessage -c описанием приюта если он найден, или стандарная фраза, что приют не найден(вместо Exception)
     */
    public SendMessage getInfoAboutShelter(Update update, String animalType) {
        Long chatId = update.message().chat().id();
        if (checkShelter(animalType)) {
            return new SendMessage(chatId, "Извините, указанный приют не найден");
        }
        Shelter shelter = shelterService.findShelterByAnimalType(animalType).get();
        String description = shelter.getDescription() + " \nНаш приют находится по адресу: " + shelter.getAddress() + "\nВремя работы : " + shelter.getTiming();
        return new SendMessage(chatId, description);
    }

    /**
     * Метод получает контактные данные охраны приюта,формирует сообщение для отправки пользователю
     * Params = Update update -данные полученные от пользователя , String AnimalType- тип приюта для которого нужно вернуть схему
     * Return SendMessage -c описанием приюта если он найден, или стандарная фраза, что приют не найден(вместо Exception)
     */
    public SendMessage getContactsSecurityShelter(Update update, String animalType) {
        Long chatId = update.message().chat().id();
        if (checkShelter(animalType)) {
            return new SendMessage(chatId, "Извините, указанный приют не найден");
        }
        Shelter shelter = shelterService.findShelterByAnimalType(animalType).get();
        return new SendMessage(chatId, shelter.getContractsSecurity());
    }

    /**
     * Метод получает информацию о технике безопастности на терририи приюта,формирует сообщение для отправки пользователю
     * Params = Update update -данные полученные от пользователя, String AnimalType- тип приюта для которого нужно вернуть схему
     * Return SendMessage -c описанием приюта если он найден, или стандарная фраза, что приют не найден(вместо Exception)
     */
    public SendMessage getSafetyByShelter(Update update, String animalType) {
        Long chatId = update.message().chat().id();
        if (checkShelter(animalType)) {
            return new SendMessage(chatId, "Извините, указанный приют не найден");
        }
        Shelter shelter = shelterService.findShelterByAnimalType(animalType).get();
        return new SendMessage(chatId, shelter.getSafety());
    }

    /**
     * Метод получает рекомендации по обращению с животными из БД.Столбец title в таблице recommendations, должен соотвествовать командам в телеграмм
     * Params = Update update -данные полученные от пользователя, String title- название команды или название рекоммендации в БД
     * Return SendMessage -сформированное из информации в бд по указанному запросу, или стандарная фраза "Не понял,дайте попробуем еще раз,вернитесь в главное меню"
     */
    public SendMessage findRecommendation(Update update, String title,String animalType) {
        Long chatId = update.message().chat().id();
        Optional<String> firstByTitle = recommendationService.getFirstByTitle(title,animalType);
        return new SendMessage(chatId, firstByTitle.orElse("Не понял,дайте попробуем еще раз,вернитесь в главное меню"));
    }

}
