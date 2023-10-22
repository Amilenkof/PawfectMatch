package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.CallBackHandler;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MessageSupplierTests {
    @Mock
    private KeyBoardService keyBoardService;
    @Mock
    private AnswerProducer answerProducer;
    @Mock
    private CallBackHandler callBackHandler;
    @InjectMocks
    private MessageSupplier messageSupplier;
    private final String json = "{\"update_id\":513378858,\"message\":{\"message_id\":3543,\"from\":{\"id\":1107343760,\"is_bot\":false,\"first_name\":\"Aleksandr\",\"last_name\":\"Milenkov\",\"username\":\"Melook94\",\"language_code\":\"ru\"},\"date\":1697737543,\"chat\":{\"id\":9999,\"type\":\"private\",\"username\":\"Melook94\",\"first_name\":\"Aleksandr\",\"last_name\":\"Milenkov\"},\"text\":\"? ??????\"}}";
    private final Update update = BotUtils.fromJson(json, Update.class);

    @Test
    public void testMessageForTextCommand() {
        List<AbstractSendRequest<?>> abstractSendRequests = messageSupplier.messageForTextCommand("/start", update);
//        var actual = (SendMessage) abstractSendRequests.get(0);
        Mockito.verify(keyBoardService).mainMenuKeyboard(any(Update.class));





    }

}
//  public List<AbstractSendRequest<?>> messageForTextCommand(String command, Update update) {
//        if (command == null) {
//            return new ArrayList<>();
//        }
//        List<AbstractSendRequest<?>> messageList = new ArrayList<>();
//
//        switch (command) {
//            case ("/start"), ("Вернуться в главное меню") -> {
//                messageList.add(keyBoardService.mainMenuKeyboard(update));
//                return messageList;
//            }
//            case ("Приют для собак") -> {
//                currentAnimal = ANIMAL_DOG;
//                messageList.add(keyBoardService.dogShelterKeyboard(update));
//                return messageList;
//            }
//            case ("Приют для кошек") -> {
//                currentAnimal = ANIMAL_CAT;
//                messageList.add(keyBoardService.catShelterKeyboard(update));
//                return messageList;
//            }
//            case ("Как взять животное") -> {
//                if (currentAnimal.equals(ANIMAL_DOG))
//                    messageList.add(keyBoardService.howTakeDogKeyboard(update));
//                else messageList.add(keyBoardService.howTakeCatKeyboard(update));
//                return messageList;
//            }
//            case ("Позвать волонтера") -> {
//                return answerProducer.callVolunteer(update, currentAnimal);
//            }
//            case ("Как проехать к приюту") -> {
//                messageList.add(answerProducer.getSchema(update, currentAnimal));
//                return messageList;
//            }
//            case ("Инфо о приюте") -> {
//                messageList.add(answerProducer.getInfoAboutShelter(update, currentAnimal));
//                return messageList;
//            }
//            case ("О приюте") -> {
//                if (currentAnimal.equals(ANIMAL_DOG))
//                    messageList.add(keyBoardService.aboutCatShelterKeyboard(update));
//                else messageList.add(keyBoardService.aboutDogShelterKeyboard(update));
//                return messageList;
//            }
//            case ("Отправить отчет") -> {
//                isReport = true;
//                messageList.add(answerProducer.sendReportForm(update));
//
//                return messageList;
//            }
//            case ("Контактные данные охраны приюта") -> {
//                messageList.add(answerProducer.getContactsSecurityShelter(update, currentAnimal));
//                return messageList;
//            }
//            case ("Техника безопасности на территории приюта") -> {
//                messageList.add(answerProducer.getSafetyByShelter(update, currentAnimal));
//                return messageList;
//            }
//            case ("Оставить контакты для связи") -> {
//                isFeedback = true;
//                System.out.println("isFeedback = " + isFeedback);
//                log.debug("isFeedback = {}", isFeedback);
//                messageList.add(answerProducer.sendFeedbackForm(update));
//                return messageList;
//            }
//
//        }
//        return messageList;
//    }