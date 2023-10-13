package pro.sky.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.repository.ReportRepository;

import java.nio.file.Path;

@Service
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Метод получает тестовый отчет о животном из БД, тестовый отчет добавляется со скриптов liquibase (cm.report-scripts.sql)
     *
     * @return Report если в базе есть тестовый отчет, если нет(по какимто причинам- отправляем пустой Report)
     */
    public Report getTestReport() {
        return reportRepository.findById(1L).orElse(new Report());
    }

}