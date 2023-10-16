package pro.sky.telegrambot.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Класс описывает картинку-схему проезда к приюту
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] data;
    private String filePath;
    private Long fileSize;
    private String mediaType;
    @OneToOne
    private Shelter shelter;
}
