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


    @SneakyThrows
    @Scheduled(cron = "0 0/1 * * * *")//каждую минуту
//    @Scheduled(cron = "0 0 21 * *?")//21 00 каждый день
    public void sendAllReportsToVolunteer() {
        if (sсhedulerService.getCurrentReports().size() > 0) {
            log.debug("Вызван TelegramBotUpdatesListener.sendAllReportsToVolunteer отправляем отчеты");
            sсhedulerService.getNextReport().ifPresent(telegramBot::execute);
        }
    }


    /**
     * метод по рассписанию отправляет должникам по отчетам напоминание отправить отчеты
     */
//    @Scheduled(cron = "0 0/1 * * * *")//каждую минуту// todo Включить  как будет все готово - надоел
//    @Scheduled(cron = "0 0 21 * *?")//21 00 каждый день
    public void scheduledLostReports() {
        List<SendMessage> messageList = sсhedulerService.sendMessagesLostReport();
        messageList.forEach(telegramBot::execute);
        log.debug("Должникам направлено {} писем с напоминанием об отчетах", messageList.size());
    }

}
