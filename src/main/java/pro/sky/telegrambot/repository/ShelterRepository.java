package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Shelter;

import java.lang.annotation.Native;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {



    @Query(nativeQuery = true, value = "SELECT * FROM shelter WHERE animal_type=?")
//    @Query("FROM Shelter s JOIN FETCH Animal  a a.")
    Optional<Shelter> findShelterByAnimalType(String animalType);


}
