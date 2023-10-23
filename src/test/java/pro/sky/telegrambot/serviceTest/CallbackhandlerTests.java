package pro.sky.telegrambot.serviceTest;

import com.pengrad.telegrambot.model.CallbackQuery;

import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.model.Users;
import pro.sky.telegrambot.service.CallBackHandler;
import pro.sky.telegrambot.service.UsersService;
import pro.sky.telegrambot.service.sheduler.SсhedulerService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CallbackhandlerTests {
    @Mock
    private SсhedulerService sсhedulerService;
    @Mock
    private UsersService usersService;
    @InjectMocks
    private CallBackHandler callBackHandler;

    @Test
    public void testHandle() {
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        when(update.callbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.data()).thenReturn("123");
        Report report = mock(Report.class);
        Users user = new Users();
        report.setUser(user);
        when(sсhedulerService.getLastReport()).thenReturn(report);
        callBackHandler.handle(update);
        verify(usersService).setReportResult(any(), any());
    }
}

