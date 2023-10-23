package pro.sky.telegrambot.exceptions;

public class ShelterForThisAnimalTypeAlreadyHaveException extends RuntimeException {
    public ShelterForThisAnimalTypeAlreadyHaveException(String message) {
        super(message);
    }
}