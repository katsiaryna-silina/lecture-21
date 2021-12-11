package by.itacademy.car.rental.silina.dao.impl;

import by.itacademy.car.rental.silina.dao.DAO;
import by.itacademy.car.rental.silina.entity.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@Slf4j
public class CarModelDAOImpl implements DAO<CarModel> {
    @PersistenceContext
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public CarModel get(@NotNull Integer id) {
        return entityManager.find(CarModel.class, id);
    }

    @Override
    public List<CarModel> getAll() {
        var query = entityManager.createQuery("SELECT cm FROM CarModel cm", CarModel.class);
        return query.getResultList();
    }

    @Override
    public CarModel save(@NotNull CarModel carModel) {
        entityManager.persist(carModel);
        log.info("{} has been saved to the database", carModel);
        return carModel;
    }

    @Override
    public CarModel update(CarModel carModel) {
        entityManager.merge(carModel);
        log.info("{} has been updated in the database", carModel);
        return carModel;
    }

    @Override
    public CarModel delete(CarModel carModel) {
        entityManager.remove(carModel);
        log.info("{} has been deleted from the database", carModel);
        return carModel;
    }
}
