package pro.sky.telegrambot.serviceTest;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.exceptions.MessageInReportUncorrectException;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.repository.ReportRepository;
import pro.sky.telegrambot.service.ReportService;
import pro.sky.telegrambot.service.UsersService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTests {
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private UsersService usersService;
    private final String regex = "^(.*?)\n(.*?)\n(.*?)$";
    @InjectMocks
    private ReportService reportService;
    private final String json = "{\"update_id\":513378858,\"message\":{\"message_id\":3543,\"from\":{\"id\":1107343760,\"is_bot\":false,\"first_name\":\"Aleksandr\",\"last_name\":\"Milenkov\",\"username\":\"Melook94\",\"language_code\":\"ru\"},\"date\":1697737543,\"chat\":{\"id\":9999,\"type\":\"private\",\"username\":\"Melook94\",\"first_name\":\"Aleksandr\",\"last_name\":\"Milenkov\"},\"text\":\"? ??????\"}}";
    private final Update update = BotUtils.fromJson(json, Update.class);


    @Test
    public void testGetTestReport() {
        Report report = mock(Report.class);
        when(reportRepository.findById(anyLong())).thenReturn(Optional.ofNullable(report));
        Assertions.assertThat(reportService.getTestReport().equals(report)).isTrue();
    }

    @Test
    public void testAddReport() throws URISyntaxException, IOException {
        when(usersService.findByChatId(update.message().chat().id())).thenReturn(Optional.of(new Users()));
        assertThatThrownBy(() -> reportService.addReport(update)).isInstanceOf(MessageInReportUncorrectException.class);
    }

    @Test
    public void TestFindAllTodayReports() {
        Report report1 = new Report();
        Report report2 = new Report();
        List<Report> expected = List.of(report2, report1);
        when(reportRepository.findReportByDateReportAfter(any())).thenReturn(expected);
        List<Report> actual = reportService.findAllTodayReports();
        assertThat(actual.equals(expected)).isTrue();
    }
}
