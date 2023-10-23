package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE chat_id=?")
    Optional<Users> findByChatId(Long Chatid);

    List<Users> findAllByDaysLostCounterIsAfter(Long daysLost);
}
