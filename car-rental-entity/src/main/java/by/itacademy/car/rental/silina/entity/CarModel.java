package by.itacademy.car.rental.silina.entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "BRAND_NAME")
    @Size(max = 100)
    private String brandName;

    @Column(name = "MODEL")
    @Size(max = 100)
    private String model;

    @Column(name = "BODY_TYPE")
    @Size(max = 30)
    private String bodyType;

    @Column(name = "FUEL_TYPE")
    @Size(max = 30)
    private String fuelType;

    @Column(name = "ENGINE_VOLUME")
    private Float engineVolume;

    @Column(name = "TRANSMISSION")
    @Size(max = 30)
    private String transmission;

    @Column(name = "BODY_COLOR")
    @Size(max = 100)
    private String bodyColor;

    @Column(name = "INTERIOR_COLOR")
    @Size(max = 100)
    private String interiorColor;

    @OneToMany(mappedBy = "carModel")
    @ToString.Exclude
    private Collection<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CarModel carModel = (CarModel) o;
        return id != null && Objects.equals(id, carModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

