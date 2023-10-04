package pro.sky.telegrambot.service.keyboards;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.model.Shelter;
import pro.sky.telegrambot.repository.ShelterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

//    public List<Shelter> find(){
//        return shelterRepository.findAll();
//    }
    @Transactional(readOnly = true)
    public Optional <Shelter> findShelterByAnimalType(String animalType){
        return shelterRepository.findShelterByAnimalType(animalType);
    }

}
