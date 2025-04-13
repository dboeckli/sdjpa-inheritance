package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.tableperclass;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.tableperclass.Dolphin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DolphinRepository extends JpaRepository<Dolphin, Long> {
}
