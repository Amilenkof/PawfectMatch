package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.keyboards.KeyBoardService;
import pro.sky.telegrambot.exceptions.UnkownCommandExeptions;
/**Класс который принимает Update, по нему подбирает клавиатуру, которую нужно вернуть и формирует ответ для TelegrammListener */
@Service
@Slf4j
public class MessageConsumer {
    private final KeyBoardService keyBoardService;


    public MessageConsumer(KeyBoardService keyBoardService) {
        this.keyBoardService = keyBoardService;

    }
/**Метод читает команду и формирует на нее ответ
 * Параметры = Update update
 * */
    public SendMessage executeResponse(Update update) {
        log.debug("Вызван метод executeResponse в классе MessageConsumer");
        String command = update.message().text();
        log.debug("Получена команда = {}",command);
        switch (command) {
            case ("/start"):
            case ("Вернуться в главное меню"):
                return keyBoardService.mainMenuKeyboard(update);
            case ("Приют для собак"):
                return keyBoardService.dogShelterKeyboard(update);
            case ("Приют для кошек"):
                return keyBoardService.catShelterKeyboard(update);
            case ("Инфо о собачьем приюте"):
                return keyBoardService.aboutDogShelterKeyboard(update);
            case ("Инфо о кошачьем приюте"):
                return keyBoardService.aboutCatShelterKeyboard(update);
            case ("Как взять собаку"):
                return keyBoardService.howTakeDogKeyboard(update);
            case ("Как взять кошку"):
                return keyBoardService.howTakeCatKeyboard(update);
            case ("Позвать волонтера"):
               return keyBoardService.callvolunteer(update);


        }
        log.debug("Кейсы не выбраны, метод зашел в дефолтный блок");

        return new SendMessage(update.message().chat().id(), "Не понял, повторите");
    }
}



