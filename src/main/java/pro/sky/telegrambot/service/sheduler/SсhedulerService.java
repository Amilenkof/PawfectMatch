package pro.sky.telegrambot.service.sheduler;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.ReportService;
import pro.sky.telegrambot.service.VolunteerService;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Класс реализует отправку сообщений по расписанию
 */
@Service
@Slf4j
public class SсhedulerService {

    @Getter
    private List<Report> currentReports;
    @Getter
    private Report lastReport;
    private final KeyBoardService keyBoardService;
    private final ReportService reportService;
    private final VolunteerService volunteerService;


    private final AnswerProducer<SendPhoto> answerProducer;


    public SсhedulerService(KeyBoardService keyBoardService, ReportService reportService, VolunteerService volunteerService, AnswerProducer<SendPhoto> answerProducer) {
        this.reportService = reportService;
        this.volunteerService = volunteerService;
        this.currentReports = new ArrayList<>();
        this.keyBoardService = keyBoardService;
        this.answerProducer = answerProducer;


    }



    /**
     * Метод получает текущий репорт, формирует из него SENDPHOTO по типу волонтера
     */
    public Optional<?> getCurrentReportToSend() {
        log.debug("Вызван метод по расписанию ShedulerService.getCurrentReportToSend");
        currentReports = reportService.findAllTodayReports();
        return currentReports.stream()
                .filter(report -> report.getId() != 1L)//пропускаем образец отчета,чтобы его никому не отправлять
                .limit(1)
                .map(report -> lastReport = report)
                .map(report -> {
                    String type = report.getUser().getAnimal().getType();
                    Optional<Volunteer> optionalVolunteer = volunteerService.findVolunteer(type);
                    if (optionalVolunteer.isPresent()) {
                        SendPhoto sendPhoto = answerProducer.sendReport(optionalVolunteer.get().getChatId(),
                                report,
                                report.getFood() + "\n" + report.getHealth() + "\n" + report.getBehaviour())
                                .replyMarkup(keyBoardService.reportDecision());
                        return Optional.of(sendPhoto);
                    }
                    return Optional.empty();
                }).toList().get(0);

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




}
