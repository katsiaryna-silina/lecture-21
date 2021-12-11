package by.itacademy.car.rental.silina.service;

import by.itacademy.car.rental.silina.config.DatabaseConfig;
import by.itacademy.car.rental.silina.config.ServiceConfig;
import by.itacademy.car.rental.silina.dao.impl.CarDAOImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseConfig.class, ServiceConfig.class})
@Sql(scripts = {"/schema.sql", "/data.sql"})
class CarServiceTest {
    @Autowired
    private CarDAOImpl carDAO;
    @Autowired
    private CarService carService;

    @Test
    void getCarById() {
        var car = carDAO.get(1);
        var carById = carService.getCarById(1);
        Assertions.assertEquals(car, carById);
    }

    @Test
    void getCarByNullId_ExpectError() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> carService.getCarById(null));
    }

    @Test
    void getAll() {
        var cars = carDAO.getAll();
        var actualCars = carService.getAll();
        Assertions.assertEquals(cars, actualCars);
    }
}