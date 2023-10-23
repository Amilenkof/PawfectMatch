package pro.sky.telegrambot.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Feedback;
import pro.sky.telegrambot.repository.FeedbackRepository;
import pro.sky.telegrambot.service.FeedbackService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTests {
    @Mock
    private FeedbackRepository feedbackRepository;
    @InjectMocks
    private FeedbackService feedbackService;


    @Test
    public void TestAddFeedBack() {
        String formFeedback = "Иванов,Иван,mail@mail.ru,+79271234567";
        Feedback feedback = new Feedback("Иван", "mail@mail.ru", "Иванов", "+79271234567");
        Mockito.when(feedbackRepository.save(Mockito.any(Feedback.class))).thenReturn(feedback);
        Optional<Feedback> actual = feedbackService.addFeedback(formFeedback);
        Optional<Feedback> expected = Optional.of(feedback);
        assertThat(actual.equals(expected)).isTrue();
    }

    @Test
    public void TestAddFeedbackNegative() {
        Optional<Feedback> actual = feedbackService.addFeedback("");
        assertThat(actual.equals(Optional.empty())).isTrue();
    }
}
