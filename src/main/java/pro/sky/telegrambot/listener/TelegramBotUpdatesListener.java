package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;
import pro.sky.telegrambot.service.sheduler.SсhedulerService;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@EnableTransactionManagement
public class TelegramBotUpdatesListener<T extends AbstractSendRequest<T>> implements UpdatesListener {
    @Getter
    private final TelegramBot telegramBot;
    private final MessageSupplier messageSupplier;
    private final SсhedulerService sсhedulerService;


    public TelegramBotUpdatesListener(TelegramBot telegramBot, MessageSupplier messageSupplier, SсhedulerService sсhedulerService) {
        this.telegramBot = telegramBot;
        this.messageSupplier = messageSupplier;
        this.sсhedulerService = sсhedulerService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override

    public int process(List<Update> updates) {

        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            var messages = messageSupplier.executeResponse(update);
            messages.stream()
                    .peek(m -> log.info("message = {}", m.toString()))
                    .forEach(telegramBot::execute);

            if (messages.size() > 0) {
                log.info("Метод TelegramBotUpdatesListener.process отправил клиенту {} сообщение/я", messages.size());
            } else {
                log.info("Метод TelegramBotUpdatesListener.process отправил 0 сообщений");
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Метод запрашивает у ShedulerService  Report в виде SENDPHOTO и направляет волонтеру его на проверку
     */

    @Scheduled(cron = "0 0/1 * * * *")//каждую минуту
//@Scheduled(cron = "* 21 * * *")
    public void sendReportToVolunteer() {
        sсhedulerService.getCurrentReportToSend().ifPresent(report -> {
            if (!report.equals(new SendPhoto(1L, "photo"))) {//todo
                telegramBot.execute(report);
                log.debug("Отчет отправлен волонтеру на проверку");
            }
        });

    }


    /**
     * метод по рассписанию отправляет должникам по отчетам напоминание отправить отчеты
     */

//    @Scheduled(cron = "* 21 * * *")
    @Scheduled(cron = "0 0/1 * * * *")//каждую минуту
    public void scheduledLostReports() {
        List<SendMessage> messageList = sсhedulerService.sendMessagesLostReport();
        messageList.forEach(telegramBot::execute);
        log.debug("Должникам направлено {} писем с напоминанием об отчетах", messageList.size());
    }

}
