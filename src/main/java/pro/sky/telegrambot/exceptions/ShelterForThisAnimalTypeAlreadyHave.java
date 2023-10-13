package pro.sky.telegrambot.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

//@ResponseStatus(NOT_ACCEPTABLE)
public class ShelterForThisAnimalTypeAlreadyHave extends RuntimeException {
    public ShelterForThisAnimalTypeAlreadyHave(String message) {
        super(message);
    }
}