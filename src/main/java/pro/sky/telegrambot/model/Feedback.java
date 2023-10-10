package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    private String email;

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    public Feedback(String firstName, String email, String lastName, String phone) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.phone = phone;
    }
}
