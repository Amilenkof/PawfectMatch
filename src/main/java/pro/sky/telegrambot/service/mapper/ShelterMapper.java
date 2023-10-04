package pro.sky.telegrambot.service.mapper;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.List;

@Service
public class ShelterMapper {
    private final VolunteerService volunteerService;

    public ShelterMapper(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

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
