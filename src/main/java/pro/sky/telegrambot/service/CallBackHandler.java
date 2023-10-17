package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.service.sheduler.SсhedulerService;


/**
 * Класс реализует логику ответов на нажатие кнопок волонтером при проверке отчетов пользователей
 */
@Service
@Slf4j
public class CallBackHandler {


    private final SсhedulerService sсhedulerService;
    private final UsersService usersService;

    public CallBackHandler(SсhedulerService sсhedulerService, UsersService usersService) {
        this.sсhedulerService = sсhedulerService;
        this.usersService = usersService;
    }

    /**
     * Метод получает перехваченный ответ от волонтера,передает его в UsersService для проставления необходимых
     * изменений и отправляет отчет пользователю о результатах отчета
     *
     * @param update
     * @return SendMessage
     */
    public SendMessage handle(Update update) {
        String data = update.callbackQuery().data();
        Report report = sсhedulerService.getLastReport();
        Users user = report.getUser();
        return usersService.setReportResult(user, data);
    }
}
