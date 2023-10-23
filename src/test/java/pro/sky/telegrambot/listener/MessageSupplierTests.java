package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.CallBackHandler;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageSupplierTests {
    @Spy
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
    public void test1MessageForTextCommand() {
        var abstractSendRequests = messageSupplier.messageForTextCommand("/start", update);
        var actual = abstractSendRequests.get(0);
        var parameters = actual.getParameters();
        var chatId = parameters.get("chat_id").equals(9999L);
        var text = parameters.get("text").equals("Привет, дружище, ты пришел за питомцем? Мы можем предложить тебе выбрать кошку или собаку, кого ты выберешь?");
        assertThat(chatId && text && parameters.get("reply_markup").getClass().equals(ReplyKeyboardMarkup.class)).isTrue();
        verify(keyBoardService).mainMenuKeyboard(any(Update.class));
    }

    @Test
    public void test2MessageForTextCommand() {
        var abstractSendRequests = messageSupplier.messageForTextCommand("Приют для собак", update);
        var actual = abstractSendRequests.get(0);
        var parameters = actual.getParameters();
        assertThat(parameters.get("chat_id").equals(9999L) &&
                   parameters.get("text").equals("Приют для собак, PawfectMatch, приветствует тебя, чем могу помочь?") &&
                   parameters.get("reply_markup").getClass().equals(ReplyKeyboardMarkup.class)).isTrue();
        verify(keyBoardService).dogShelterKeyboard(any(Update.class));

    }

    @Test
    public void test3MessageForTextCommand() {
        var abstractSendRequests = messageSupplier.messageForTextCommand("Приют для кошек", update);
        var actual = abstractSendRequests.get(0);
        var parameters = actual.getParameters();
        assertThat(parameters.get("chat_id").equals(9999L) &&
                   parameters.get("text").equals("Приют для кошек, PawfectMatch, приветствует тебя, чем могу помочь?") &&
                   parameters.get("reply_markup").getClass().equals(ReplyKeyboardMarkup.class)).isTrue();
        verify(keyBoardService).catShelterKeyboard(any(Update.class));
    }

    @Test
    public void testCallVolunteerCommand() {
        var actual = messageSupplier.messageForTextCommand("Позвать волонтера", update);
        verify(answerProducer).callVolunteer(any(Update.class), anyString());
    }

    @Test
    public void testGetSchemaCommand() {
        var actual = messageSupplier.messageForTextCommand("Как проехать к приюту", update);
        verify(answerProducer).getSchema(any(Update.class), anyString());
    }

    @Test
    public void testInfoAboutShelterCommand() {
        var actual = messageSupplier.messageForTextCommand("Инфо о приюте", update);
        verify(answerProducer).getInfoAboutShelter(any(Update.class), anyString());
    }

    @Test
    public void testInfoAboutSecurityCommand() {
        var actual = messageSupplier.messageForTextCommand("Контактные данные охраны приюта", update);
        verify(answerProducer).getContactsSecurityShelter(any(Update.class), anyString());
    }

    @Test
    public void testInfoAboutSafetyCommand() {
        var actual = messageSupplier.messageForTextCommand("Техника безопасности на территории приюта", update);
        verify(answerProducer).getSafetyByShelter(any(Update.class), anyString());
    }

    @Test
    public void testKeyboardServiceDogShelterKeyboard() {
        messageSupplier.messageForTextCommand("Приют для собак", update);
        var list = messageSupplier.messageForTextCommand("О приюте", update);
        var actual = list.get(0);
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(9999L);
        boolean text = parameters.get("text").equals("Здесь можно посмотреть информацию о нашем приюте для собак");
        boolean replyMarkup = parameters.get("reply_markup").getClass().equals(ReplyKeyboardMarkup.class);
        assertThat(chatId &&
                   text &&
                   replyMarkup).isTrue();
    }

    @Test
    public void testKeyboardServiceCatShelterKeyboard() {
        messageSupplier.messageForTextCommand("Приют для кошек", update);
        var list = messageSupplier.messageForTextCommand("О приюте", update);
        var actual = list.get(0);
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(9999L);
        boolean text = parameters.get("text").equals("Здесь можно посмотреть информацию о нашем приюте для кошек");
        boolean replyMarkup = parameters.get("reply_markup").getClass().equals(ReplyKeyboardMarkup.class);
        assertThat(chatId &&
                   text &&
                   replyMarkup).isTrue();
    }

    @Test
    public void testSendReportCommand() {
        var list = messageSupplier.messageForTextCommand("Отправить отчет", update);
        verify(answerProducer).sendReportForm(update);
    }

    @Test
    public void testSendFeedbackFormCommand() {
        var list = messageSupplier.messageForTextCommand("Оставить контакты для связи", update);
        verify(answerProducer).sendFeedbackForm(update);
    }

    @Test
    public void testHowTakeAnimalCatCommand() {
        messageSupplier.messageForTextCommand("Приют для кошек", update);
        var list = messageSupplier.messageForTextCommand("Как взять животное", update);
        var parameters = list.get(0).getParameters();
        assertThat(parameters.get("chat_id").equals(9999L) &&
                   parameters.get("text").equals("Все что нужно знать о том, как взять кошку") &&
                   parameters.get("reply_markup").getClass().equals(ReplyKeyboardMarkup.class)).isTrue();
    }

    @Test
    public void testHowTakeAnimalDogCommand() {
        messageSupplier.messageForTextCommand("Приют для собак", update);
        var list = messageSupplier.messageForTextCommand("Как взять животное", update);
        var parameters = list.get(0).getParameters();
        assertThat(parameters.get("chat_id").equals(9999L) &&
                   parameters.get("text").equals("Все что нужно знать о том, как взять собаку") &&
                   parameters.get("reply_markup").getClass().equals(ReplyKeyboardMarkup.class)).isTrue();
    }


}