package pro.sky.telegrambot.service.mapper;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Animal;
import pro.sky.telegrambot.model.DTO.AnimalDTO;

@Service
public class AnimalMapper {
    private final ShelterMapper shelterMapper;

    public AnimalMapper(ShelterMapper shelterMapper) {
        this.shelterMapper = shelterMapper;
    }

    public AnimalDTO toDto(Animal animal) {
        return new AnimalDTO(animal.getType(),
                animal.getName(),
                animal.isSick(),
                animal.isLittle(),
                shelterMapper.toDtoIn(animal.getShelter()),
                animal.isStatus());
    }
}
