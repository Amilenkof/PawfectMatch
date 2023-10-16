package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.service.sheduler.SсhedulerService;

@Service
@Slf4j
public class CallBackHandler {
    public CallBackHandler(SсhedulerService sсhedulerService) {
        this.sсhedulerService = sсhedulerService;
    }

    private final SсhedulerService sсhedulerService;


    public SendMessage handle(Update update) {
        String data = update.callbackQuery().data();
        Report report = sсhedulerService.getLastReport();
        Users user = report.getUser();
        if (data.equals("Принять отчет")) {
            user.setDaysLostCounter(0L);
            Integer durationCounter = user.getDurationCounter();
            if (durationCounter == 0) {
                user.getAnimal().setStatus(false);
                return new SendMessage(user.getChatId(), "Отличная работа!! Питомец стал полноправным членом Вашей семьи!");
            }
            user.setDurationCounter(durationCounter - 1);
            return new SendMessage(user.getChatId(), "Отчет проверен");
        } else
            return new SendMessage(user.getChatId(), "Отчет не прошел проверку, пожалуйста заполняйте отчет подробнее, если у Вас возникли вопросы воспользуйтесь формой Позвать Волонтера");

    }
}
