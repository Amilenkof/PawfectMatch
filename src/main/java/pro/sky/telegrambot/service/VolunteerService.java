package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final ShelterRepository shelterRepository;

    public VolunteerService(VolunteerRepository volunteerRepository, ShelterRepository shelterRepository) {
        this.volunteerRepository = volunteerRepository;
        this.shelterRepository = shelterRepository;
    }


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
    /**Метод получает из БД список волонтеров по указанному типу животных
     * @param animalType
     * @return List<Volunteer>*/
    @Transactional(readOnly = true)
    public List<Volunteer> findVolunteerByAnimalType(String animalType){
      return   volunteerRepository.findVolunteerByAnimalType().stream()
                .filter(volunteer -> volunteer.getShelter().getAnimalType().equals(animalType))
                .collect(Collectors.toList());
    }
}
