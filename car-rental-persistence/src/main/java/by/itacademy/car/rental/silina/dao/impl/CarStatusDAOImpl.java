package by.itacademy.car.rental.silina.dao.impl;

import by.itacademy.car.rental.silina.dao.DAO;
import by.itacademy.car.rental.silina.entity.CarStatus;
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
public class CarStatusDAOImpl implements DAO<CarStatus> {
    private final Logger logger = LoggerFactory.getLogger(CarStatusDAOImpl.class);
    private final EntityManager entityManager;

    @Override
    public CarStatus get(@NotNull Integer id) {
        try {
            return entityManager.find(CarStatus.class, id);
        } catch (Exception e) {
            logger.error("Error in getting car status by id = {} ", id);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<CarStatus> getAll() {
        try {
            var query = entityManager.createQuery("SELECT cs FROM CarStatus cs", CarStatus.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error in getting all car statuses");
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public CarStatus save(@NotNull CarStatus carStatus) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(carStatus);
            transaction.commit();
            logger.info("{} has been saved to the database", carStatus);
            return carStatus;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction has rolled back");
            }
            logger.error("Error in saving {} to the database", carStatus);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public CarStatus update(@NotNull CarStatus carStatus) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(carStatus);
            transaction.commit();
            logger.info("{} has been updated in the database", carStatus);
            return carStatus;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction has rolled back");
            }
            logger.error("Error in updating {} in the database", carStatus);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public CarStatus delete(@NotNull CarStatus carStatus) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(carStatus);
            transaction.commit();
            logger.info("{} has been deleted from the database", carStatus);
            return carStatus;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                logger.error("Transaction has rolled back");
            }
            logger.error("Error in deleting {} from the database", carStatus);
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
