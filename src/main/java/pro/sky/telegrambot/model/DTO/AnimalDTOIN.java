package pro.sky.telegrambot.model.DTO;

import pro.sky.telegrambot.model.Shelter;

public record AnimalDTOIN(String type,
                          String name,
                          boolean isSick,
                          boolean isLittle,
                          ShelterDTOIN shelterDTOIN,
                          boolean status) {
}
