package pro.sky.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exceptions.AnimalNotFoundException;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.DTO.AnimalDTO;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.AnimalRepository;
import pro.sky.telegrambot.service.mapper.AnimalMapper;

@Service
@Slf4j
public class AnimalService {
    private final AnimalRepository animalRepository;

    private final ShelterService shelterService;
    private final AnimalMapper animalMapper;


    public AnimalService(AnimalRepository animalRepository, ShelterService shelterService, AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.shelterService = shelterService;
        this.animalMapper = animalMapper;
    }

    public AnimalDTO addAnimal(String type,
                               String name,
                               boolean isSick,
                               boolean isLittle,
                               boolean status) {
        Shelter shelter = shelterService.findShelterByAnimalType(type.toLowerCase()).orElseThrow(() -> new ShelterNotFoundException("Приют для указанного типа животных не найден"));
        Animal animal = new Animal(type, name, isSick, isLittle, status, shelter);
        animalRepository.save(animal);
        return animalMapper.toDto(animal);
    }


    public Animal findById(Long animalID) {
        return animalRepository.findById(animalID).orElseThrow(() -> new AnimalNotFoundException("Животное с указанным Id не найдено"));
    }
}
