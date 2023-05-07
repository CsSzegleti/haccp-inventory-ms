package hu.soft4d.repository;

import hu.soft4d.model.ControlProperty;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ControlPropertyRepository implements PanacheRepositoryBase<ControlProperty, String> {

    public List<ControlProperty> listAll(String controlPointId) {
        return list("control_point_id", controlPointId);
    }

    public ControlProperty findLatest(String controlPointId) {
        return list("control_point_id", Sort.by("created_date"), controlPointId).stream().findFirst().orElse(null);
    }

    public ControlProperty findById(String controlPointId, String controlPropertyId) {
        return (ControlProperty) find("control_point_id = ?1 and id = ?2", controlPointId, controlPropertyId);
    }
}
