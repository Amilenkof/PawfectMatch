package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Picture;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Optional<Picture> findSchemaByShelter_Id(Long shelterID);

    @Query(nativeQuery = true, value = "Select data from map where shelter_id=1")
    byte[] getbytes();


}
