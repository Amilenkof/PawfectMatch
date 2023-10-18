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
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
}
//    public Report addReport(Update update) {
//        log.debug("Вызван метод ReportService.addReport");
//        Long chatId = update.message().chat().id();
//        Optional<Users> optionalUser = usersService.findByChatId(chatId);
//        Users users = optionalUser.orElseThrow(() -> new UsersNotFoundException("Пользователь с таким ChatID не обнаружен"));
//        String text = update.message().caption();
//        if (text == null) throw new MessageInReportUncorrectException("В отчете отсутствует описание");
//        Matcher matcher = pattern.matcher(text);
//        if (matcher.find()) {
//            String food = matcher.group(1);
//            String health = matcher.group(2);
//            String behavior = matcher.group(3);
//            Report report = new Report(getPhoto(update), food, health, behavior, LocalDateTime.now(), users);
//            addReportToDB(report);
//            return report;
//        }
//        throw new MessageInReportUncorrectException("Не удалось привести сообщение к виду регулярного выражения");
//    }
//    @SneakyThrows
//    public byte[] getPhoto(Update update) {
//        PhotoSize[] photo = update.message().photo();
//        PhotoSize photoSize = photo[photo.length - 1];
//        GetFile getFile = new GetFile(photoSize.fileId());
//        return telegramBot.getFileContent(telegramBot.execute(getFile).file());
//    }
//    public Report addReport(Update update) {
//        log.debug("Вызван метод ReportService.addReport");
//        Long chatId = update.message().chat().id();
//        Optional<Users> optionalUser = usersService.findByChatId(chatId);
//        Users users = optionalUser.orElseThrow(() -> new UsersNotFoundException("Пользователь с таким ChatID не обнаружен"));
//        String text = update.message().caption();
//        if (text == null) throw new MessageInReportUncorrectException("В отчете отсутствует описание");
//        Matcher matcher = pattern.matcher(text);
//        if (matcher.find()) {
//            String food = matcher.group(1);
//            String health = matcher.group(2);
//            String behavior = matcher.group(3);
//            Report report = new Report(getPhoto(update), food, health, behavior, LocalDateTime.now(), users);
//            addReportToDB(report);
//            return report;
//        }
//        throw new MessageInReportUncorrectException("Не удалось привести сообщение к виду регулярного выражения");
//    }