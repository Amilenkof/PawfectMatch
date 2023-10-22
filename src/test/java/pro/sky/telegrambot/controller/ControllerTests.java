package pro.sky.telegrambot.controller;

import jakarta.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.testcontainers.containers.PostgreSQLContainer;
import pro.sky.telegrambot.model.DTO.AnimalDTO;
import pro.sky.telegrambot.model.DTO.ShelterDTOIN;
import pro.sky.telegrambot.model.Recommendation;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.model.Users;

import static org.assertj.core.api.Assertions.*;

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

    @PostConstruct
    void init() {

            testRestTemplate.getForObject("http://localhost:" + port + "/shelter?description=description&address=address&timing=1&contactsSecurity=contacts&safety=safety&animalType=straus", Shelter.class);
            testRestTemplate.getForObject("http://localhost:" + port + "/animal?type=straus&name=test&isSick=false&isLittle=false&status=false", AnimalDTO.class);

    }
    @Test
    public void test1AddAnimal() {
        AnimalDTO actual = this.testRestTemplate.getForObject("http://localhost:" + port + "/animal?type=straus&name=testname&isSick=false&isLittle=false&status=false", AnimalDTO.class);
        ShelterDTOIN shelterDTOIN = new ShelterDTOIN("description", "address", "1", "contacts", "safety", "straus");
        AnimalDTO expected = new AnimalDTO("straus", "testname", false, false, shelterDTOIN, false);
        assertThat(actual).isNotNull();
        assertThat(actual.equals(expected)).isTrue();
    }
    @Test
    public void testAddRecommedantion(){
        Recommendation actual = this.testRestTemplate.getForObject("http://localhost:" + port + "/recommendation?title=title&text=text&animalType=straus1", Recommendation.class);
        Recommendation expected = new Recommendation("title", "text", "straus1");
        assertThat(actual.getAnimalType().equals(expected.getAnimalType())&&
                   actual.getTitle().equals(expected.getTitle())&&
                   actual.getText().equals(expected.getText())).isTrue();
    }

    @Test
    void testCreateShelter(){

        Shelter actual = testRestTemplate.getForObject("http://localhost:" + port + "/shelter?description=description&address=address&timing=1&contactsSecurity=contacts&safety=safety&animalType=tiger", Shelter.class);
        Shelter expected = new Shelter("description", "address", "1", "contacts", "safety", "tiger");
        assertThat(actual.getSafety().equals("safety") &&
                   actual.getAnimalType().equals("tiger") &&
                   actual.getDescription().equals("description") &&
                   actual.getAddress().equals("address")&&
                   actual.getTiming().equals("1")&&
                   actual.getContractsSecurity().equals("contacts")).isTrue();
    }


}
//    public ResponseEntity<Shelter> createShelter(@RequestParam String description,
//                                                 @RequestParam String address,
//                                                 @RequestParam String timing,
//                                                 @RequestParam String contactsSecurity,
//                                                 @RequestParam String safety,
//                                                 @RequestParam String animalType) {
//        log.info("Вызван метод ShelterController.createShelter String description ={}," +
//                 "String address ={}," +
//                 "String timing ={}," +
//                 "String contactsSecurity ={}," +
//                 "String animalType ={}", description, address, timing, contactsSecurity, safety, animalType);
//
//        return ResponseEntity.ok(shelterService.createShelter(description, address, timing, contactsSecurity, safety, animalType));
//    }