package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private byte[] photo;

    private String food;

    private String health;

    private String behaviour;
    @OneToOne
    private Users user;

    public Report(byte[] photo, String food, String health, String behaviour, Users user) {
        this.photo = photo;
        this.food = food;
        this.health = health;
        this.behaviour = behaviour;
        this.user = user;
    }
}
