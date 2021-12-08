package by.itacademy.car.rental.silina.dao.impl;

import by.itacademy.car.rental.silina.dao.DAO;
import by.itacademy.car.rental.silina.entity.CarModel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@AllArgsConstructor
public class CarModelDAOImpl implements DAO<CarModel> {
    private final Logger logger = LoggerFactory.getLogger(CarModelDAOImpl.class);
    private EntityManager entityManager;
    
    @Override
    public CarModel get(@NotNull Integer id) {
        try {
            return entityManager.find(CarModel.class, id);
        } catch (Exception e) {
            logger.error("Error in getting car model by id = {} ", id);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<CarModel> getAll() {
        try {
            var query = entityManager.createQuery("SELECT cm FROM CarModel cm", CarModel.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error in getting all car models ");
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public CarModel save(@NotNull CarModel carModel) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(carModel);
            transaction.commit();
            logger.info("{} has been saved to the database", carModel);
            return carModel;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction has rolled back");
            }
            logger.error("Error in saving {} to the database", carModel);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public CarModel update(CarModel carModel) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(carModel);
            transaction.commit();
            logger.info("{} has been updated in the database", carModel);
            return carModel;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction has rolled back");
            }
            logger.error("Error in updating {} in the database", carModel);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public CarModel delete(CarModel carModel) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(carModel);
            transaction.commit();
            logger.info("{} has been deleted from the database", carModel);
            return carModel;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction has rolled back");
            }
            logger.error("Error in deleting {} from the database", carModel);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
