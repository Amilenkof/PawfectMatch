package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.configuration.AnimalTypes;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.SchemaRepository;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.ShelterService;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс который принимает Update, по нему подбирает клавиатуру,
 * которую нужно вернуть и формирует ответ для TelegrammListener
 */

@Service
@Slf4j
public class MessageSupplier {
    private final KeyBoardService keyBoardService;

    private final AnswerProducer answerProducer;

    private final String ANIMAL_CAT = "cat";
    private final String ANIMAL_DOG = "dog";
    private String currentAnimal = "";


    public MessageSupplier(KeyBoardService keyBoardService, AnswerProducer answerProducer) {
        this.keyBoardService = keyBoardService;
        this.answerProducer = answerProducer;
    }

    /**
     * Метод читает команду и формирует на нее ответ подкладывая нужную клавиатуру
     * PARAMS = Update update
     */
    public List<AbstractSendRequest<? extends AbstractSendRequest<?>>> executeResponse(Update update) {

        log.debug("Вызван метод executeResponse в классе MessageConsumer");
        String command = update.message().text();
        log.debug("Получена команда = {}", command);
        List<AbstractSendRequest<?>> messageList = new ArrayList<>();
        switch (command) {
            case ("/start"), ("Вернуться в главное меню") -> {
                messageList.add(keyBoardService.mainMenuKeyboard(update));
                return messageList;
            }
            case ("Приют для собак") -> {
                currentAnimal = ANIMAL_DOG;
                messageList.add(keyBoardService.dogShelterKeyboard(update));
                return messageList;
            }
            case ("Приют для кошек") -> {
                currentAnimal = ANIMAL_CAT;
                messageList.add(keyBoardService.catShelterKeyboard(update));
                return messageList;
            }
            case ("Как взять животное") -> {
                if (currentAnimal.equals(ANIMAL_DOG))
                    messageList.add(keyBoardService.howTakeDogKeyboard(update));
                else messageList.add(keyBoardService.howTakeCatKeyboard(update));
                return messageList;
            }
            case ("Позвать волонтера") -> {
                return keyBoardService.callVolunteer(update, currentAnimal);
            }
            case ("Как проехать к приюту") -> {
                messageList.add(answerProducer.getSchema(update, currentAnimal));
                return messageList;
            }
            case ("О приюте") -> {
                messageList.add(answerProducer.getInfoAboutShelter(update, currentAnimal));
                return messageList;
            }
            case ("Контактные данные охраны приюта") -> {
                messageList.add(answerProducer.getContactsSecurityShelter(update, currentAnimal));
                return messageList;
            }
            case ("Техника безопасности на территории приюта") -> {
                messageList.add(answerProducer.getSafetyByShelter(update, currentAnimal));
                return messageList;
            }
        }
        SendMessage recommendation = answerProducer.findRecommendation(update, command, currentAnimal);

        log.debug("Кейсы не выбраны, метод зашел в дефолтный блок");
        return List.of(recommendation);//todo возможно стоит изменить варианты ответа
    }


}




