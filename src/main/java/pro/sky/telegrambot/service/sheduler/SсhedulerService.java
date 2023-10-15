package pro.sky.telegrambot.service.sheduler;

import com.pengrad.telegrambot.request.SendPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.ReportService;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует отправку сообщений по расписанию
 */
@Service
@Slf4j
public class SсhedulerService {

    private final AnswerProducer<SendPhoto> answerProducer;



    public SсhedulerService( AnswerProducer<SendPhoto> answerProducer) {
        this.answerProducer = answerProducer;

    }

    /**
     * Метод формирует список всех текущих репортов, по каждому приюту для отправки волонтерам на просмотр
     */


    public List<SendPhoto> sendCurrentReports() {
        log.debug("Вызван метод по расписанию ShedulerService.sendCurrentReports");
        var reportResponse = new ArrayList<SendPhoto>();
        reportResponse.addAll(answerProducer.getCurrentReports("cat"));
        reportResponse.addAll(answerProducer.getCurrentReports("dog"));
        log.debug("К отправке волонтерам {} отчетов",reportResponse.size());
        return reportResponse;

    }




}
