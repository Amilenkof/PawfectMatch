package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
    private LocalDateTime dateReport;
    @OneToOne
    private Users user;

    public Report(byte[] photo, String food, String health, String behaviour, LocalDateTime dateReport, Users user) {
        this.photo = photo;
        this.food = food;
        this.health = health;
        this.behaviour = behaviour;
        this.dateReport = dateReport;
        this.user = user;
    }
}
