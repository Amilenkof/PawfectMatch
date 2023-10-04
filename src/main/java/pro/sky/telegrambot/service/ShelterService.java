package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.service.mapper.ShelterMapper;

import java.util.Optional;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final ShelterMapper shelterMapper;

    public ShelterService(ShelterRepository shelterRepository, ShelterMapper shelterMapper) {
        this.shelterRepository = shelterRepository;
        this.shelterMapper = shelterMapper;
    }


    @Transactional(readOnly = true)
    public Optional <Shelter> findShelterByAnimalType(String animalType){
        return shelterRepository.findShelterByAnimalType(animalType);
    }

    public Shelter createShelter (ShelterDTOIN shelterDTOIN) {

        return shelterRepository.save(shelterMapper.toEntity(shelterDTOIN));
    }

}
