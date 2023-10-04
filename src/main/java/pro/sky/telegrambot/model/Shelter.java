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

    private byte[] map;

    private String timing;

    @Column(name = "contacts_security")
    private String contractsSecurity;

    private String safety;

    @OneToMany(mappedBy = "shelter")
    @LazyCollection(LazyCollectionOption. EXTRA)
    private List<Volunteer> volunteer;
    @Setter
    private  String AnimalType;



    public int getVolunteerCount(){
       return volunteer.size();
    }
}
