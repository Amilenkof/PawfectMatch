package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Recommendation;

@Repository
public interface RecommendationRepository extends JpaRepository <Recommendation,Long> {
}
