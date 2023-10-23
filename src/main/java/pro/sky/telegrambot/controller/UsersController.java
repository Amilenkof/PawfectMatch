package pro.sky.telegrambot.controller;

import com.pengrad.telegrambot.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.exceptions.AnimalNotFoundException;
import pro.sky.telegrambot.model.DTO.AnimalDTO;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.service.UsersService;


@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping
    @Operation(
            summary = "Добавить пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Users.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Такого животного нет",
                            content = @Content(
                            )
                    )
            }
    )
    public ResponseEntity<Users> addUsers(@Parameter(description = "ID чата пользователя") @RequestParam Long chatId,
                                          @Parameter(description = "Имя", example = "Иван") @RequestParam String firstName,
                                          @Parameter(description = "Фамилия", example = "Иванов") @RequestParam String lastName,
                                          @Parameter(description = "Почта", example = "example@mail.ru") @RequestParam String email,
                                          @Parameter(description = "Телефон", example = "89871234567") @RequestParam String phone,
                                          @Parameter(description = "ID животного") @RequestParam Long animalID) {
        return ResponseEntity.ok(usersService.addUsers(chatId, firstName, lastName, email, phone, animalID));
    }

    @Operation(
            summary = "Продлить срок подачи отчетов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Users.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Введены неверные данные",
                            content = @Content(
                            )
                    )
            }
    )
    @GetMapping("/increase")
    public ResponseEntity<Users> increaseDurationReports(@Parameter(description = "ID чата пользователя") @RequestParam Long userId,
                                                         @Parameter(description = "На сколько дней продлить срок подачи отчетов") @RequestParam int increaseValue) {
        return ResponseEntity.ok(usersService.increaseDurationCounter(userId, increaseValue));
    }
    @ExceptionHandler(AnimalNotFoundException.class)
    public ResponseEntity<String> handleException( AnimalNotFoundException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException( IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatusCode.valueOf(400)).build();
    }
}
