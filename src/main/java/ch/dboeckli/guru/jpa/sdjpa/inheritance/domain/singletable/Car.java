package ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("CAR")
@ToString
@Getter
@Setter
public class Car extends Vehicle {

    private String trimLevel;

}
