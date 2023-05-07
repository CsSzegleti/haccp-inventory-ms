package hu.soft4d.repository;

import hu.soft4d.model.Cleaning;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CleaningRepository implements PanacheRepositoryBase<Cleaning, String> {

    public List<Cleaning> listAll(String foodStorageId) {
        return list("food_storage_id", foodStorageId);
    }

    public Cleaning findLatest(String foodStorageId) {
        return list("food_storage_id", Sort.by("created_date"), foodStorageId).stream().findFirst().orElse(null);
    }

    public Cleaning findById(String foodStorageId, String cleaningId) {
        return find("food_storage_id = ?1 and id = ?2", foodStorageId, cleaningId).firstResult();
    }
}
