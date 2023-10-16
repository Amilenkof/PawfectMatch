package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Recommendation;
import pro.sky.telegrambot.model.Volunteer;

import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM volunteer")
    List<Volunteer> findVolunteerByAnimalType();
}
