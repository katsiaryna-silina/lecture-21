package by.itacademy.car.rental.silina.dto;

import lombok.Data;

@Data
public class CarDTO {
    private Integer id;
    private String stateNumber;
    private CarModelDTO carModel;
    private CarStatusDTO carStatus;
}
