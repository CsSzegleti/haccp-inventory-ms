package hu.soft4d.repository;

import hu.soft4d.model.InventoryItem;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class InventoryItemRepository implements PanacheRepositoryBase<InventoryItem, String> {
    public List<InventoryItem> listByFoodStorageIdAndMenuItemId(String foodStroageId, Long menuItemId) {
        return list("food_storage_id = ?1 and menu_item_id = ?2", Sort.by("created_date"), foodStroageId, menuItemId);
    }
}
