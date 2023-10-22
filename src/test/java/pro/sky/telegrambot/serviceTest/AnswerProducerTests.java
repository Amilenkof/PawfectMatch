package pro.sky.telegrambot.serviceTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.*;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.service.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class AnswerProducerTests {
    @Mock
    private PictureService pictureService;
    @Mock
    private ShelterService shelterService;
    @Mock
    private RecommendationService recommendationService;
    @Mock
    private FeedbackService feedbackService;
    @Mock
    private ReportService reportService;
    @Mock
    private VolunteerService volunteerService;
    @Mock
    private UsersService usersService;
    @Mock
    private ShelterRepository shelterRepository;
    @InjectMocks
    private AnswerProducer answerProducer;

    private final String json = "{\"update_id\":513378858,\"message\":{\"message_id\":3543,\"from\":{\"id\":1107343760,\"is_bot\":false,\"first_name\":\"Aleksandr\",\"last_name\":\"Milenkov\",\"username\":\"Melook94\",\"language_code\":\"ru\"},\"date\":1697737543,\"chat\":{\"id\":9999,\"type\":\"private\",\"username\":\"Melook94\",\"first_name\":\"Aleksandr\",\"last_name\":\"Milenkov\"},\"text\":\"? ??????\"}}";
    private final Update update = BotUtils.fromJson(json, Update.class);

    @Test
    public void testCallVolunteer() {
        Shelter shelter = mock(Shelter.class);
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.of(shelter));
        when(volunteerService.callVolunteer(any(Shelter.class))).thenReturn(Optional.empty());
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        Chat chat = mock(Chat.class);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(1L);
        List<SendMessage> actual = answerProducer.callVolunteer(update, "cat");
        SendMessage actualMessage = actual.get(0);
        Map<String, Object> parameters = actualMessage.getParameters();
        assertThat(parameters.get("text").equals("Извините,сейчас нет доступных волонтеров") &&
                parameters.get("chat_id").equals(1L)).isTrue();
    }


    @Test
    public void testCheckShelter() {
        Shelter shelter = mock(Shelter.class);
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.of(shelter));
        assertThat(answerProducer.checkShelter(anyString())).isFalse();
    }

    @Test
    public void testGetSchema() {
        Update update = BotUtils.fromJson(json, Update.class);
        var actual = answerProducer.getSchema(update, "Test");
//        var expected = new SendMessage(9999L, "Извините,схема проезда не найдена");
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(9999L);
        boolean text = parameters.get("text").equals("Извините, схема проезда не найдена");
        assertThat(chatId && text).isTrue();
    }


    @Test
    public void testGetInfoAboutShelter() {
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.of(new Shelter()));
        var actual = answerProducer.getInfoAboutShelter(update, "Test");
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(9999L);
        boolean equals = parameters.get("text").equals("null \n" +
                "Наш приют находится по адресу: null\n" +
                "Время работы : null");
        assertThat(chatId &&
                equals).isTrue();
    }

    @Test
    public void testGetInfoAboutShelter1() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.of(new Shelter()));
        var actual = answerProducer.getInfoAboutShelter(update, "cat");
        var parameters = actual.getParameters();
        boolean text = parameters.get("text").equals("null \n" +
                "Наш приют находится по адресу: null\n" +
                "Время работы : null");
        boolean chatId = parameters.get("chat_id").equals(1L);
        assertThat(chatId).isTrue();

    }

    @Test
    public void testCheckSchema() {
        Shelter shelter = mock(Shelter.class);
        Picture picture = mock(Picture.class);
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.ofNullable(shelter));
        when(pictureService.findByShelter_id(anyLong())).thenReturn(Optional.of(picture));
        assertThat(answerProducer.checkSchema(anyString())).isFalse();
    }

    @Test
    public void testGetContactsSecurityShelter() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.of(new Shelter()));
        var actual = answerProducer.getContactsSecurityShelter(update, "cat");
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(1L);
        assertThat(chatId).isTrue();
    }

    @Test
    public void testGetSafetyByShelter() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        Shelter shelter = new Shelter();
        shelter.setSafety("bla-bla");
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.of(shelter));
        var actual = answerProducer.getSafetyByShelter(update, "cat");
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(1L);
        boolean text = parameters.get("text").equals("bla-bla");
        assertThat(chatId && text).isTrue();
    }

    @Test
    public void testFindRecommendation() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(recommendationService.getFirstByTitle(anyString(), anyString())).thenReturn(Optional.of("test"));
        var actual = answerProducer.findRecommendation(update, "test", "cat");
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(1L);
        boolean text = parameters.get("text").equals("test");
        assertThat(chatId && text).isTrue();
    }

    @Test
    public void testSendFeedbackForm() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        var actual = answerProducer.sendFeedbackForm(update);
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(1L);
        boolean text = parameters.get("text").equals("Пожалуйста пришлите Ваши контакты в форме:Иванов,Иван,mail@mail.ru,+79271234567");
        assertThat(chatId && text).isTrue();
    }

    @Test
    public void testAddFeedback() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        var actual = answerProducer.addFeedback(update);
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(1L);
        boolean text = parameters.get("text").equals("Не удалось добавить сообщение, попробуйте еще раз, обратите внимание на пробелы и запятые в указанной форме");
        assertThat(chatId && text).isTrue();
    }

    @Test
    public void testSendReportForm() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        Report report = new Report();
        report.setFood("qwe");
        report.setHealth("qwe1");
        report.setBehaviour("qwe2");
        report.setPhoto(new byte[]{1, 2, 3});
        when(reportService.getTestReport()).thenReturn(report);
        String reportCaption = String.format("Просим прислать отчет о Вашем питомце как в форме ниже: \n%s\n%s\n%s\n \n" +
                        " Каждый пункт с новой строки, обязательно пришлите фотографию питомца",
                report.getFood(),
                report.getHealth(),
                report.getBehaviour());

        var actual = answerProducer.sendReportForm(update);
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(1L);
        boolean text = parameters.get("caption").equals(reportCaption);
        assertThat(chatId && text).isTrue();
    }

    @Test
    public void testAddReport() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        var actual = answerProducer.addReport(update);
        var parameters = actual.getParameters();
        boolean chatId = parameters.get("chat_id").equals(1L);
        boolean text = parameters.get("text").equals("Пожалуйста,заполните отчет в соответствии с формой.Не забудьте прикрепить " +
                "фото питомца");
        assertThat(chatId && text).isTrue();
    }

    @Test
    public void testGetListMessagesForReportLostUsers() {
        Users users1 = new Users();
        users1.setId(1L);
        when(usersService.findAllByDaysLostCounterIsAfter()).thenReturn(List.of(users1));//, users2));
        var actual = answerProducer.getListMessagesForReportLostUsers();

        var id1 = (SendMessage) actual.get(0);
        var parameters = id1.getParameters();
        parameters.put("chat_id", 1L);
        System.out.println("parameters = " + parameters);

        boolean chatId = parameters.get("chat_id").equals(1L);
        boolean text = parameters.get("text").equals("Дорогой, друг не забывай присылать отчеты о питомце каждый день, иначе волонтерам придется прийти и посмотреть как у него дела");
        assertThat(chatId && text).isTrue();
    }

}