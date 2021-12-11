package by.itacademy.car.rental.silina.dao.impl;

import by.itacademy.car.rental.silina.config.DatabaseConfig;
import by.itacademy.car.rental.silina.entity.CarStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static by.itacademy.car.rental.silina.constant.TestConstant.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@Sql(scripts = {"/schema.sql", "/data.sql"})
class CarStatusDAOImplTest {
    @Autowired
    private CarStatusDAOImpl carStatusDAO;

    @Test
    void get() {
        var carStatus = CarStatus.builder()
                .id(DB_CAR_STATUS_ID)
                .status(DB_CAR_STATUS_STATUS)
                .build();

        var carStatusFromDB = carStatusDAO.get(DB_CAR_STATUS_ID);
        //compare actual car status id with car status id from db
        Assertions.assertEquals(carStatusFromDB.getId(), DB_CAR_STATUS_ID);
        //compare actual car with car from db
        Assertions.assertEquals(carStatus, carStatusFromDB);
    }

    @Test
    void getCarStatusByNullId_ExpectError() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> carStatusDAO.get(null));
    }

    @Test
    void getAll() {
        var carStatuses = carStatusDAO.getAll();
        var numberOfCarStatusesFromDB = carStatuses.size();
        //compare numbers of actual cars statuses and cars statuses from db
        Assertions.assertEquals(numberOfCarStatusesFromDB, NUMBER_OF_ALL_CAR_STATUSES);

        var carsStatusesToCompare = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_ALL_CAR_STATUSES; i++) {
            carsStatusesToCompare.add(carStatusDAO.get(i));
        }
        //compare actual car statuses List with car statuses List from db
        Assertions.assertEquals(carsStatusesToCompare, carStatuses);
    }

    @Test
    @Transactional
    void save() {
        var carStatus = CarStatus.builder()
                .id(DB_CAR_STATUS_ID)
                .status(DB_CAR_STATUS_STATUS)
                .build();

        var savedCarStatus = carStatusDAO.save(carStatus);

        Assertions.assertEquals(carStatus, savedCarStatus);
        Assertions.assertNotNull(carStatus.getId());

        var carStatusFromDb = carStatusDAO.get(carStatus.getId());
        Assertions.assertEquals(carStatus, carStatusFromDb);
    }

    @Test
    @Transactional
    void update() {
        var originalCarStatus = carStatusDAO.get(DB_CAR_STATUS_ID);

        var carStatusToUpdate = CarStatus.builder()
                .id(DB_CAR_STATUS_ID)
                .status(TEST_CAR_STATUS)
                .build();

        Assertions.assertNotEquals(originalCarStatus, carStatusToUpdate);

        var updatedCarStatus = carStatusDAO.update(carStatusToUpdate);
        Assertions.assertEquals(updatedCarStatus, originalCarStatus);
        Assertions.assertEquals(TEST_CAR_STATUS, updatedCarStatus.getStatus());

        var carStatusFromDB = carStatusDAO.get(DB_CAR_STATUS_ID);
        Assertions.assertEquals(updatedCarStatus, carStatusFromDB);
    }

    @Test
    @Transactional
    void delete() {
        var carStatus = carStatusDAO.get(DB_CAR_STATUS_ID);
        Assertions.assertNotNull(carStatus);

        carStatusDAO.delete(carStatus);
        Assertions.assertNull(carStatusDAO.get(DB_CAR_STATUS_ID));
    }
}