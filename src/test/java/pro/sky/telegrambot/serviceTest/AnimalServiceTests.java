package pro.sky.telegrambot.serviceTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.DTO.AnimalDTO;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.AnimalRepository;
import pro.sky.telegrambot.service.AnimalService;
import pro.sky.telegrambot.service.ShelterService;
import pro.sky.telegrambot.service.mapper.AnimalMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTests {
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private ShelterService shelterService;
    @InjectMocks
    private AnimalService animalService;
    @Mock
    private AnimalMapper animalMapper;

    @Test
    public void testAddAnimal() {
        Shelter shelter = mock(Shelter.class);
        when(shelterService.findShelterByAnimalType(anyString())).thenReturn(Optional.of(shelter));
        Animal animal = new Animal("type", "name", false, false, false, shelter);
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);
        ShelterDTOIN shelterDTOIN = new ShelterDTOIN("description", "address", "1", "contacts", "safety", "cat");
        AnimalDTO expected = new AnimalDTO("type", "name", false, false, shelterDTOIN, false);
        when(animalMapper.toDto(any(Animal.class))).thenReturn(expected);
        AnimalDTO animalDTO = animalService.addAnimal("type", "name", false, false, false);
        Assertions.assertThat(animalDTO.equals(expected)).isTrue();
        verify(animalRepository).save(any(Animal.class));
    }

    @Test
    public void testFindById() {
        Shelter shelter = mock(Shelter.class);
        Animal animal = new Animal("type", "name", false, false, false, shelter);
        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal));
        Animal actual = animalService.findById(anyLong());
        Assertions.assertThat(actual.equals(animal)).isTrue();

    }
}
