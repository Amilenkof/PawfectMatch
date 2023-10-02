package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "not_reported")
public class NotReported {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "days_lost")
    private Integer daysLost;
    @OneToOne
    private Users user;
}
