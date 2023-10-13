package pro.sky.telegrambot.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ReportRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    @Value("${path.to.defaultReportPicture}")
    String defaultReportPicture;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
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
            throw new RuntimeException("Ошибка чтения файла, проверьте путь = "+defaultReportPicture);
        }
        Report report = new Report(1L, getbytes, "1-Мясо, корм для собак VidalSosun,овощи", "2-Животное чувствует себя хорошо", "3-Животное полно сил и энергии", null);
        reportRepository.save(report);
    }

    /**
     * Метод получает массив байт из картинки
     * @return byte[]
     */
    private byte[] getbytes() throws IOException {
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
     * @return Report если в базе есть тестовый отчет, если нет(по какимто причинам- отправляем пустой Report)
     */
    public Report getTestReport() {
        return reportRepository.findById(1L).orElse(new Report());
    }
}