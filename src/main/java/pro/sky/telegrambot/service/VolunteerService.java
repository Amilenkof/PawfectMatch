package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.exceptions.VolunteerListIsEmpty;
import pro.sky.telegrambot.model.Question;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.util.Optional;

@Service
@Slf4j
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final ShelterRepository shelterRepository;

    public VolunteerService(VolunteerRepository volunteerRepository, ShelterRepository shelterRepository) {
        this.volunteerRepository = volunteerRepository;
        this.shelterRepository = shelterRepository;
    }
//todo пишу - Миленьков А.

    /**
     * Метод получает волонтера из БД
     * Return = Optional<Volunteer>
     */
    @Transactional(readOnly = true)
    public Optional<Volunteer> callVolunteer(Update update, Shelter shelter) {
        return volunteerRepository.findAll().stream()
                .filter(volunteer -> volunteer.getShelter().getAnimalType().equals(shelter.getAnimalType()))
                .findFirst();
    }
}
