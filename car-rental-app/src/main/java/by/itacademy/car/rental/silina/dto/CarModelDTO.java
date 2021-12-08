package by.itacademy.car.rental.silina.dto;

import lombok.Data;

@Data
public class CarModelDTO {
    private Integer id;
    private String brandName;
    private String model;
    private String bodyType;
    private String fuelType;
    private Float engineVolume;
    private String transmission;
    private String bodyColor;
    private String interiorColor;
}
