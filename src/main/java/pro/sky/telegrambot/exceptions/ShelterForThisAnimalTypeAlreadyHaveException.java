package pro.sky.telegrambot.exceptions;

//@ResponseStatus(NOT_ACCEPTABLE)
public class ShelterForThisAnimalTypeAlreadyHaveException extends RuntimeException {
    public ShelterForThisAnimalTypeAlreadyHaveException(String message) {
        super(message);
    }
}