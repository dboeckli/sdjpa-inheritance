package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.joined;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.joined.ElectricGuitar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricGuitarRepository extends JpaRepository<ElectricGuitar, Long> {
}
