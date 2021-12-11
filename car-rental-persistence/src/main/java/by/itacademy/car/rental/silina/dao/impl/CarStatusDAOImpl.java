package by.itacademy.car.rental.silina.dao.impl;

import by.itacademy.car.rental.silina.dao.DAO;
import by.itacademy.car.rental.silina.entity.CarStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@Slf4j
public class CarStatusDAOImpl implements DAO<CarStatus> {
    @PersistenceContext
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public CarStatus get(@NotNull Integer id) {
        return entityManager.find(CarStatus.class, id);
    }

    @Override
    public List<CarStatus> getAll() {
        var query = entityManager.createQuery("SELECT cs FROM CarStatus cs", CarStatus.class);
        return query.getResultList();
    }

    @Override
    public CarStatus save(@NotNull CarStatus carStatus) {
        entityManager.persist(carStatus);
        log.info("{} has been saved to the database", carStatus);
        return carStatus;
    }

    @Override
    public CarStatus update(@NotNull CarStatus carStatus) {
        entityManager.merge(carStatus);
        log.info("{} has been updated in the database", carStatus);
        return carStatus;
    }

    @Override
    public CarStatus delete(@NotNull CarStatus carStatus) {
        entityManager.remove(carStatus);
        log.info("{} has been deleted from the database", carStatus);
        return carStatus;
    }
}
