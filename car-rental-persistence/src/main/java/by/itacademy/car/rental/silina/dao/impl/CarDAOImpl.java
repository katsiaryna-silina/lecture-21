package by.itacademy.car.rental.silina.dao.impl;

import by.itacademy.car.rental.silina.dao.DAO;
import by.itacademy.car.rental.silina.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@Slf4j
public class CarDAOImpl implements DAO<Car> {
    @PersistenceContext
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Car get(@NotNull Integer id) {
        return entityManager.find(Car.class, id);
    }

    @Override
    public List<Car> getAll() {
        var query = entityManager.createQuery("SELECT c FROM Car c", Car.class);
        return query.getResultList();
    }

    @Override
    public Car save(@NotNull Car car) {
        entityManager.persist(car);
        log.info("{} has been saved to the database", car);
        return car;
    }

    @Override
    public Car update(@NotNull Car car) {
        entityManager.merge(car);
        log.info("{} has been updated in the database", car);
        return car;
    }

    @Override
    public Car delete(@NotNull Car car) {
        entityManager.remove(car);
        log.info("{} has been deleted from the database", car);
        return car;
    }
}