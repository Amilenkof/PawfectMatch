package pro.sky.telegrambot.listener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.CallBackHandler;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

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

    @Test
    public void testMessageForTextCommand(){


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