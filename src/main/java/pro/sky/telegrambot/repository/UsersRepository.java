package pro.sky.telegrambot.repository;

import com.fasterxml.jackson.annotation.OptBoolean;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE chat_id=?")
    Optional<Users> findByChatId(Long Chatid);
}
