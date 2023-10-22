package pro.sky.telegrambot.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.List;

@Service
@Slf4j
public class ShelterMapper {


    public ShelterMapper() {
    }


    public Shelter toEntity (ShelterDTOIN dtoIn) {
      return   new Shelter(dtoIn.description(),
                dtoIn.address(),
                dtoIn.timing(),
                dtoIn.contactsSecurity(),
                dtoIn.safety(),
                dtoIn.animalType().toLowerCase());

    }

    public ShelterDTOIN toDtoIn(Shelter shelter){
        return new ShelterDTOIN(shelter.getDescription(),
                shelter.getAddress(),
                shelter.getTiming(),
                shelter.getContractsSecurity(),
                shelter.getSafety(),
                shelter.getAnimalType());
    }
}
