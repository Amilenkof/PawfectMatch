package pro.sky.telegrambot.service.sheduler;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс реализует отправку сообщений по расписанию
 */
@Service
@Slf4j
public class SсhedulerService {
    @Getter
    private List<SendPhoto> currentReports;
    @Getter
    private  SendPhoto lastReport;
    private final KeyBoardService keyBoardService;


    private final AnswerProducer<SendPhoto> answerProducer;


    public SсhedulerService(KeyBoardService keyBoardService, AnswerProducer<SendPhoto> answerProducer) {
        this.currentReports = new ArrayList<>();
        this.keyBoardService = keyBoardService;
        this.answerProducer = answerProducer;


    }

    /**
     * Метод формирует список всех текущих репортов и сохраняет их в List поле currentReports
     */

    @Scheduled(cron = "0 0/1 * * * *")//каждую минуту
//    @Scheduled(cron = "0 0 21 * *?")//21 00 каждый день
    public void sendCurrentReports() {
        log.debug("Вызван метод по расписанию ShedulerService.sendCurrentReports");
        var reportResponse = new ArrayList<SendPhoto>();
        reportResponse.addAll(answerProducer.getCurrentReports("cat"));
        reportResponse.addAll(answerProducer.getCurrentReports("dog"));
        log.debug("К отправке волонтерам {} отчетов", reportResponse.size());
        currentReports = reportResponse;
    }

    /**
     * Метод формирует список всех сообщений для отправки должникам по отчетам
     */
    public List<SendMessage> sendMessagesLostReport() {
        log.debug("Вызван метод по расписанию ShedulerService.sendMessagesLostReport");
        List<SendMessage> messages = answerProducer.getListMessagesForReportLostUsers();
        log.debug("К отправке  {} писем должникам по  отчетам", messages.size());
        return messages;
    }

    /**
     * Метод получает один репорт из списка текущих репортов к отправке, добавляет в него клавиатуру для выбора действия
     */
    public Optional<SendPhoto> getNextReport() {
        log.debug("Вызван метод ShedulerService.getNextReport к отправке волонтерам {} отчетов",currentReports.size());
        Optional<SendPhoto> currentReport = currentReports.stream().filter(Objects::nonNull).findFirst();
        currentReport.ifPresent(sendPhoto -> currentReports.remove(sendPhoto));
        Optional<SendPhoto> reportToSend = currentReport.map(sendPhoto -> sendPhoto.replyMarkup(keyBoardService.reportDecision()));
        reportToSend.ifPresent(report -> lastReport = report);
        return reportToSend;
    }


}
