package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;
import pro.sky.telegrambot.service.ShelterService;


import java.util.List;

@Service
@Slf4j
@EnableTransactionManagement
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final KeyBoardService keyBoardService;

    private final TelegramBot telegramBot;
    private final MessageSupplier messageSupplier;
    private final ShelterService service;


    public TelegramBotUpdatesListener(KeyBoardService keyBoardService, TelegramBot telegramBot, MessageSupplier messageSupplier, ShelterService service) {
        this.keyBoardService = keyBoardService;
        this.telegramBot = telegramBot;
        this.messageSupplier = messageSupplier;
        this.service = service;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override

    public int process(List<Update> updates) {

        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            List<AbstractSendRequest<?>> messages = messageSupplier.executeResponse(update);
            messages.forEach(m-> log.info("message = {}",m.toString()));
            messages.forEach(telegramBot::execute);
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
