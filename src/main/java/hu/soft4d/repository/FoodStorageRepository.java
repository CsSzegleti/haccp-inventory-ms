package hu.soft4d.repository;

import hu.soft4d.model.FoodStorage;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FoodStorageRepository implements PanacheRepositoryBase<FoodStorage, String> {

}
