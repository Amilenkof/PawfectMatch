package pro.sky.telegrambot.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import jakarta.persistence.*;

import java.util.List;

import jakarta.persistence.*;

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

    private String map;

    private String timing;

    @Column(name = "contacts_security")
    private String contractsSecurity;

    private String safety;

    @OneToMany(mappedBy = "shelter")
    private List<Volunteer> volunteer;
    @Setter
    private String AnimalType;

    public Shelter(String description, String address, String map, String timing, String contractsSecurity, String safety, String animalType) {
        this.description = description;
        this.address = address;
        this.map = map;
        this.timing = timing;
        this.contractsSecurity = contractsSecurity;
        this.safety = safety;
        AnimalType = animalType;
    }

    public int getVolunteerCount() {
        return volunteer.size();
    }
}
