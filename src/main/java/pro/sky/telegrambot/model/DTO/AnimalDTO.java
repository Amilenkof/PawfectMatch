package pro.sky.telegrambot.model.DTO;

public record AnimalDTO(String type,
                        String name,
                        boolean isSick,
                        boolean isLittle,
                        ShelterDTOIN shelterDTOIN,
                        boolean status) {
}
