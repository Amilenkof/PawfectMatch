package pro.sky.telegrambot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.repository.UsersRepository;

import java.util.Optional;

@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
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

}
