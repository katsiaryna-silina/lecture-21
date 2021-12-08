package by.itacademy.car.rental.silina.dao;

import java.util.List;

public interface DAO<T> {
    T get(Integer id);

    List<T> getAll();

    T save(T pojo);

    T update(T pojo);

    T delete(T pojo);

}
