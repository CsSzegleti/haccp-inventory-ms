package hu.soft4d.service;

import hu.soft4d.model.ControlPoint;
import hu.soft4d.model.ControlProperty;
import hu.soft4d.repository.ControlPointRepository;
import hu.soft4d.repository.ControlPropertyRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ControlPropertyService {

    @Inject
    ControlPropertyRepository controlPropertyRepository;

    @Inject
    ControlPointService controlPointService;

    public List<ControlProperty> getAll(String controlPointId) {
        return controlPropertyRepository.listAll(controlPointId);
    }

    public ControlProperty getLast(String controlPointId) {
        return controlPropertyRepository.findLatest(controlPointId);
    }

    public ControlProperty getOne(String controlPointId, String controlPropertyId) {
        return controlPropertyRepository.findById(controlPointId, controlPropertyId);
    }

    @Transactional
    public void persist(String foodStorageId, String controlPointId, String userName, ControlProperty property) {
        ControlPoint controlPoint = controlPointService.findById(foodStorageId, controlPointId);

        if (null == controlPoint) {
            throw new EntityNotFoundException();
        }

        property.setControlPoint(controlPoint);
        property.setAddedBy(userName);
        controlPropertyRepository.persist(property);
    }
}
