package pro.sky.telegrambot.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.exceptions.ShelterForThisAnimalTypeAlreadyHaveException;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.service.ShelterService;
import pro.sky.telegrambot.service.mapper.ShelterMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTests {
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private ShelterMapper shelterMapper;
    @InjectMocks
    private ShelterService shelterService;

    @Test
    public void testfindShelterByAnimalType(){
        String type = "cat";
        Shelter shelter = new Shelter();
        when(shelterRepository.findShelterByAnimalType(type)).thenReturn(Optional.of(shelter));
        assertThat(shelterService.findShelterByAnimalType(type).equals(Optional.of(shelter))).isTrue();
        verify(shelterRepository).findShelterByAnimalType(type);
    }


    @Test
    public void testCreateShelter(){
        String decription = "description";
        String address = "address";
        String timing= "timing";
        String contacts= "contacts";
        String safety= "safety";
        String type = "cat";
        Shelter shelter = new Shelter();
        ShelterDTOIN dtoin = new ShelterDTOIN(decription,address,timing,contacts,safety,type);
        when(shelterRepository.findShelterByAnimalType(type)).thenReturn(Optional.of(shelter));
        when(shelterMapper.toEntity(dtoin)).thenReturn(shelter);
        when(shelterRepository.save(shelter)).thenReturn(shelter);
        Shelter result = shelterService.createShelter(dtoin);
        assertThat(result.equals(shelter)).isTrue();
        verify(shelterRepository).findShelterByAnimalType(type);
        verify(shelterRepository).save(shelter);


    }
    @Test
    public void testCreateShelterReload(){
        String decription = "description";
        String address = "address";
        String timing= "timing";
        String contacts= "contacts";
        String safety= "safety";
        String type = "cat";
        Shelter shelter = new Shelter(decription, address, timing, contacts, safety, type);
        ShelterDTOIN dtoin = new ShelterDTOIN(decription,address,timing,contacts,safety,type);

        when(shelterRepository.findShelterByAnimalType(type)).thenReturn(Optional.empty());
        when(shelterRepository.save(shelter)).thenReturn(shelter);
        Shelter resultReload = shelterService.createShelter(decription, address, timing, contacts, safety, type);
        assertThat(resultReload.equals(shelter)).isTrue();
        verify(shelterRepository).findShelterByAnimalType(type);
        verify(shelterRepository).save(shelter);
    }
    @Test
    public void testCheckshelterThrowException(){
        String decription = "description";
        String address = "address";
        String timing= "timing";
        String contacts= "contacts";
        String safety= "safety";
        String type = "cat";
        Shelter shelter = new Shelter(decription, address, timing, contacts, safety, type);
        ShelterDTOIN dtoin = new ShelterDTOIN(decription,address,timing,contacts,safety,type);
        when(shelterRepository.findShelterByAnimalType(type)).thenReturn(Optional.of(shelter));
        when(shelterMapper.toEntity(dtoin)).thenReturn(shelter);
        assertThatThrownBy(()->shelterService.createShelter(dtoin)).isInstanceOf(ShelterForThisAnimalTypeAlreadyHaveException.class);
    }


}
