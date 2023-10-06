package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
@Builder
/**Класс отражает сущность вопроса, который отправляют волонтеры пользователи */
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Shelter shelter;
    @OneToOne
    private Volunteer volunteer;
    private String questionText;

}
