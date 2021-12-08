package by.itacademy.car.rental.silina.entity;

import lombok.*;
import org.hibernate.Hibernate;

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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CarStatus carStatus = (CarStatus) o;
        return id != null && Objects.equals(id, carStatus.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
