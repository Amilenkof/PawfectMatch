package pro.sky.telegrambot.serviceTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VolunteerServiceTests {
    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private ShelterRepository shelterRepository;
    @InjectMocks
    private VolunteerService volunteerService;


    @Test
    public void testCallVolunteer() {
        Shelter shelter = new Shelter();
        shelter.setAnimalType("cat");
        Volunteer volunteer = new Volunteer(1L, "name", "lastname", 1L, shelter);
        List<Volunteer> list = new ArrayList<>();
        list.add(volunteer);
        Mockito.when(volunteerRepository.findAll()).thenReturn(list);
        Optional<Volunteer> actual = volunteerService.callVolunteer(shelter);
        Assertions.assertThat(actual.equals(Optional.of(volunteer))).isTrue();
        verify(volunteerRepository).findAll();
    }


}
