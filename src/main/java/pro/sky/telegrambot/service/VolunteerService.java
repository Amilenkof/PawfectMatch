package pro.sky.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.util.Optional;

@Service
@Slf4j
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;

    }


    /**
     * Метод получает волонтера из БД
     * Return = Optional<Volunteer>
     */
    @Transactional(readOnly = true)
    public Optional<Volunteer> callVolunteer(Shelter shelter) {
        return volunteerRepository.findAll().stream()
                .filter(volunteer -> volunteer.getShelter().getAnimalType().equals(shelter.getAnimalType()))
                .findFirst();
    }


}
