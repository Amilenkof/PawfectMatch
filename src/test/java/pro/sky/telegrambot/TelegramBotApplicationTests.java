package pro.sky.telegrambot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.telegrambot.controller.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TelegramBotApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private AnimalController animalController;
    @Autowired
    private PictureController pictureController;
    @Autowired
    private RecommendationController recommendationController;
    @Autowired
    private ShelterController shelterController;
    @Autowired
    private UsersController usersController;
	@Autowired
	private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
		Assertions.assertThat(animalController).isNotNull();
		Assertions.assertThat(pictureController).isNotNull();
		Assertions.assertThat(recommendationController).isNotNull();
		Assertions.assertThat(shelterController).isNotNull();
		Assertions.assertThat(usersController).isNotNull();
    }

}
