package pro.sky.telegrambot.serviceTest.keyboardTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.AnimalRepository;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.service.AnimalService;
import pro.sky.telegrambot.service.ShelterService;
import pro.sky.telegrambot.service.mapper.ShelterMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTests {
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private ShelterMapper shelterMapper;
    @InjectMocks
    private ShelterService shelterService;
    @InjectMocks
    private AnimalService animalService;


    @Test
    public  void testAddAnimal(){
        String type = "cat";
        String name = "Fluffy";
        boolean isSick = false;
        boolean isLittle = false;
        boolean status = true;
        Shelter shelter = new Shelter();
        Mockito.when(shelterRepository.findShelterByAnimalType(type)).thenReturn(Optional.of(shelter));
        Mockito.when(shelterService.findShelterByAnimalType(type)).thenReturn(Optional.of(shelter));
        Animal animal = animalService.addAnimal(type, name, isSick, isLittle, status);
        Assertions.assertNotNull(animal);
//        Mockito.verify(animalRepository).save(Mockito.any(Mockito.class));
    }
}
