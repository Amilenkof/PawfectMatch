package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Report;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {


    //    @Query(nativeQuery = true, value = "SELECT * FROM report join users u on u.id = report.user_id join animal a on a.id = u.animal_id WHERE date_report > current_date AND a.type='?';")
//    List<Report> findAllTodayReports(String animalType);
    @Query(nativeQuery = true, value = "SELECT * FROM report WHERE date_report>current_date")
    List<Report> findAllTodayReports();

    List<Report> findReportByDateReportAfter(LocalDateTime DateTime);



}
