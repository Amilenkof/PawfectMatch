package pro.sky.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Recommendation;
import pro.sky.telegrambot.repository.RecommendationRepository;

import java.util.Optional;

@Service
@Slf4j
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public Optional<String> getFirstByTitle(String title, String animalType) {
        return recommendationRepository.getFirstByTitle(title, animalType);
    }

    public Recommendation addRecommendation(String title, String text, String animalType) {
        return recommendationRepository.save(new Recommendation(title, text, animalType));
    }

}
