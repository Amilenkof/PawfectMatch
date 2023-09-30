package pro.sky.telegrambot.keyboards;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/***///todo дописать доку сделать логирование
@Slf4j
@Service
public class KeyBoardService {
//    private static final Map<String, SendMessage> commands = new HashMap<>();

    public KeyBoardService() {

    }

    public SendMessage mainMenuKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Приют для кошек");
        KeyboardButton button2 = new KeyboardButton("Приют для собак");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        SendMessage message = new SendMessage(update.message().chat().id(), "Привет, дружище, ты пришел за питомцем? Мы можем предложить тебе выбрать кошку или собаку, кого ты выберешь?");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    public SendMessage dogShelterKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Инфо о собачьем приюте");
        KeyboardButton button2 = new KeyboardButton("Как взять собаку");
        KeyboardButton button3 = new KeyboardButton("Отчет о собаке");
        KeyboardButton button4 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button5 = new KeyboardButton("Вернуться в главное меню");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        replyKeyboardMarkup.addRow(button3, button4);
        replyKeyboardMarkup.addRow(button5);
        SendMessage message = new SendMessage(update.message().chat().id(), "Приют для собак, PawfectMatch, приветствует тебя, чем могу помочь?");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    public SendMessage catShelterKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Инфо о кошечьем приюте");
        KeyboardButton button2 = new KeyboardButton("Как взять кошку");
        KeyboardButton button3 = new KeyboardButton("Отчет о кошке");
        KeyboardButton button4 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button5 = new KeyboardButton("Вернуться в главное меню");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        replyKeyboardMarkup.addRow(button3, button4);
        replyKeyboardMarkup.addRow(button5);
        SendMessage message = new SendMessage(update.message().chat().id(), "Приют для кошек, PawfectMatch, приветствует тебя, чем могу помочь?");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    public SendMessage aboutDogShelterKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("О собачьем приюте");
        KeyboardButton button2 = new KeyboardButton("Как проехать к приюту для собак");
        KeyboardButton button3 = new KeyboardButton("Контактные данные охраны приюта для собак");
        KeyboardButton button4 = new KeyboardButton("Техника безопасности на территории приюта для собак");
        KeyboardButton button5 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button6 = new KeyboardButton("Вернуться в главное меню");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1);
        replyKeyboardMarkup.addRow(button2, button3, button4);
        replyKeyboardMarkup.addRow(button5);
        replyKeyboardMarkup.addRow(button6);
        SendMessage message = new SendMessage(update.message().chat().id(), "Здесь можно посмотреть информацию о нашем приюте для собак");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    public SendMessage aboutCatShelterKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("О кошачьем приюте");
        KeyboardButton button2 = new KeyboardButton("Расписание работы, адрес, схема проезда кошачьего приюта");
        KeyboardButton button3 = new KeyboardButton("Контактные данные охраны приюта для кошек");
        KeyboardButton button4 = new KeyboardButton("Техника безопасности на территории приюта для кошек");
        KeyboardButton button5 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button6 = new KeyboardButton("Вернуться в главное меню");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1);
        replyKeyboardMarkup.addRow(button2, button3, button4);
        replyKeyboardMarkup.addRow(button5);
        replyKeyboardMarkup.addRow(button6);
        SendMessage message = new SendMessage(update.message().chat().id(), "Здесь можно посмотреть информацию о нашем приюте для кошек");
        return message.replyMarkup(replyKeyboardMarkup);
    }

    public SendMessage howTakeDogKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Знакомство с собакой");
        KeyboardButton button2 = new KeyboardButton("Документы для усыновления собаки");
        KeyboardButton button3 = new KeyboardButton("Транспортировка собаки");
        KeyboardButton button4 = new KeyboardButton("Готовим дом для взрослой собаки");//todo вынести в отдельную клавиатуру дом для щенка и дом для взрослой собаки?
        KeyboardButton button5 = new KeyboardButton("Готовим дом для щенка");
        KeyboardButton button6 = new KeyboardButton("Готовим дом для собаки-инвалида");
        KeyboardButton button7 = new KeyboardButton("Советы кинолога");
        KeyboardButton button8 = new KeyboardButton("Обратиться к кинологу");
        KeyboardButton button9 = new KeyboardButton("Причины для отказа усыновления пса");
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

    public SendMessage howTakeCatKeyboard(Update update) {
        KeyboardButton button1 = new KeyboardButton("Знакомство с кошкой");
        KeyboardButton button2 = new KeyboardButton("Документы для усыновления кошки");
        KeyboardButton button3 = new KeyboardButton("Транспортировка кошки");
        KeyboardButton button4 = new KeyboardButton("Готовим дом для взрослого кота/кошки");//todo вынести в отдельную клавиатуру дом для кота и котенка?
        KeyboardButton button5 = new KeyboardButton("Готовим дом для котенка");
        KeyboardButton button6 = new KeyboardButton("Готовим дом для кошки-инвалида");
        KeyboardButton button7 = new KeyboardButton("Причины для отказа усыновления кошки");
        KeyboardButton button8 = new KeyboardButton("Оставить контакты для связи");
        KeyboardButton button9 = new KeyboardButton("Позвать волонтера");
        KeyboardButton button12 = new KeyboardButton("Вернуться в главное меню");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(button1, button2);
        replyKeyboardMarkup.addRow(button3, button4);
        replyKeyboardMarkup.addRow(button5, button6);
        replyKeyboardMarkup.addRow(button7, button8);
        replyKeyboardMarkup.addRow(button9);
        replyKeyboardMarkup.addRow(button12);
        SendMessage message = new SendMessage(update.message().chat().id(), "Все что нужно знать о том, как взять собаку");
        return message.replyMarkup(replyKeyboardMarkup);
    }
    public SendMessage callvolunteer(Update update){
        //todo Логика которая зовет волонтера
        return new SendMessage(update.message().chat().id(),"Волонтер уже тут");
    }

}
