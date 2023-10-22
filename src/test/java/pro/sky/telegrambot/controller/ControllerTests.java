package pro.sky.telegrambot.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.PostgreSQLContainer;
import pro.sky.telegrambot.model.DTO.AnimalDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ControllerTests {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres")
            .withUsername("Bot")
            .withPassword("Bot")
            .withDatabaseName("PawfectMatch");

    @BeforeAll
    static void runContainer() {
        POSTGRE_SQL_CONTAINER.start();
    }

    @Test
    public void testAddAnimal(){
        AnimalDTO forObject = this.testRestTemplate.getForObject("http://localhost:" + port + "/animal?type=cat&name=testname&isSick=false&isLittle=false&status=false", AnimalDTO.class);
        Assertions.assertThat(forObject).isNotNull();


    }



}