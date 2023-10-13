package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendPhoto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pro.sky.telegrambot.service.SchemaService;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;
import pro.sky.telegrambot.service.ShelterService;


import java.util.List;

@Service
@Slf4j
@EnableTransactionManagement
public class TelegramBotUpdatesListener<T extends AbstractSendRequest<T>> implements UpdatesListener {
    private final KeyBoardService keyBoardService;

    private final TelegramBot telegramBot;
    private final MessageSupplier messageSupplier;
    private final ShelterService service;
    private final SchemaService schemaService;


    public TelegramBotUpdatesListener(KeyBoardService keyBoardService, TelegramBot telegramBot, MessageSupplier messageSupplier, ShelterService service, SchemaService schemaService) {
        this.keyBoardService = keyBoardService;
        this.telegramBot = telegramBot;
        this.messageSupplier = messageSupplier;
        this.service = service;
        this.schemaService = schemaService;
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


}
