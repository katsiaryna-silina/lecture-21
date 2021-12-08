package by.itacademy.car.rental.silina.dao;

import by.itacademy.car.rental.silina.config.DatabaseConfig;
import by.itacademy.car.rental.silina.config.MySpringMvcDispatcherServletInitializer;
import by.itacademy.car.rental.silina.dao.impl.CarDAOImpl;
import by.itacademy.car.rental.silina.entity.Car;
import by.itacademy.car.rental.silina.entity.CarModel;
import by.itacademy.car.rental.silina.entity.CarStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MySpringMvcDispatcherServletInitializer.class, DatabaseConfig.class})
@WebAppConfiguration
@Sql(scripts = {"/schema.sql", "/data.sql"})
class CarDAOImplTest {
    @Autowired
    private CarDAOImpl carDAO;

    @Test
    void get() {
        var car = carDAO.get(1);
        //TODO COMPARE WITH KNOWN DB CAR
        Assertions.assertEquals(car.getId(), 1);
    }

    @Test
    void getAll() {
        var cars = carDAO.getAll();
        int size = cars.size();
        Assertions.assertEquals(size, 6);
        //TODO CREATE LIST OF CARS USING METHOD CARDAO.GET() AND COMPARE WITH CARS(LIST)
    }

    @Test
    @Transactional
    void save() {
        var carModel = CarModel.builder()
                .id(1)
                .build();
        var carStatus = CarStatus.builder()
                .id(1)
                .build();
        var car = Car.builder()
                .stateNumber("TestNumber")
                .carModel(carModel)
                .carStatus(carStatus)
                .build();
        var car1 = carDAO.save(car);
        Assertions.assertEquals(car, car1);
        Assertions.assertNotNull(car.getId());

        Car carFromDb = carDAO.get(car.getId());
        Assertions.assertEquals(car, carFromDb);
    }

    @Test
    @Transactional
    void update() {
        var originalCar = carDAO.get(1);

        var carToUpdate = Car.builder()
                .id(originalCar.getId())
                .carModel(originalCar.getCarModel())
                .carStatus(originalCar.getCarStatus())
                .stateNumber("TestNumber")
                .build();

        Assertions.assertNotEquals(originalCar, carToUpdate);

        Car updatedCar = carDAO.update(carToUpdate);
        Assertions.assertEquals(updatedCar, originalCar);
        Assertions.assertEquals("TestNumber", updatedCar.getStateNumber());

        Car carFromDb = carDAO.get(1);
        Assertions.assertEquals(updatedCar, carFromDb);
    }

    @Test
    @Transactional
    void delete() {
        Car car = carDAO.get(1);
        Assertions.assertNotNull(car);
        carDAO.delete(car);
        Assertions.assertNull(carDAO.get(1));
    }
}