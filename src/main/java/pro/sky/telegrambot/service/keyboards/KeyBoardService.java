package pro.sky.telegrambot.service.keyboards;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.ShelterService;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.List;
import java.util.Optional;


/**
 * Класс генерирует все необходимые клавиатуры для работы бота
 */
@Slf4j
@Service
public class KeyBoardService {
    private final VolunteerService volunteerService;
    private final ShelterService shelterService;


    public KeyBoardService(VolunteerService volunteerService, ShelterService shelterService) {


        this.volunteerService = volunteerService;
        this.shelterService = shelterService;
    }


    /**
     * Метод формирует клавиатуру для главного меню
     */
    public SendMessage mainMenuKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Приют для кошек");
        KeyboardButton button2 = new KeyboardButton("Приют для собак");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        SendMessage message = new SendMessage(update.message().chat().id(), "Привет, дружище, ты пришел за питомцем? Мы можем предложить тебе выбрать кошку или собаку, кого ты выберешь?");
        return message.replyMarkup(replyKeyboardMarkup);
    }
    /**
     * Метод формирует клавиатуру для принятия решения волонтером по отчету
     */
    public InlineKeyboardMarkup reportDecision() {
       return new InlineKeyboardMarkup(
                new InlineKeyboardButton("Принять отчет").callbackData("Принять отчет"),
                new InlineKeyboardButton("Вернуть отчет").callbackData("Вернуть отчет"));
//        KeyboardButton button1 = new KeyboardButton("Принять отчет");
//        KeyboardButton button2 = new KeyboardButton("Отправить на доработку");
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);


    }

    /**
     * Метод формирует клавиатуру для меню приюта для собак
     */

    public SendMessage dogShelterKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("О приюте");
        KeyboardButton button2 = new KeyboardButton("Как взять животное");
        KeyboardButton button3 = new KeyboardButton("Отправить отчет");
        KeyboardButton button4 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button5 = new KeyboardButton("Вернуться в главное меню");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        replyKeyboardMarkup.addRow(button3, button4);
        replyKeyboardMarkup.addRow(button5);
        SendMessage message = new SendMessage(update.message().chat().id(), "Приют для собак, PawfectMatch, приветствует тебя, чем могу помочь?");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    /**
     * Метод формирует клавиатуру для меню приюта для кошек
     */

    public SendMessage catShelterKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("О приюте");
        KeyboardButton button2 = new KeyboardButton("Как взять животное");
        KeyboardButton button3 = new KeyboardButton("Отправить отчет");
        KeyboardButton button4 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button5 = new KeyboardButton("Вернуться в главное меню");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        replyKeyboardMarkup.addRow(button3, button4);
        replyKeyboardMarkup.addRow(button5);
        SendMessage message = new SendMessage(update.message().chat().id(), "Приют для кошек, PawfectMatch, приветствует тебя, чем могу помочь?");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    /**
     * Метод формирует клавиатуру для меню описания приюта для собак
     */

    public SendMessage aboutDogShelterKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Инфо о приюте");
        KeyboardButton button2 = new KeyboardButton("Как проехать к приюту");
        KeyboardButton button3 = new KeyboardButton("Контактные данные охраны приюта");
        KeyboardButton button4 = new KeyboardButton("Техника безопасности на территории приюта");
        KeyboardButton button5 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button6 = new KeyboardButton("Вернуться в главное меню");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1);
        replyKeyboardMarkup.addRow(button2, button3, button4);
        replyKeyboardMarkup.addRow(button5);
        replyKeyboardMarkup.addRow(button6);
        SendMessage message = new SendMessage(update.message().chat().id(), "Здесь можно посмотреть информацию о нашем приюте для собак");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    /**
     * Метод формирует клавиатуру для меню описания приюта для кошек
     */

    public SendMessage aboutCatShelterKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Инфо о приюте");
        KeyboardButton button2 = new KeyboardButton("Как проехать к приюту");
        KeyboardButton button3 = new KeyboardButton("Контактные данные охраны приюта ");
        KeyboardButton button4 = new KeyboardButton("Техника безопасности на территории приюта");
        KeyboardButton button5 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button6 = new KeyboardButton("Вернуться в главное меню");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1);
        replyKeyboardMarkup.addRow(button2, button3, button4);
        replyKeyboardMarkup.addRow(button5);
        replyKeyboardMarkup.addRow(button6);
        SendMessage message = new SendMessage(update.message().chat().id(), "Здесь можно посмотреть информацию о нашем приюте для кошек");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    /**
     * Метод формирует клавиатуру для меню "как взять собаку"
     */

    public SendMessage howTakeDogKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Знакомство с животным");
        KeyboardButton button2 = new KeyboardButton("Документы для усыновления собаки");
        KeyboardButton button3 = new KeyboardButton("Транспортировка собаки");
        KeyboardButton button4 = new KeyboardButton("Готовим дом для взрослой собаки");//todo вынести в отдельную клавиатуру дом для щенка и дом для взрослой собаки?
        KeyboardButton button5 = new KeyboardButton("Готовим дом для щенка");
        KeyboardButton button6 = new KeyboardButton("Готовим дом для собаки-инвалида");
        KeyboardButton button7 = new KeyboardButton("Советы кинолога");
        KeyboardButton button8 = new KeyboardButton("Обратиться к кинологу");
        KeyboardButton button9 = new KeyboardButton("Причины для отказа усыновления");
        KeyboardButton button10 = new KeyboardButton("Оставить контакты для связи");
        KeyboardButton button11 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button12 = new KeyboardButton("Вернуться в главное меню");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        replyKeyboardMarkup.addRow(button3, button4);
        replyKeyboardMarkup.addRow(button5, button6);
        replyKeyboardMarkup.addRow(button7, button8);
        replyKeyboardMarkup.addRow(button9, button10);
        replyKeyboardMarkup.addRow(button11);
        replyKeyboardMarkup.addRow(button12);
        SendMessage message = new SendMessage(update.message().chat().id(), "Все что нужно знать о том, как взять собаку");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    /**
     * Метод формирует клавиатуру для меню "как взять кошку"
     */

    public SendMessage howTakeCatKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Знакомство с животным");
        KeyboardButton button2 = new KeyboardButton("Документы для усыновления");
        KeyboardButton button3 = new KeyboardButton("Транспортировка кошки");
        KeyboardButton button4 = new KeyboardButton("Готовим дом для взрослой кошки");//todo вынести в отдельную клавиатуру дом для кота и котенка?
        KeyboardButton button5 = new KeyboardButton("Готовим дом для котенка");
        KeyboardButton button6 = new KeyboardButton("Готовим дом для кошки-инвалида");
        KeyboardButton button7 = new KeyboardButton("Причины для отказа усыновления");
        KeyboardButton button8 = new KeyboardButton("Оставить контакты для связи");
        KeyboardButton button9 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button12 = new KeyboardButton("Вернуться в главное меню");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        replyKeyboardMarkup.addRow(button3, button4);
        replyKeyboardMarkup.addRow(button5, button6);
        replyKeyboardMarkup.addRow(button7, button8);
        replyKeyboardMarkup.addRow(button9);
        replyKeyboardMarkup.addRow(button12);
        SendMessage message = new SendMessage(update.message().chat().id(), "Все что нужно знать о том, как взять кошку");
        return message.replyMarkup(replyKeyboardMarkup);
    }
