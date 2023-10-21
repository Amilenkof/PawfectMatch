package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exceptions.PhotoInReportNotFoundException;

@Service
@Slf4j
public class DataGetter {
    private final TelegramBot telegramBot;

    public DataGetter(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
    /**

     * Метод из сообщения пользователя извлекает файл с фото и получает из него массив байт

     * @param update
     * @return byte[]
     * @throws PhotoInReportNotFoundException если не передана фотография животного в отчете
     */
    @SneakyThrows
    public byte[] getPhoto(Update update) {
        PhotoSize[] photo = update.message().photo();
        PhotoSize photoSize = photo[photo.length - 1];
        GetFile getFile = new GetFile(photoSize.fileId());
        return telegramBot.getFileContent(telegramBot.execute(getFile).file());
    }


}
