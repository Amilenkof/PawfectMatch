package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.sheduler.SсhedulerService;

@Service
@Slf4j
public class CallBackHandler {
    public CallBackHandler(SсhedulerService sсhedulerService) {
        this.sсhedulerService = sсhedulerService;
    }

    private final SсhedulerService sсhedulerService;


    public void handle(Update update){
        String data = update.callbackQuery().data();
        if (data.equals("Принять отчет")){
            sсhedulerService.getLastReport().
        }
    }
}
//                new InlineKeyboardButton("Принять отчет").callbackData("Принять отчет"),
//                new InlineKeyboardButton("Вернуть отчет").callbackData("Вернуть отчет"));