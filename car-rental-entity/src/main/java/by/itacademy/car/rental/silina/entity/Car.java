package by.itacademy.car.rental.silina.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Builder
@Table(name = "car")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "STATE_NUMBER")
    private String stateNumber;

    @NotNull
    @JoinColumn(name = "CAR_MODEL_ID", nullable = false)
    @ManyToOne(optional = false)
    private CarModel carModel;

    @NotNull
    @JoinColumn(name = "CAR_STATUS_ID", nullable = false)
    @ManyToOne(optional = false)
    private CarStatus carStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (!Objects.equals(id, car.id)) return false;
        if (!stateNumber.equals(car.stateNumber)) return false;
        if (!carModel.equals(car.carModel)) return false;
        return carStatus.equals(car.carStatus);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
