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
import pro.sky.telegrambot.model.Picture;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.service.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    public void testCheckSchema(){
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.of(new Shelter()));
        when(pictureService.findByShelter_id(anyLong())).thenReturn(Optional.of(new Picture()));
    }

//        public boolean checkSchema(String animalType) {
//        log.debug("Вызван метод AnswerProducer.checkSchema , animalType={}", animalType);
//        Shelter shelter = shelterService.findShelterByAnimalType(animalType).get();
//        Optional<Picture> optionalSchema = pictureService.findByShelter_id(shelter.getId());
//        if (optionalSchema.isEmpty()) {
//            log.debug("Схема проезда не найдена, клиенту будет направлен wrongMessage");
//        }
//        return optionalSchema.isEmpty();
//    }

}