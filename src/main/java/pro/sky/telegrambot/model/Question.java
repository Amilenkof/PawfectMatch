package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
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
