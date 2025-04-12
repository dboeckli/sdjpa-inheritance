package ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.mappedsuperclass;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class OrderHeader extends BaseEntity {

    private String customerName;
}
