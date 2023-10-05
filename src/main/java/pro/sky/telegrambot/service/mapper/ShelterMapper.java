package pro.sky.telegrambot.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.List;
/**Класс для преобразования dto в entity - и обратно */
@Service
@Slf4j
public class ShelterMapper {
    private final VolunteerService volunteerService;

    public ShelterMapper(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }
/**Метод преобразует dtoIn сущность в Entity
 * Params SHELTER DTOIn
 * RETURN SHELTER shelter
 * {@See не заполняет поля Long Id,List<Volunteer> volunteer;}*/

    public Shelter toEntity (ShelterDTOIN dtoIn) {
      return   new Shelter(dtoIn.description(),
                dtoIn.adress(),
                dtoIn.map(),
                dtoIn.timing(),
                dtoIn.contactsSercurity(),
                dtoIn.safety(),
                dtoIn.animalType());

    }
}
