package pro.sky.telegrambot.service.sheduler;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.AnswerProducer;
import pro.sky.telegrambot.service.ReportService;
import pro.sky.telegrambot.service.ShelterService;
import pro.sky.telegrambot.service.VolunteerService;
import pro.sky.telegrambot.service.keyboards.KeyBoardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final ShelterService shelterService;



    private final AnswerProducer<SendPhoto> answerProducer;


    public SсhedulerService(KeyBoardService keyBoardService, ReportService reportService, VolunteerService volunteerService, ShelterService shelterService, AnswerProducer<SendPhoto> answerProducer) {
        this.reportService = reportService;
        this.volunteerService = volunteerService;
        this.shelterService = shelterService;
        this.currentReports = new ArrayList<>();
        this.keyBoardService = keyBoardService;
        this.answerProducer = answerProducer;


    }



    /**
     * Метод получает текущий репорт, формирует из него SENDPHOTO по типу волонтера
     */
    public Optional<SendPhoto> getCurrentReportToSend() {
        log.debug("Вызван метод по расписанию ShedulerService.getCurrentReportToSend");
        currentReports = reportService.findAllTodayReports();
        return currentReports.stream()
                .filter(report -> report.getId() != 1L)//пропускаем образец отчета,чтобы его никому не отправлять
                .limit(1)
                .map(report -> lastReport = report)
                .map(report -> {
                    String type = report.getUser().getAnimal().getType();
                    Shelter shelter = shelterService.findShelterByAnimalType(type).get();//сноска. тк ни report, ни животные не могу существовать без SHELTER- могу себе позволить выдернуть обьект через гет
                    Optional<Volunteer> optionalVolunteer = volunteerService.callVolunteer(shelter);
                    if (optionalVolunteer.isPresent()) {
                        SendPhoto sendPhoto = answerProducer.sendReport(optionalVolunteer.get().getChatId(),
                                report,
                                report.getFood() + "\n" + report.getHealth() + "\n" + report.getBehaviour())
                                .replyMarkup(keyBoardService.reportDecision());
                        return sendPhoto;
                    }
                    return new SendPhoto(1L, "photo");//todo
                })
                .findFirst();

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
