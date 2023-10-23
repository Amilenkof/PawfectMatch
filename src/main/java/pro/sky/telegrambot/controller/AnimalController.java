package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.DTO.AnimalDTO;
import pro.sky.telegrambot.service.AnimalService;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    @Operation(
            summary = "Добавить животное",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Такого приюта нет",
                            content = @Content(
                            )
                    )
            }
    )
    public ResponseEntity<AnimalDTO> addAnimal(@Parameter(description = "Тип животного", example = "cat") @RequestParam String type,
                                               @Parameter(description = "Имя животного", example = "Vasya") @RequestParam String name,
                                               @Parameter(description = "Если true, то животное больное", example = "true") @RequestParam boolean isSick,
                                               @Parameter(description = "Если true, то это детеныш", example = "true") @RequestParam boolean isLittle,
                                               @Parameter(description = "Если true, то животное в приюте", example = "true") @RequestParam boolean status) {
        return ResponseEntity.ok(animalService.addAnimal(type, name, isSick, isLittle, status));
    }

    @ExceptionHandler(ShelterNotFoundException.class)
    public ResponseEntity<String> handleException(ShelterNotFoundException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }

}
