package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final KeyBoardService keyBoardService;

    private final TelegramBot telegramBot;
    private final MessageConsumer messageConsumer;

    public TelegramBotUpdatesListener(KeyBoardService keyBoardService, TelegramBot telegramBot, MessageConsumer messageConsumer) {
        this.keyBoardService = keyBoardService;
        this.telegramBot = telegramBot;
        this.messageConsumer = messageConsumer;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            PhotoSize[] photo = update.message().photo();
        });
        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            SendMessage message = messageConsumer.executeResponse(update);
            telegramBot.execute(message);
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
