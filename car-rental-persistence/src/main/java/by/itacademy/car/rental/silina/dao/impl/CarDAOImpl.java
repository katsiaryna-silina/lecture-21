package by.itacademy.car.rental.silina.dao.impl;

import by.itacademy.car.rental.silina.dao.DAO;
import by.itacademy.car.rental.silina.entity.Car;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@AllArgsConstructor
public class CarDAOImpl implements DAO<Car> {
    @PersistenceContext
    private final EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(CarDAOImpl.class);

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
        logger.info("{} has been saved to the database", car);
        return car;

    }

    @Override
    public Car update(@NotNull Car car) {
        entityManager.merge(car);
        logger.info("{} has been updated in the database", car);
        return car;

    }

    @Override
    public Car delete(@NotNull Car car) {
        entityManager.remove(car);
        logger.info("{} has been deleted from the database", car);
        return car;

    }
}
