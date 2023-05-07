package hu.soft4d.service;

import hu.soft4d.model.Cleaning;
import hu.soft4d.model.FoodStorage;
import hu.soft4d.repository.CleaningRepository;
import hu.soft4d.repository.FoodStorageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CleaningService {

    @Inject
    CleaningRepository cleaningRepository;

    @Inject
    FoodStorageRepository foodStorageRepository;

    public List<Cleaning> getAll(String foodStorageId) {
        return cleaningRepository.listAll(foodStorageId);
    }

    public Cleaning getLast(String foodStorageId) {
        return cleaningRepository.findLatest(foodStorageId);
    }

    public Cleaning getOne(String foodStorageId, String cleaningId) {
        return cleaningRepository.findById(foodStorageId, cleaningId);
    }

    @Transactional
    public Cleaning save(String foodStorageId, String userName, Cleaning cleaning) {
        Optional<FoodStorage> foodStorage = foodStorageRepository.findByIdOptional(foodStorageId);

        if (foodStorage.isEmpty()) {
            throw new EntityNotFoundException();
        }

        cleaning.setFoodStorage(foodStorage.get());
        cleaning.setCleanedBy(userName);
        cleaningRepository.persist(cleaning);
        return cleaning;
    }
}
