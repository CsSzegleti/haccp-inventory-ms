package hu.soft4d.repository;

import hu.soft4d.model.ControlPoint;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ControlPointRepository implements PanacheRepositoryBase<ControlPoint, String> {
    public List<ControlPoint> listAll(String foodStorageId) {
        return list("food_storage_id", foodStorageId);
    }

    public ControlPoint findByIdAndFoodStorage(String foodStorageId, String controlPointId) {
        return find("food_storage_id = ?1 and id = ?2", foodStorageId, controlPointId).firstResult();
    }
}
