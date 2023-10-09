package pro.sky.telegrambot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.model.Recommendation;
import pro.sky.telegrambot.service.RecommendationService;

@RestController()
@RequestMapping("/recommendation")
public class RecommendationController {


    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public ResponseEntity<Recommendation> addRecommendation(String title,String text,String animalType ){
        return ResponseEntity.ok(recommendationService.addRecommendation(title, text, animalType));
    }
}
