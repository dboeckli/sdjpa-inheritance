package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.mappedsuperclass;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.mappedsuperclass.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {}
