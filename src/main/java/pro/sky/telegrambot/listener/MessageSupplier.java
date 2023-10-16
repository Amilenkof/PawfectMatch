package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.CallBackHandler;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс который принимает Update, по нему подбирает клавиатуру или вызывает методы AnswerProduser, который формирует ответы
 * которые будут направлены в TelegrammListener Для отправки пользователю
 */

@Service
@Slf4j
public class MessageSupplier {
    private final KeyBoardService keyBoardService;

    private final AnswerProducer answerProducer;
    private final CallBackHandler callBackHandler;

    private final String ANIMAL_CAT = "cat";
    private final String ANIMAL_DOG = "dog";
    private String currentAnimal = "";
    private boolean isFeedback;
    private boolean isReport;


    public MessageSupplier(KeyBoardService keyBoardService, AnswerProducer answerProducer, CallBackHandler callBackHandler) {
        this.keyBoardService = keyBoardService;
        this.answerProducer = answerProducer;
        this.callBackHandler = callBackHandler;
    }

    /**
     * Метод читает команду и формирует на нее ответ подкладывая нужную клавиатуру
     * PARAMS = Update update
     */
    public List<AbstractSendRequest<? extends AbstractSendRequest<?>>> executeResponse(Update update) {

        log.debug("Вызван метод executeResponse в классе MessageConsumer");
        if (update.callbackQuery()!=null){
           return List.of(callBackHandler.handle(update));

        }
        String command = update.message().text();//todo если переслать сообщение текст будет = null и приложуха ляжет..
        log.debug("Получена команда = {}", command);

//        if ((command != null || update.message().caption() != null) && !(isFeedback || isReport)) {
//            return List.of(answerProducer.wrongAnswer(update));
//        }

        if (isFeedback) {
            log.debug("Получен feedback");
            isFeedback = false;
            return List.of(answerProducer.addFeedback(update));
        }
        if (isReport) {
            log.debug("Получен report");
            isReport = false;
            return List.of(answerProducer.addReport(update));
        }
        var answersForTextCommand = new ArrayList<>(messageForTextCommand(command, update));
        if (!answersForTextCommand.isEmpty()){
            return answersForTextCommand;
        }
        SendMessage recommendation = answerProducer.findRecommendation(update, command, currentAnimal);
        log.debug("Кейсы не выбраны, метод зашел в дефолтный блок");
        return List.of(recommendation);
    }

    public List<AbstractSendRequest<?>> messageForTextCommand(String command, Update update) {
        if (command==null){
            return new ArrayList<>();
        }
        List<AbstractSendRequest<?>> messageList = new ArrayList<>();

        switch (command) {
            case ("/start"), ("Вернуться в главное меню") -> {
                messageList.add(keyBoardService.mainMenuKeyboard(update));
                return messageList;
            }
            case ("Приют для собак") -> {
                currentAnimal = ANIMAL_DOG;
                messageList.add(keyBoardService.dogShelterKeyboard(update));
                return messageList;
            }
            case ("Приют для кошек") -> {
                currentAnimal = ANIMAL_CAT;
                messageList.add(keyBoardService.catShelterKeyboard(update));
                return messageList;
            }
            case ("Как взять животное") -> {
                if (currentAnimal.equals(ANIMAL_DOG))
                    messageList.add(keyBoardService.howTakeDogKeyboard(update));
                else messageList.add(keyBoardService.howTakeCatKeyboard(update));
                return messageList;
            }
            case ("Позвать волонтера") -> {
                return keyBoardService.callVolunteer(update, currentAnimal);
            }
            case ("Как проехать к приюту") -> {
                messageList.add(answerProducer.getSchema(update, currentAnimal));
                return messageList;
            }
            case ("Инфо о приюте") -> {
                messageList.add(answerProducer.getInfoAboutShelter(update, currentAnimal));
                return messageList;
            }
            case ("О приюте") -> {
                if (currentAnimal.equals(ANIMAL_DOG))
                    messageList.add(keyBoardService.aboutCatShelterKeyboard(update));
                else messageList.add(keyBoardService.aboutDogShelterKeyboard(update));
                return messageList;
            }
            case ("Отправить отчет") -> {
                isReport = true;
                messageList.add(answerProducer.sendReportForm(update));

                return messageList;
            }
            case ("Контактные данные охраны приюта") -> {
                messageList.add(answerProducer.getContactsSecurityShelter(update, currentAnimal));
                return messageList;
            }
            case ("Техника безопасности на территории приюта") -> {
                messageList.add(answerProducer.getSafetyByShelter(update, currentAnimal));
                return messageList;
            }
            case ("Оставить контакты для связи") -> {
                isFeedback = true;
                System.out.println("isFeedback = " + isFeedback);
                log.debug("isFeedback = {}", isFeedback);
                messageList.add(answerProducer.sendFeedbackForm(update));
                return messageList;
            }

        }
        return messageList;
    }


}




