package pro.sky.telegrambot.serviceTest;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Value;
import pro.sky.telegrambot.MessageSupplierTest;
import pro.sky.telegrambot.exceptions.MessageInReportUncorrectException;
import pro.sky.telegrambot.exceptions.ShelterForThisAnimalTypeAlreadyHaveException;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.repository.ReportRepository;
import pro.sky.telegrambot.service.ReportService;
import pro.sky.telegrambot.service.UsersService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTests {
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private UsersService usersService;
    @Value("${path.to.defaultReportPicture}")
    private String defaultReportPicture;

    private final String regex = "^(.*?)\n(.*?)\n(.*?)$";
    @InjectMocks
    private ReportService reportService;


    private final Pattern pattern = Pattern.compile(regex);

//    private Update getUpdate(String command) throws URISyntaxException, IOException {
////        Path path = Paths.get(Objects.requireNonNull(this.getClass().getResource("src/main/resources/updateJson.json")).toURI());
//        Path path1 = Path.of("src/main/resources/updateJson.json");
//        String json = Files.readString(path1);
////        String replaced = json.replace("%text%", command);
//        return BotUtils.fromJson(json,Update.class);
//    }

    @Test
    public void testGetTestReport() {
        Report report = mock(Report.class);
        when(reportRepository.findById(anyLong())).thenReturn(Optional.ofNullable(report));
        Assertions.assertThat(reportService.getTestReport().equals(report)).isTrue();
    }

    @Test
    public void testAddReport() throws URISyntaxException, IOException {
        byte[] arr = new byte[1];
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        Users users = new Users();
        when(usersService.findByChatId(anyLong())).thenReturn(Optional.of(users));
        when(update.message().caption()).thenReturn("строка1\nстрока2\nстрока3");
        when(reportService.getPhoto(any(Update.class))).thenReturn(arr);
        Report actual = reportService.addReport(any(Update.class));
        Report expected = new Report(arr, "строка1", "строка2", "строка3", LocalDateTime.now(), users);
        Assertions.assertThat(actual.getBehaviour().equals(expected.getBehaviour()) &&
                              actual.getUser().equals(expected.getUser()) &&
                              actual.getHealth().equals(expected.getHealth()) &&
                              actual.getFood().equals(expected.getFood())).isTrue();

    }
    @Test
    public void TestFindAllTodayReports(){
        Report report1 = new Report();
        Report report2 = new Report();
        List<Report> expected = List.of(report2, report1);
        when(reportRepository.findReportByDateReportAfter(any())).thenReturn(expected);
        List<Report> actual = reportService.findAllTodayReports();
        assertThat(actual.equals(expected)).isTrue();
    }
}
