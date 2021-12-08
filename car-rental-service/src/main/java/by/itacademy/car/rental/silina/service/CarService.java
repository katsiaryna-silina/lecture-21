package by.itacademy.car.rental.silina.service;

import by.itacademy.car.rental.silina.dao.impl.CarDAOImpl;
import by.itacademy.car.rental.silina.entity.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@AllArgsConstructor
public class CarService {
    private CarDAOImpl carDAO;

    public Car getCarById(@NotNull Integer id) {
        return carDAO.get(id);
    }

    public List<Car> getAll() {
        return carDAO.getAll();
    }
}
