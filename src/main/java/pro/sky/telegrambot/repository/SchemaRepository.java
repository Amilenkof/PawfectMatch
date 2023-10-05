package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Schema;

import java.util.Map;
import java.util.Optional;

@Repository
public interface SchemaRepository extends JpaRepository<Schema,Long> {

    Optional<Schema> findSchemaByShelter_Id(Long shelterID);


}
