package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.tableperclass;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.tableperclass.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
