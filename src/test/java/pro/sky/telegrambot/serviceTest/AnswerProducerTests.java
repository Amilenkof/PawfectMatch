package pro.sky.telegrambot.serviceTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnswerProducerTests {
    @Mock
    private  PictureService pictureService;
    @Mock
    private  ShelterService shelterService;
    @Mock
    private  RecommendationService recommendationService;
    @Mock
    private  FeedbackService feedbackService;
    @Mock
    private  ReportService reportService;
    @Mock
    private  VolunteerService volunteerService;
    @Mock
    private  UsersService usersService;
    @InjectMocks
    private AnswerProducer answerProducer;

    @Test
    public void testCallVolunteer(){
        Shelter shelter = mock(Shelter.class);
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.of(shelter));
        Volunteer volunteer = mock(Volunteer.class);
        when(volunteerService.callVolunteer(any(Shelter.class))).thenReturn(Optional.empty());
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        Chat chat = mock(Chat.class);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(1L);
        List<SendMessage> expected = List.of(new SendMessage(1L, "ответ"));
        List<SendMessage> actual = answerProducer.callVolunteer(update, "cat");
        System.out.println("expected.equals(actual) = " + expected.equals(actual));//todo
//        Assertions.assertThat(expected.equals(actual)).isTrue();
//        List<SendMessage> expected = List.of(message);
//        List actual = answerProducer.callVolunteer(any(Update.class), anyString());
//        Assertions.assertThat(actual.equals(expected)).isTrue();
    }
}
//    public List<AbstractSendRequest<? extends AbstractSendRequest<?>>> callVolunteer(Update update, String animalType) {
//        Optional<Shelter> optionalShelter = shelterService.findShelterByAnimalType(animalType);
//        Shelter shelter = optionalShelter.orElse(new Shelter());
//        Optional<Volunteer> optionalVolunteer = volunteerService.callVolunteer(shelter);
//        if (optionalVolunteer.isEmpty()) {
//            return List.of(new SendMessage(update.message().chat().id(), "Извините,сейчас нет доступных волонтеров"));
//        }
//        SendMessage messageToVolonteer = new SendMessage(optionalVolunteer.get().getChatId(), "@" + update.message().chat().username() + " ожидает Вашего сообщения");
//        SendMessage messageToUser = new SendMessage(update.message().chat().id(), "Ожидайте, с Вами свяжется волонтер");
//        return List.of(messageToUser, messageToVolonteer);
//    }