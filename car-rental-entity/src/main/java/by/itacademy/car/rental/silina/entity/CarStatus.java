package by.itacademy.car.rental.silina.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;

@Entity
@Builder
@Table(name = "car_status")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarStatus {
    @Id
    private Integer id;

    @Column(name = "STATUS")
    @Size(max = 10)
    private String status;

    @OneToMany(mappedBy = "carStatus")
    @ToString.Exclude
    private Collection<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarStatus carStatus = (CarStatus) o;

        if (!Objects.equals(id, carStatus.id)) return false;
        return Objects.equals(status, carStatus.status);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}