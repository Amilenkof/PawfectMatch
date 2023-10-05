package pro.sky.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.service.mapper.ShelterMapper;

import java.util.Optional;

@Service
@Slf4j
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final ShelterMapper shelterMapper;

    public ShelterService(ShelterRepository shelterRepository, ShelterMapper shelterMapper) {
        this.shelterRepository = shelterRepository;
        this.shelterMapper = shelterMapper;
    }

/**Метод получает из БД shelter по animalType
 * Внутри содержит native SQL QUERY:
 * """SQL
 *{@code  SELECT * FROM shelter WHERE animal_type=?}
 * """*/
    @Transactional(readOnly = true)
    public Optional <Shelter> findShelterByAnimalType(String animalType){
        log.debug("Вызван метод ShelterService.findShelterByAnimalType, animalType={}",animalType);
        return shelterRepository.findShelterByAnimalType(animalType);
    }
    /**Метод получает dtoin с помощью ShelterMapper переводит обьект в Shelter и с помощью метода CRUREPOSITORY.SAVE() сохраняет в БД
     * PARAMS =ShelterDTOIN shelterDTOIN
     * RETURN =Shelter entity
     */
    public Shelter createShelter (ShelterDTOIN shelterDTOIN) {
        log.debug("Вызван метод ShelterService.findShelterByAnimalType, shelterDTOIN={}",shelterDTOIN);
        Shelter entity = shelterMapper.toEntity(shelterDTOIN);
        log.debug("entity={}",shelterDTOIN);
        return shelterRepository.save(entity);
    }

}
