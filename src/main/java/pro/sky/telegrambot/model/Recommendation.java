package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String title;
    @Getter

    private String text;
    private String animalType;

    public Recommendation(String title, String text, String animalType) {
        this.title = title;
        this.text = text;
        this.animalType = animalType;
    }
}
