package hu.soft4d.service;

import hu.soft4d.model.ControlPoint;
import hu.soft4d.model.FoodStorage;
import hu.soft4d.repository.ControlPointRepository;
import org.apache.commons.beanutils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ControlPointService {

    @Inject
    ControlPointRepository controlPointRepository;

    @Inject
    FoodStorageService foodStorageService;

    public List<ControlPoint> listAll(String foodStorageId) {
        return controlPointRepository.listAll(foodStorageId);
    }

    public ControlPoint findById(String foodStorageId, String controlPointId) {
        return controlPointRepository.findByIdAndFoodStorage(foodStorageId, controlPointId);
    }


    @Transactional
    public void persist(String foodStorageId, ControlPoint controlPoint) {
        Optional<FoodStorage> foodStorage =  foodStorageService.findByIdOptional(foodStorageId);

        if (foodStorage.isEmpty()) {
            throw new EntityNotFoundException();
        }

        controlPoint.setFoodStorage(foodStorage.get());
        controlPointRepository.persist(controlPoint);
    }

    @Transactional
    public void update(String foodStorageId, ControlPoint controlPoint) {
        ControlPoint entity = findById(foodStorageId, controlPoint.getId());
        if (null == entity) {
            throw new NotFoundException();
        }

        entity.setDescription(controlPoint.getDescription());
        entity.setName(controlPoint.getName());
        entity.setLimitType(controlPoint.getLimitType());
        entity.setThreshold(controlPoint.getThreshold());

    }
}
