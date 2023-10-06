package pro.sky.telegrambot.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Users;

@Repository
public interface UsersRepository extends JpaRepository <Users,Long> {
}
