package pro.sky.telegrambot.service;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.exceptions.AnimalNotFoundException;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;
    private final AnimalService animalService;
    @org.springframework.beans.factory.annotation.Value("${duration.counter}")
    private  int DURATION_COUNTER;

    public UsersService(UsersRepository usersRepository, AnimalService animalService) {
        this.usersRepository = usersRepository;
        this.animalService = animalService;
    }


    /**
     * Метод ищет Users по ChatId в БД
     *
     * @param chatId
     * @return Optional<Users>
     */
//    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE chat_id=?")
    @Transactional(readOnly = true)
    public Optional<Users> findByChatId(Long chatId) {
        return usersRepository.findByChatId(chatId);

    }


    /**Метод для создания пользователей
     * использует констранту DURATION_COUNTER из application_properties- колво дней, которое нужно сдавать отчеты после усыновления питомца
     * */
    public Users addUsers(Long chatId, String firstName, String lastName, String email, String phone, Long animalID) throws AnimalNotFoundException  {
            return usersRepository.save(new Users(chatId,firstName, lastName, email, phone, DURATION_COUNTER,animalService.findById(animalID), 0L));
    }


    /**Метод сначало получает выборку всех пользоватлей, увеличивает им задержку по отчетам на 1 день,
     * После делает выборку по людям у которых просрочка по отчетам более 2 дней
     * Возвращает список всех должников по отчетам*/
    public List<Users> findAllByDaysLostCounterIsAfter (){
        List<Users> allUsers = usersRepository.findAllByDaysLostCounterIsAfter(0L);
        allUsers.forEach(user -> user.setDaysLostCounter(user.getDaysLostCounter() + 1));
        List<Users> userWithLostForReports = usersRepository.findAllByDaysLostCounterIsAfter(2L);
        return userWithLostForReports;
    }

}
