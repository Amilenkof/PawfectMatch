package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository <Feedback,Long> {


}
