package pro.sky.telegrambot.model;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

/**
 * Класс предстваляет собой сущность ПРИЮТА для животных в java
 * Поля:
 * description - описание приюта
 * map - схема проезда
 * timing время работы
 * contractsSecurity - контакты охраны
 * safety - техника безопасности на территории приюта
 * volunteer список волонтеров в этом приюте
 * AnimalType - вид животных которые содержатся в приюте
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shelter")
@ToString
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String address;

    private String timing;

    @Column(name = "contacts_security")
    private String contractsSecurity;

    private String safety;

    @OneToMany(fetch =FetchType.LAZY,mappedBy = "shelter")
    private List<Volunteer> volunteer;
    @Setter
    private String animalType;


    /**
     * Конструктор для сервиса ShelterMapper
     */
    public Shelter(String description, String address, String timing, String contractsSecurity, String safety, String animalType) {
        this.description = description;
        this.address = address;
        this.timing = timing;
        this.contractsSecurity = contractsSecurity;
        this.safety = safety;
        this.animalType = animalType;
    }


}
