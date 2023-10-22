package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exceptions.MessageInReportUncorrectException;
import pro.sky.telegrambot.exceptions.UsersNotFoundException;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.repository.ReportRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final UsersService usersService;
    @Value("${path.to.defaultReportPicture}")
    private String defaultReportPicture;
    private final String regex = "^(.*?)\n(.*?)\n(.*?)$";
    private final DataGetter dataGetter;


    private final TelegramBot telegramBot;
    private final Pattern pattern = Pattern.compile(regex);

    public ReportService(ReportRepository reportRepository, UsersService usersService, DataGetter dataGetter, TelegramBot telegramBot) {
        this.reportRepository = reportRepository;
        this.usersService = usersService;
        this.dataGetter = dataGetter;
        this.telegramBot = telegramBot;
    }


    /**
     * Init метод создает дефолтный отчет о животном,чтобы показать клиенту как должен выглядеть отчет
     */
    @PostConstruct
    void init() {
        byte[] getbytes = null;
        try {
            getbytes = getbytes();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла, проверьте путь = " + defaultReportPicture);
        }
        Report report = new Report(1L, getbytes, "Опишите рацион питомца",
                "Опишите самочувствие питомца",
                "Опишите поведение питомца",
                LocalDateTime.now(),
                null);
        addReportToDB(report);
    }


    /**
     * Метод получает массив байт из картинки
     *
     * @return byte[]
     */
    public byte[] getbytes() throws IOException {
        Path from = Path.of(defaultReportPicture);
        try (InputStream inputStream = Files.newInputStream(from);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }


    /**
     * Метод получает тестовый отчет о животном из БД, тестовый отчет добавляется со скриптов liquibase (cm.report-scripts.sql)
     *
     * @return Report если в базе есть тестовый отчет, если нет(по какимто причинам- отправляем пустой Report)
     */
    public Report getTestReport() {
        return reportRepository.findById(1L).get();
    }


    /**
     * Метод получает сообщение клиента и создает отчет-report в БД
     *
     * @param update
     * @return Optional<Report>
     * @throws UsersNotFoundException если пользователь отправивший отчет отсутствует в БД,
     *                                MessageInReportUncorrectException если пользователь передал не корректное сообщение
     */
    public Report addReport(Update update) {
        log.debug("Вызван метод ReportService.addReport");
        Long chatId = update.message().chat().id();
        Optional<Users> optionalUser = usersService.findByChatId(chatId);
        Users users = optionalUser.orElseThrow(() -> new UsersNotFoundException("Пользователь с таким ChatID не обнаружен"));
        String text = update.message().caption();
        if (text == null) throw new MessageInReportUncorrectException("В отчете отсутствует описание");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String food = matcher.group(1);
            String health = matcher.group(2);
            String behavior = matcher.group(3);

           return addReportToDB(new Report(dataGetter.getPhoto(update), food, health, behavior, LocalDateTime.now(), users));

        }
        throw new MessageInReportUncorrectException("Не удалось привести сообщение к виду регулярного выражения");
    }



    /**
     * Метод добавляет переданный Report в базу данных
     *
     * @param report
     * @return Report
     */
    @NotNull
    @Transactional
    private Report addReportToDB(Report report) {
        return reportRepository.save(report);
    }




    /**
     * Метод получает список отчетов о питомцах присланных сегодня
     *
     * @return list<Report>
     */
    @Transactional
    public List<Report> findAllTodayReports() {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);//дата от начала текущих суток
        return reportRepository.findReportByDateReportAfter(dateTime);
    }


}