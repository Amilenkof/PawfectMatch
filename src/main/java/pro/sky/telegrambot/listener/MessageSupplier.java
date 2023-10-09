package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.SchemaRepository;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.ShelterService;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс который принимает Update, по нему подбирает клавиатуру,
 * которую нужно вернуть и формирует ответ для TelegrammListener
 */

@Service
@Slf4j
public class MessageSupplier {
    private final KeyBoardService keyBoardService;

    private final AnswerProducer answerProducer;

    public MessageSupplier(KeyBoardService keyBoardService, AnswerProducer answerProducer) {
        this.keyBoardService = keyBoardService;
        this.answerProducer = answerProducer;
    }

    /**
     * Метод читает команду и формирует на нее ответ подкладывая нужную клавиатуру
     * PARAMS = Update update
     */
    public List<AbstractSendRequest<? extends AbstractSendRequest<?>>> executeResponse(Update update) {

        log.debug("Вызван метод executeResponse в классе MessageConsumer");
        String command = update.message().text();
        log.debug("Получена команда = {}", command);
        List<AbstractSendRequest<?>> messageList = new ArrayList<>();
        switch (command) {
            case ("/start"):
            case ("Вернуться в главное меню"):
                messageList.add(keyBoardService.mainMenuKeyboard(update));
                return messageList;
            case ("Приют для собак"):
                messageList.add(keyBoardService.dogShelterKeyboard(update));
                return messageList;
            case ("Приют для кошек"):
                messageList.add(keyBoardService.catShelterKeyboard(update));
                return messageList;
            case ("Инфо о собачьем приюте"):
                messageList.add(keyBoardService.aboutDogShelterKeyboard(update));
                return messageList;
            case ("Инфо о кошачьем приюте"):
                messageList.add(keyBoardService.aboutCatShelterKeyboard(update));
                return messageList;
            case ("Как взять собаку"):
                messageList.add(keyBoardService.howTakeDogKeyboard(update));
                return messageList;
            case ("Как взять кошку"):
                messageList.add(keyBoardService.howTakeCatKeyboard(update));
                return messageList;
            case("Позвать волонтера-Кошачий приют"):
                return keyBoardService.callVolunteer(update, "cat");//todo нужно ли предусматривать расширение в будущем , когда несколько приютов будут с одним типом животных?
            case("Позвать волонтера-Собачий приют"):
                return keyBoardService.callVolunteer(update, "dog");
            case ("Как проехать к приюту для собак"):
                messageList.add(answerProducer.getSchema(update, "dog"));
                return messageList;
            case ("Как проехать к приюту для кошек"):
                 messageList.add(answerProducer.getSchema(update, "cat"));
                return messageList;



        }
        log.debug("Кейсы не выбраны, метод зашел в дефолтный блок");
        SendMessage messageDefault = new SendMessage(update.message().chat().id(), "Не понял, повторите");
        return List.of(messageDefault);//todo возможно стоит изменить варианты ответа
    }
}



