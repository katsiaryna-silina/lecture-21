package by.itacademy.car.rental.silina.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CAR_MODEL")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "BRAND_NAME")
    @Size(max = 100)
    private String brandName;

    @NotNull
    @Column(name = "MODEL")
    @Size(max = 100)
    private String model;

    @NotNull
    @Column(name = "BODY_TYPE")
    @Size(max = 30)
    private String bodyType;

    @NotNull
    @Column(name = "FUEL_TYPE")
    @Size(max = 30)
    private String fuelType;

    @NotNull
    @Column(name = "ENGINE_VOLUME")
    private Double engineVolume;

    @NotNull
    @Column(name = "TRANSMISSION")
    @Size(max = 30)
    private String transmission;

    @NotNull
    @Column(name = "BODY_COLOR")
    @Size(max = 100)
    private String bodyColor;

    @NotNull
    @Column(name = "INTERIOR_COLOR")
    @Size(max = 100)
    private String interiorColor;

    @OneToMany(mappedBy = "carModel")
    @ToString.Exclude
    private Collection<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarModel carModel = (CarModel) o;

        if (!id.equals(carModel.id)) return false;
        if (!brandName.equals(carModel.brandName)) return false;
        if (!model.equals(carModel.model)) return false;
        if (!bodyType.equals(carModel.bodyType)) return false;
        if (!fuelType.equals(carModel.fuelType)) return false;
        if ((Math.round(engineVolume * 10.0) / 10.0) != (Math.round(carModel.engineVolume * 10.0) / 10.0)) return false;
        if (!transmission.equals(carModel.transmission)) return false;
        if (!bodyColor.equals(carModel.bodyColor)) return false;
        return interiorColor.equals(carModel.interiorColor);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}