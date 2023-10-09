package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Recommendation;

import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    @Query(nativeQuery = true, value = "SELECT text FROM recommendation WHERE title=? AND animal_type=?")
    Optional<String> getFirstByTitle(String title,String animalType);
}
