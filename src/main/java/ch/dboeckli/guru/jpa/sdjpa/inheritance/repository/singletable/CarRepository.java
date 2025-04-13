package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.singletable;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.singletable.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
