package ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.tableperclass;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
public class Dog extends Mammal {

  private String breed;
}
