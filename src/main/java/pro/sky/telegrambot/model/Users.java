package pro.sky.telegrambot.model;

import lombok.*;
import jakarta.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phone;

    @Column(name = "duration_counter")//сколько дней  нужно проверять отчеты о животном от этого пользователя
    private Integer durationCounter;

    @OneToOne
    private Animal animal;
    @Setter
    @Getter
    private Long daysLostCounter;//колво дней когда пользователь не присылал отчеты

    public Users(String firstName, String lastName, String email, String phone, Integer durationCounter, Animal animal, Long daysLostCounter) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.durationCounter = durationCounter;
        this.animal = animal;
        this.daysLostCounter = daysLostCounter;
    }
}
