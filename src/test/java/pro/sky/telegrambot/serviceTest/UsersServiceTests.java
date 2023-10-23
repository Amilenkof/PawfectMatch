package pro.sky.telegrambot.serviceTest;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.repository.UsersRepository;
import pro.sky.telegrambot.service.AnimalService;
import pro.sky.telegrambot.service.UsersService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTests {
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private AnimalService animalService;
    @InjectMocks
    private UsersService usersService;


    @Test
    public void testFindByChatId() {
        Users users = new Users();
        when(usersRepository.findByChatId(anyLong())).thenReturn(Optional.of(users));
        Optional<Users> actual = usersService.findByChatId(1L);
        assertThat(actual.equals(Optional.of(users))).isTrue();
        verify(usersRepository).findByChatId(anyLong());
    }


    @Test
    public void testAddUsers() {
        Animal animal = mock(Animal.class);
        Users user = new Users(1L, "name", "lastname", "email", "phone", 30, animal, 0L);
        when(usersRepository.save(any(Users.class))).thenReturn(user);
        Users actual = usersService.addUsers(1L, "name", "lastname", "email", "phone", 1L);
        assertThat(actual.getChatId().equals(user.getChatId()) &&
                   actual.getFirstName().equals(user.getFirstName()) &&
                   actual.getLastName().equals(user.getLastName()) &&
                   actual.getEmail().equals(user.getEmail()) &&
                   actual.getPhone().equals(user.getPhone())).isTrue();
        verify(usersRepository).save(any(Users.class));
    }

    @Test
    public void testFindALlByDaysLostCounterIsAfter() {
        Animal animal1 = mock(Animal.class);
        Users user1 = new Users(1L, "name", "lastname", "email", "phone", 30, animal1, 0L);
        Animal animal2 = mock(Animal.class);
        Users user2 = new Users(1L, "name", "lastname", "email", "phone", 30, animal2, 0L);
        List<Users> list = List.of(user1, user2);
        when(usersRepository.findAllByDaysLostCounterIsAfter(anyLong())).thenReturn(list);

        Animal animal1After = mock(Animal.class);
        Users user1After = new Users(1L, "name", "lastname", "email", "phone", 30, animal1After, 1L);
        Animal animal2After = mock(Animal.class);
        Users user2After = new Users(1L, "name", "lastname", "email", "phone", 30, animal2After, 1L);
        List<Users> expected = List.of(user1, user2);

        List<Users> actual = usersService.findAllByDaysLostCounterIsAfter();
        assertThat(actual.stream().filter(expected::contains).count() == 2).isTrue();
        verify(usersRepository, times(2)).findAllByDaysLostCounterIsAfter(anyLong());
    }

    @Test
    public void testSetReportResultFirst() {
        Animal animal = new Animal();
        animal.setStatus(true);
        Users user = new Users(1L, "name", "lastname", "email", "phone", 0, animal, 200L);
        when(usersRepository.save(any(Users.class))).thenReturn(user);
        SendMessage expected = new SendMessage(1L, "Отличная работа!! Питомец стал полноправным членом Вашей семьи!");
        SendMessage actual = usersService.setReportResult(user, "Принять отчет");

        assertThat(user.getDaysLostCounter() == 0L).isTrue();
        assertThat(!user.getAnimal().isStatus()).isTrue();
        verify(usersRepository).save(any(Users.class));

    }

    @Test
    public void testSetReportResultSecond() {
        Animal animal = mock(Animal.class);
        animal.setStatus(true);
        Users user = new Users(1L, "name", "lastname", "email", "phone", 20, animal, 200L);
        when(usersRepository.save(any(Users.class))).thenReturn(user);
        SendMessage expected = new SendMessage(1L, "Отличная работа!! Питомец стал полноправным членом Вашей семьи!");
        SendMessage actual = usersService.setReportResult(user, "Принять отчет");
        assertThat(user.getDurationCounter() == 19L).isTrue();
        verify(usersRepository).save(any(Users.class));
    }
}
