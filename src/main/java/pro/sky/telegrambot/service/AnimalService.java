package pro.sky.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exceptions.AnimalNotFoundException;
import pro.sky.telegrambot.exceptions.ShelterNotFoundException;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.AnimalRepository;


import java.util.Optional;

@Service
@Slf4j
public class AnimalService {
    private final AnimalRepository animalRepository;

    private final ShelterService shelterService;

    public AnimalService(AnimalRepository animalRepository, ShelterService shelterService) {
        this.animalRepository = animalRepository;

        this.shelterService = shelterService;
    }

    public Animal addAnimal( String type,
                             String name,
                             boolean isSick,
                             boolean isLittle,
                             boolean status){
        Shelter shelter = shelterService.findShelterByAnimalType(type).orElseThrow(() -> new ShelterNotFoundException("Приют для указанного типа животных не найден"));
        Animal animal = new Animal(type,name,isSick,isLittle,status,shelter);
        return animalRepository.save(animal);
    }


    public Animal findById(Long animalID) {
      return animalRepository.findById(animalID).orElseThrow(() -> new AnimalNotFoundException("Животное с указанным Id не найдено"));
    }
}
