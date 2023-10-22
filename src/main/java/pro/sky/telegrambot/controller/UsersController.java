package pro.sky.telegrambot.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.exceptions.AnimalNotFoundException;
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
    public ResponseEntity<Users> addUsers(@RequestParam Long chatId,
                                          @RequestParam String firstName,
                                          @RequestParam String lastName,
                                          @RequestParam String email,
                                          @RequestParam String phone,
                                          @RequestParam Long animalID) {
        return ResponseEntity.ok(usersService.addUsers(chatId, firstName, lastName, email, phone, animalID));
    }
    @GetMapping("/increase")
    public ResponseEntity<Users> increaseDurationReports(@RequestParam Long userId, @RequestParam int increaseValue) {
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
