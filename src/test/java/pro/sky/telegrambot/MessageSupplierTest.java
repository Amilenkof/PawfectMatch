package pro.sky.telegrambot;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import org.hibernate.query.sqm.mutation.internal.UpdateHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class MessageSupplierTest {


    private Update getUpdate(String command) throws URISyntaxException, IOException {
        String json = Files.readString(Paths.get(Objects.requireNonNull(MessageSupplierTest.class.getResource("src/test/updateJson.json")).toURI()));
        String replaced = json.replace("%text%", command);
        return BotUtils.fromJson(replaced,Update.class);
    }
}
