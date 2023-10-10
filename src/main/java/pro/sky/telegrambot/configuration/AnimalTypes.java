package pro.sky.telegrambot.configuration;

import lombok.Getter;
import lombok.Setter;
//todo нужен вообще?
@Getter

public enum AnimalTypes {

    dog ("dog"),

    cat("cat");

    private final String current;


    AnimalTypes(String current) {
        this.current = current;
    }



}
