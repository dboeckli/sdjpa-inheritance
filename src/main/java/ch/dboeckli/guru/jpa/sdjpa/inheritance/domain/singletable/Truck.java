package ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("truck")
@ToString
@Getter
@Setter
public class Truck extends Vehicle {

  private Integer payload;
}
