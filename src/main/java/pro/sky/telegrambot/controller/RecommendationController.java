package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.model.DTO.AnimalDTO;
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
    @Operation(
            summary = "Добавление различных рекомендаций, советов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Recommendation.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Вы передали пустые поля",
                            content = @Content(
                            )
                    )
            }
    )
    public ResponseEntity<Recommendation> addRecommendation(@Parameter(description = "Название команды", example = "Готовим дом для щенка") @RequestParam String title,
                                                            @Parameter(description = "Ответы на команды", example = "Текст о том как приготовиться к появлению дома щенка") @RequestParam String text,
                                                            @Parameter(description = "Тип животного", example = "cat") @RequestParam String animalType) {
        return ResponseEntity.ok(recommendationService.addRecommendation(title, text, animalType));
    }
}
