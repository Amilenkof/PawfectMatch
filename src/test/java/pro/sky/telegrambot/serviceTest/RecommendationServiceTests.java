package pro.sky.telegrambot.serviceTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Recommendation;
import pro.sky.telegrambot.repository.RecommendationRepository;
import pro.sky.telegrambot.service.RecommendationService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTests {

    @Mock
    private RecommendationRepository recommendationRepository;
    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    public void testGetFirstByTitle() {
        Optional<String> actual = recommendationService.getFirstByTitle("title", "type");
        Mockito.verify(recommendationRepository).getFirstByTitle("title", "type");

    }

    @Test
    public void addRecommendation() {
        Recommendation expected = new Recommendation("title", "text", "type");
        Mockito.when(recommendationRepository.save(Mockito.any(Recommendation.class))).thenReturn(expected);
        Recommendation actual = recommendationService.addRecommendation("title", "text", "type");
        Assertions.assertThat(actual.getAnimalType().equals(expected.getAnimalType()) &&
                              actual.getText().equals(expected.getText()) &&
                              actual.getTitle().equals(expected.getTitle())).isTrue();
        Mockito.verify(recommendationRepository).save(expected);

    }

}