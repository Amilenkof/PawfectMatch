package pro.sky.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.exceptions.ShelterForThisAnimalTypeAlreadyHave;
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
    /**Метод получает dtoin с помощью ShelterMapper переводит обьект в Shelter и с помощью метода CRUDREPOSITORY.SAVE() сохраняет в БД
     * @param shelterDTOIN
     * @return Shelter entity
     */
    public Shelter createShelter (ShelterDTOIN shelterDTOIN) {
        shelterRepository.findShelterByAnimalType(shelterDTOIN.animalType()).orElseThrow(() -> new ShelterForThisAnimalTypeAlreadyHave("Приют для данного типа животных уже есть"));
        Shelter entity = shelterMapper.toEntity(shelterDTOIN);
        checkHaveShelter(entity);
        log.debug("В БД записывается,  entity={}",shelterDTOIN);
        return shelterRepository.save(entity);
    }
    public Shelter createShelter (String description,String address,String timing,String contactsSecurity,String safety,String animalType) {
        Shelter entity = new Shelter(description, address,  timing, contactsSecurity, safety, animalType.toLowerCase());
        checkHaveShelter(entity);
        log.debug("В БД записывается, entity={}",entity);
        return shelterRepository.save(entity);
    }
/**Метод проверяет есть ли в БД приют который, мы пытаемся создать, если есть выбрасывает ошибку ShelterForThisAnimalTypeAlreadyHave
 * @param entity
 * @throws ShelterForThisAnimalTypeAlreadyHave()-если приют указанного типа животных уже есть в БД
 * */
    private void checkHaveShelter(Shelter entity) {
        if(shelterRepository.findShelterByAnimalType(entity.getAnimalType()).isPresent()) {
            log.debug("Приют уже есть БД выбросится ошибка");
            throw new ShelterForThisAnimalTypeAlreadyHave("Приют для данного типа животных уже создан");
        }
    }

}
