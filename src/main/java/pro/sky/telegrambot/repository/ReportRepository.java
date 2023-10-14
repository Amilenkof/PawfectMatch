package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Report;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM report WHERE date_report > current_date AND user_id=?;")
    List<Report> findAllTodayReports(Long userID);


}
