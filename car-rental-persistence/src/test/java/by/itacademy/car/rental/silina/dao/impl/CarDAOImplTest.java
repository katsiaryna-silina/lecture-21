package by.itacademy.car.rental.silina.dao.impl;

import by.itacademy.car.rental.silina.config.DatabaseConfig;
import by.itacademy.car.rental.silina.entity.Car;
import by.itacademy.car.rental.silina.entity.CarModel;
import by.itacademy.car.rental.silina.entity.CarStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static by.itacademy.car.rental.silina.constant.TestConstant.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfig.class)
@Sql(scripts = {"/schema.sql", "/data.sql"})
class CarDAOImplTest {
    @Autowired
    private CarDAOImpl carDAO;

    @Test
    void get() {
        var carModel = CarModel.builder()
                .id(DB_CAR_MODEL_ID)
                .brandName(DB_CAR_MODEL_BRAND_NAME)
                .model(DB_CAR_MODEL_MODEL)
                .bodyColor(DB_CAR_MODEL_BODY_COLOR)
                .fuelType(DB_CAR_MODEL_FUEL_TYPE)
                .engineVolume(DB_CAR_MODEL_ENGINE_VOLUME)
                .transmission(DB_CAR_MODEL_TRANSMISSION)
                .bodyType(DB_CAR_MODEL_BODY_TYPE)
                .interiorColor(DB_CAR_MODEL_INTERIOR_COLOR)
                .build();

        var carStatus = CarStatus.builder()
                .id(DB_CAR_STATUS_ID)
                .status(DB_CAR_STATUS_STATUS)
                .build();

        var car = Car.builder()
                .id(DB_CAR_ID)
                .stateNumber(DB_CAR_STATE_NUMBER)
                .carModel(carModel)
                .carStatus(carStatus)
                .build();

        var carFromDb = carDAO.get(DB_CAR_ID);
        //compare actual car id with car id from db
        Assertions.assertEquals(carFromDb.getId(), DB_CAR_ID);
        //compare actual car with car from db
        Assertions.assertEquals(car, carFromDb);
    }

    @Test
    void getCarByNullId_ExpectError() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> carDAO.get(null));
    }

    @Test
    void getAll() {
        var cars = carDAO.getAll();
        var numberOfCarsFromDB = cars.size();
        //compare numbers of actual cars and cars from db
        Assertions.assertEquals(numberOfCarsFromDB, NUMBER_OF_ALL_CARS);

        var carsToCompare = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_ALL_CARS; i++) {
            carsToCompare.add(carDAO.get(i));
        }
        //compare actual car List with car List from db
        Assertions.assertEquals(carsToCompare, cars);
    }

    @Test
    @Transactional
    void save() {
        var carModel = CarModel.builder()
                .id(DB_CAR_MODEL_ID)
                .brandName(DB_CAR_MODEL_BRAND_NAME)
                .model(DB_CAR_MODEL_MODEL)
                .bodyColor(DB_CAR_MODEL_BODY_COLOR)
                .fuelType(DB_CAR_MODEL_FUEL_TYPE)
                .engineVolume(DB_CAR_MODEL_ENGINE_VOLUME)
                .transmission(DB_CAR_MODEL_TRANSMISSION)
                .bodyType(DB_CAR_MODEL_BODY_TYPE)
                .interiorColor(DB_CAR_MODEL_INTERIOR_COLOR)
                .build();

        var carStatus = CarStatus.builder()
                .id(DB_CAR_STATUS_ID)
                .status(DB_CAR_STATUS_STATUS)
                .build();

        var car = Car.builder()
                .stateNumber(TEST_CAR_STATE_NUMBER)
                .carModel(carModel)
                .carStatus(carStatus)
                .build();

        var savedCar = carDAO.save(car);

        Assertions.assertEquals(car, savedCar);
        Assertions.assertNotNull(car.getId());

        var carFromDb = carDAO.get(car.getId());
        Assertions.assertEquals(car, carFromDb);
    }

    @Test
    @Transactional
    void update() {
        var originalCar = carDAO.get(DB_CAR_ID);

        var carToUpdate = Car.builder()
                .id(originalCar.getId())
                .carModel(originalCar.getCarModel())
                .carStatus(originalCar.getCarStatus())
                .stateNumber(TEST_CAR_STATE_NUMBER)
                .build();

        Assertions.assertNotEquals(originalCar, carToUpdate);

        var updatedCar = carDAO.update(carToUpdate);
        Assertions.assertEquals(updatedCar, originalCar);
        Assertions.assertEquals(TEST_CAR_STATE_NUMBER, updatedCar.getStateNumber());

        var carFromDB = carDAO.get(DB_CAR_ID);
        Assertions.assertEquals(updatedCar, carFromDB);
    }

    @Test
    @Transactional
    void delete() {
        var car = carDAO.get(DB_CAR_ID);
        Assertions.assertNotNull(car);

        carDAO.delete(car);
        Assertions.assertNull(carDAO.get(DB_CAR_ID));
    }
}