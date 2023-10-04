package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
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
    private final MessageConsumer messageConsumer;
    private final ShelterService service;
//    @Autowired
//   private SessionFactory sessionFactory;
//

    public TelegramBotUpdatesListener(KeyBoardService keyBoardService, TelegramBot telegramBot, MessageConsumer messageConsumer, ShelterService service) {
        this.keyBoardService = keyBoardService;
        this.telegramBot = telegramBot;
        this.messageConsumer = messageConsumer;
        this.service = service;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override

    public int process(List<Update> updates) {
//        updates.forEach(update -> {
//            PhotoSize[] photo = update.message().photo();
//        });
        updates.forEach(update -> {
//            Session session = sessionFactory.openSession();

            log.info("Processing update: {}", update);
            List<SendMessage> messages = messageConsumer.executeResponse(update);

            messages.forEach(m-> log.info("message = {}",m.toString()));

            messages.forEach(telegramBot::execute);
            log.info("Sended {} messages",messages.size());




//            String s = service.find().toString();
//            SendMessage message = new SendMessage(update.message().chat().id(), s);
//            telegramBot.execute(message);

//            session.close();
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
