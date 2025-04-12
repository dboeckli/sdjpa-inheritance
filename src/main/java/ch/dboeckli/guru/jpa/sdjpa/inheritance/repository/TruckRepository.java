package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.singletable.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, Long> {
}
