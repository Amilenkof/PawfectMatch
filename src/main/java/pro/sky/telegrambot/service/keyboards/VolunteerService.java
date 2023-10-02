//package pro.sky.telegrambot.service.keyboards;
//
//import org.springframework.stereotype.Service;
//import pro.sky.telegrambot.model.Volunteer;
//import pro.sky.telegrambot.repository.VolunteerRepository;
//
//import java.util.Optional;
//
//@Service
//public class VolunteerService {
//
//    private final VolunteerRepository volunteerRepository;
//
//    public VolunteerService(VolunteerRepository volunteerRepository) {
//        this.volunteerRepository = volunteerRepository;
//    }
//
//    /**
//     * Метод получает волонтера из БД
//     * Return = Optional<Volunteer>
//     */
//    public Optional<Volunteer> callVolunteer() {
//        return volunteerRepository.findAll().stream().findAny();
//    }
//}
