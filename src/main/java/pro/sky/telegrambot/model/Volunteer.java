package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "chat_id")
    private Long chatId;
    @ManyToOne(fetch = FetchType.EAGER)
    private Shelter shelter;


}
