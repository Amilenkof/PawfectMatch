package pro.sky.telegrambot.service.keyboards;

import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exceptions.VolunteerListIsEmpty;
import pro.sky.telegrambot.model.Question;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.repository.VolunteerRepository;

import java.util.Optional;

@Service
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
//    public Optional<Volunteer> callVolunteer(Update update) {
//        String username = update.message().chat().username();
//
//        new Question().builder()
//                .shelter(shelterRepository.findAll().stream().findFirst().orElseThrow(() ->  new VolunteerListIsEmpty("Список волонтеров пуст")))
//                .questionText("");
//        return null;
//    }
}
