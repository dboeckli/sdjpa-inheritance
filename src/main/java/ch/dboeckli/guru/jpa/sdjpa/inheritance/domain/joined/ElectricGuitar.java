package ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.joined;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
public class ElectricGuitar extends Guitar{

    private Integer numberOfPickups;

}
