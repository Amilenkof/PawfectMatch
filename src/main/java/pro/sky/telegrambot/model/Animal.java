package pro.sky.telegrambot.model;

import lombok.Builder;
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

        private boolean status;//true- в приюте, false -усыновлен

        @ManyToOne(fetch = FetchType.LAZY)
        private Shelter shelter;

        public Animal(String type, String name, boolean isSick, boolean isLittle, boolean status, Shelter shelter) {
                this.type = type;
                this.name = name;
                this.isSick = isSick;
                this.isLittle = isLittle;
                this.status = status;
                this.shelter = shelter;
        }
}
