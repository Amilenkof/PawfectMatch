package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shelter")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String address;

    private byte[] map;

    private String timing;

    @Column(name = "contacts_security")
    private String contractsSecurity;

    private String safety;

    @OneToMany(mappedBy = "shelter")
    private List<Volunteer> volunteer;


}
