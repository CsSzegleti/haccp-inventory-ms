package hu.soft4d.service;

import hu.soft4d.model.FoodStorage;
import hu.soft4d.model.InventoryItem;
import hu.soft4d.repository.FoodStorageRepository;
import hu.soft4d.repository.InventoryItemRepository;
import hu.soft4d.resource.dto.InventoryItemToMoveDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FoodStorageService {

    @Inject
    FoodStorageRepository foodStorageRepository;

    @Inject
    InventoryItemRepository inventoryItemRepository;

    public Optional<FoodStorage> findByIdOptional(String id) {
        return foodStorageRepository.findByIdOptional(id);
    }

    public void persist(FoodStorage foodStorage) {
        foodStorageRepository.persist(foodStorage);
    }

    public List<FoodStorage> listAll() {
        return foodStorageRepository.listAll();
    }

    public void deleteById(String id) {
        foodStorageRepository.deleteById(id);
    }

    @Transactional
    public List<String> addMultipleItemsToStorage(String storageId, InventoryItemToMoveDto itemToAdd) {
        List<String> items = new LinkedList<>();
        FoodStorage storage = foodStorageRepository.findById(storageId);
        try{
            for (int i = 0; i < itemToAdd.getQuantity(); i++) {
                InventoryItem item = new InventoryItem();
                item.setFoodStorage(storage);
                item.setMenuItemId(itemToAdd.getMenuItemId());

                inventoryItemRepository.persist(item);
                items.add(item.getId());
            }
        } catch (EntityNotFoundException ex) {
            throw new NotFoundException();
        }

        return items;
    }

    @Transactional
    public List<String> removeMultipleItemsFromStorage(String storageId, InventoryItemToMoveDto itemToMoveDto) {
        List<String> itemIds = new LinkedList<>();
        FoodStorage storage = foodStorageRepository.findById(storageId);

        var items = inventoryItemRepository.listByFoodStorageIdAndMenuItemId(storageId, itemToMoveDto.getMenuItemId());

        for (int i = 0; i < Math.min(itemToMoveDto.getQuantity(), items.size()); i++) {
            inventoryItemRepository.deleteById(items.get(i).getId());
        }

        return itemIds;
    }
}
