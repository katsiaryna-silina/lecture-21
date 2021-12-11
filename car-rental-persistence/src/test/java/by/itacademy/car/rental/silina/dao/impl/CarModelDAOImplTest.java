package by.itacademy.car.rental.silina.dao.impl;

import by.itacademy.car.rental.silina.config.DatabaseConfig;
import by.itacademy.car.rental.silina.entity.CarModel;
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
class CarModelDAOImplTest {
    @Autowired
    private CarModelDAOImpl carModelDAO;

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

        var carModelFromDb = carModelDAO.get(DB_CAR_MODEL_ID);
        //compare actual car model id with car model id from db
        Assertions.assertEquals(carModelFromDb.getId(), DB_CAR_MODEL_ID);
        //compare actual car model with car model from db
        Assertions.assertEquals(carModel, carModelFromDb);
    }

    @Test
    void getCarModelByNullId_ExpectError() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> carModelDAO.get(null));
    }

    @Test
    void getAll() {
        var carModels = carModelDAO.getAll();
        int numberOfCarModelsFromDB = carModels.size();
        //compare numbers of actual car models and car models from db
        Assertions.assertEquals(numberOfCarModelsFromDB, NUMBER_OF_ALL_CAR_MODELS);

        var carsModelsToCompare = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_ALL_CAR_MODELS; i++) {
            carsModelsToCompare.add(carModelDAO.get(i));
        }
        //compare actual car models List with car models List from db
        Assertions.assertEquals(carsModelsToCompare, carModels);
    }

    @Test
    @Transactional
    void save() {
        var carModel = CarModel.builder()
                .brandName(DB_CAR_MODEL_BRAND_NAME)
                .model(TEST_CAR_MODEL)
                .bodyColor(DB_CAR_MODEL_BODY_COLOR)
                .fuelType(DB_CAR_MODEL_FUEL_TYPE)
                .engineVolume(DB_CAR_MODEL_ENGINE_VOLUME)
                .transmission(DB_CAR_MODEL_TRANSMISSION)
                .bodyType(DB_CAR_MODEL_BODY_TYPE)
                .interiorColor(DB_CAR_MODEL_INTERIOR_COLOR)
                .build();

        var savedCarModel = carModelDAO.save(carModel);

        Assertions.assertEquals(carModel, savedCarModel);
        Assertions.assertNotNull(carModel.getId());

        var carModelFromDB = carModelDAO.get(carModel.getId());
        Assertions.assertEquals(carModel, carModelFromDB);
    }

    @Test
    @Transactional
    void update() {
        var originalCarModel = carModelDAO.get(DB_CAR_MODEL_ID);

        var carModelToUpdate = CarModel.builder()
                .id(originalCarModel.getId())
                .brandName(DB_CAR_MODEL_BRAND_NAME)
                .model(TEST_CAR_MODEL)
                .bodyColor(DB_CAR_MODEL_BODY_COLOR)
                .fuelType(DB_CAR_MODEL_FUEL_TYPE)
                .engineVolume(DB_CAR_MODEL_ENGINE_VOLUME)
                .transmission(DB_CAR_MODEL_TRANSMISSION)
                .bodyType(DB_CAR_MODEL_BODY_TYPE)
                .interiorColor(DB_CAR_MODEL_INTERIOR_COLOR)
                .build();

        Assertions.assertNotEquals(originalCarModel, carModelToUpdate);

        var updatedCarModel = carModelDAO.update(carModelToUpdate);
        Assertions.assertEquals(updatedCarModel, originalCarModel);
        Assertions.assertEquals(TEST_CAR_MODEL, updatedCarModel.getModel());

        var carModelFromDB = carModelDAO.get(DB_CAR_MODEL_ID);
        Assertions.assertEquals(updatedCarModel, carModelFromDB);
    }

    @Test
    @Transactional
    void delete() {
        var carModel = carModelDAO.get(DB_CAR_MODEL_ID);
        Assertions.assertNotNull(carModel);

        carModelDAO.delete(carModel);
        Assertions.assertNull(carModelDAO.get(DB_CAR_MODEL_ID));
    }
}