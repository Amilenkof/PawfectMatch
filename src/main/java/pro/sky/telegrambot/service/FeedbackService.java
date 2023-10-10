package pro.sky.telegrambot.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Feedback;
import pro.sky.telegrambot.repository.FeedbackRepository;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    private final String regex = "^(.*?),(.*?),(.*?),(.*?)$";

    private final Pattern pattern = Pattern.compile(regex);


    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

/**Метод получает строку от клиента, обрабатывает ее, если она соответствует шаблону, помещает в БД задание связаться с пользователем
 * Params String message- сообщение пользователя
 * Return Optional<Feedback>-если сообщение успешно обработано и добавлено в БД,
 * Optional.empty- если сообщение не удалось обработать
 * */
    public Optional<Feedback> addFeedback(String message) {
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String lastName = matcher.group(1).toLowerCase(Locale.ROOT);
            String firstName = matcher.group(2).toLowerCase(Locale.ROOT);
            String email = matcher.group(3).toLowerCase(Locale.ROOT);
            String phoneNumber = matcher.group(4).toLowerCase(Locale.ROOT);
            return Optional.of(feedbackRepository.save(new Feedback(
                    StringUtils.capitalize(firstName.trim()),
                    email.trim(),
                    StringUtils.capitalize(lastName.trim()),
                    phoneNumber.trim())));
        }
        return Optional.empty();
    }
}