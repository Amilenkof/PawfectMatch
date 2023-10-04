package pro.sky.telegrambot.model;

import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;
@Entity
@Data
@NoArgsConstructor
public class Animal {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String type;

        private String name;

        private boolean isSick;

        private boolean isLittle;

        private boolean status;

        @ManyToOne(fetch = FetchType.LAZY)
        private Shelter shelter;
}