//todo пишу - Миленьков А. вынести в новый сервис AnswerComandService

    /**
     * Метод  реализоует логику вызова волонтера.
     * Генерирует сообщения пользователю и волонтеру<br>
     * Params=Update update - данные пользователя,
     * String AnimalType= тип животных в приюте,из которого будет вызван волонтер
     * Метод не выбрасывает ошибок, в случае обнаружения исключительных ситуаций, пользователю будет отправлен ответ
     * "Извините,сейчас нет доступных волонтеров"
     */
    public List<AbstractSendRequest<? extends AbstractSendRequest<?>>> callVolunteer(Update update, String animalType) {
        Optional<Shelter> optionalShelter = shelterService.findShelterByAnimalType(animalType);//Шелтеров не может не быть, тк мы забиваем кнопки только в меню где должен быть шелтер
        Shelter shelter = optionalShelter.orElse(new Shelter());
        Optional<Volunteer> optionalVolunteer = volunteerService.callVolunteer(update, shelter);// волонтеров может не быть в приюте, но ошибку бросать нельзя, ляжет все приложение
        if (optionalVolunteer.isEmpty()) {
            return List.of(new SendMessage(update.message().chat().id(), "Извините,сейчас нет доступных волонтеров"));// поэтому отправляю сообщение пользователю
        }
        SendMessage messageToVolonteer = new SendMessage(optionalVolunteer.get().getChatId(), "@" + update.message().chat().username() + " ожидает Вашего сообщения");
        SendMessage messageToUser = new SendMessage(update.message().chat().id(), "Ожидайте, с Вами свяжется волонтер");
        return List.of(messageToUser, messageToVolonteer);
    }

//todo

    /**
     * Метод формирует клавиатуру для меню назад
     */
    public SendMessage backMenuKeyboard(Update update) {
        KeyboardButton button = new KeyboardButton("Вернуться в главное меню");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button);
        SendMessage message = new SendMessage(update.message().chat().id(),
                "");
        return message.replyMarkup(replyKeyboardMarkup);
    }


}
