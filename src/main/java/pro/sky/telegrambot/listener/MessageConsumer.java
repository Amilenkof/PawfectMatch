package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс который принимает Update, по нему подбирает клавиатуру,
 * которую нужно вернуть и формирует ответ для TelegrammListener
 */

@Service
@Slf4j
public class MessageConsumer {
    private final KeyBoardService keyBoardService;


    public MessageConsumer(KeyBoardService keyBoardService) {
        this.keyBoardService = keyBoardService;

    }

    /**
     * Метод читает команду и формирует на нее ответ подкладывая нужную клавиатуру
     * Параметры = Update update
     */
    public List<SendMessage> executeResponse(Update update) {

        log.debug("Вызван метод executeResponse в классе MessageConsumer");
        String command = update.message().text();
        log.debug("Получена команда = {}", command);
        List<SendMessage> messageList = new ArrayList<>();
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
                return keyBoardService.callVolunteer(update, "cat");
            case("Позвать волонтера-Собачий приют"):
                return keyBoardService.callVolunteer(update, "dog");

//            case ("Позвать волонтера"):
//               return keyBoardService.callVolunteer(update);
//            case ("О кошачьем приюте"):
//
//                return keyBoardService.howTakeCatKeyboard(update);


        }
        log.debug("Кейсы не выбраны, метод зашел в дефолтный блок");
        SendMessage messageDefault = new SendMessage(update.message().chat().id(), "Не понял, повторите");
        return List.of(messageDefault);//todo возможно стоит изменить варианты ответа
    }
}



