package pro.sky.telegrambot.service;


import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Feedback;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Schema;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.SchemaRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private final FeedbackService feedbackService;
    private final ReportService reportService;
    @Autowired
    SchemaRepository repository;

    public AnswerProducer(SchemaService schemaService, ShelterService shelterService, RecommendationService recommendationService, FeedbackService feedbackService, ReportService reportService) {
        this.schemaService = schemaService;
        this.shelterService = shelterService;
        this.recommendationService = recommendationService;
        this.feedbackService = feedbackService;
        this.reportService = reportService;
    }


    /**
     * Метод проверяет если ли приют по указанному типу животных
     * Params = String animalType- вид животного
     * Return = boolean
     */
    public boolean checkShelter(String animalType) {
        log.debug("Вызван метод AnswerProducer.checkShelter , animalType={}", animalType);
        Optional<Shelter> optionalShelter = shelterService.findShelterByAnimalType(animalType);
        if (optionalShelter.isEmpty()) {
            log.debug("Приют не найден, клиенту будет направлен wrongMessage");
        }
        return optionalShelter.isEmpty();
    }

    /**
     * Метод проверяет если ли схема для приюта по указанному типу животных
     * Params = String animalType- вид животного
     * Return = boolean
     */
    public boolean checkSchema(String animalType) {
        log.debug("Вызван метод AnswerProducer.checkSchema , animalType={}", animalType);
        Shelter shelter = shelterService.findShelterByAnimalType(animalType).get();
        Optional<Schema> optionalSchema = schemaService.findByShelter_id(shelter.getId());
        if (optionalSchema.isEmpty()) {
            log.debug("Схема проезда не найдена, клиенту будет направлен wrongMessage");
        }
        return optionalSchema.isEmpty();
    }

    /**
     * Метод генерирует ответ на запрос схемы проезда
     * Params = Update update -данные полученные от пользователя, String AnimalType- тип приюта для которого нужно вернуть схему
     * Return = SendMessage -если схема не найдена
     * Return = SendPhoto- если схема найдена, возвращаем схему
     */
    public AbstractSendRequest<? extends AbstractSendRequest<?>> getSchema(Update update, String animalType) {
        log.debug("Вызван метод AnswerProducer.getSchema,animalType={}", animalType);
        Long chatId = update.message().chat().id();
        if (checkShelter(animalType) || checkSchema(animalType)) {//todo не нравится, проверки есть и потом приходится опять то же самое делать
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
     * Return = SendMessage -c описанием приюта если он найден, или стандарная фраза, что приют не найден(вместо Exception)
     */
    public SendMessage getInfoAboutShelter(Update update, String animalType) {
        log.debug("Вызван метод AnswerProducer.getInfoAboutShelter,animalType={}", animalType);
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
     * Return = SendMessage -c описанием приюта если он найден, или стандарная фраза, что приют не найден(вместо Exception)
     */
    public SendMessage getContactsSecurityShelter(Update update, String animalType) {
        log.debug("Вызван метод AnswerProducer.getContactsSecurityShelter, animalType={}", animalType);
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
     * Return = SendMessage -c описанием приюта если он найден, или стандарная фраза, что приют не найден(вместо Exception)
     */
    public SendMessage getSafetyByShelter(Update update, String animalType) {
        log.debug("Вызван метод AnswerProducer.getSafetyByShelter,animalType={}", animalType);
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
     * Return = SendMessage -сформированное из информации в бд по указанному запросу, или стандарная фраза "Не понял,дайте попробуем еще раз,вернитесь в главное меню"
     */
    public SendMessage findRecommendation(Update update, String title, String animalType) {
        log.debug("Вызван метод AnswerProducer.findRecommendation, animalType={}", animalType);
        Long chatId = update.message().chat().id();
        Optional<String> firstByTitle = recommendationService.getFirstByTitle(title, animalType);
        return new SendMessage(chatId, firstByTitle.orElse("Не понял,дайте попробуем еще раз,вернитесь в главное меню"));
    }

    /**
     * Метод отправляет клиенту форму сообщения для дальнейшей связи с ним
     * Params = Update update -данные от пользователя
     * Return = SendMessage c формой сообщения
     */
    public SendMessage sendFeedbackForm(Update update) {
        log.debug("Вызван метод AnswerProducer.sendFeedbackForm");
        Long chatId = update.message().chat().id();
        return new SendMessage(chatId, "Пожалуйста пришлите Ваши контакты в форме:Иванов,Иван,mail@mail.ru,+79271234567");
    }

    /**
     * Метод формирует ответ пользователю на попытку оставить обратную связь
     *
     * @param Update
     * @return new SendMessage
     */

    public SendMessage addFeedback(Update update) {
        log.debug("Вызван метод AnswerProducer.addFeedback");
        Long chatId = update.message().chat().id();
        SendMessage wrongMessage = new SendMessage(chatId, "Не удалось добавить сообщение, попробуйте еще раз," +
                                                           " обратите внимание на пробелы и запятые в указанной форме");
        Optional<Feedback> feedback = Optional.empty();
        try {
            feedback = feedbackService.addFeedback(update.message().text());
        } catch (IllegalStateException | IndexOutOfBoundsException e) {
            return wrongMessage;
        }
        return feedback.isEmpty() ? wrongMessage :
                new SendMessage(chatId, "Ваше сообщение передано волонтерам, ожидайте ответа");
    }

    /**
     * Метод отправляет клиенту форму для отправки отчета о животном для дальнейшей связи с ним
     *
     * @param Update update -данные от пользователя
     * @return AbstractSendRequest<? extends AbstractSendRequest < ?>>
     */
    public AbstractSendRequest<? extends AbstractSendRequest<?>> sendReportForm(Update update) {
        log.debug("Вызван метод AnswerProducer.sendReportForm");
        Report testReport = reportService.getTestReport();
        byte[] photo = testReport.getPhoto();//верный код



//        byte[] data = schemaService.findByShelter_id(1L).get().getData();//todo временная заглушка,пока картинку дергаем из схем проезда- тк туда легко добавить
        String reportCaption = String.format("Просим прислать отчет о Вашем питомце как в форме ниже: \n1-%s\n2-%s\n3-%s", testReport.getFood(), testReport.getHealth(), testReport.getBehaviour());
        SendPhoto sendPhoto = new SendPhoto(update.message().chat().id(), photo).caption(reportCaption);
        return sendPhoto;
    }

}




