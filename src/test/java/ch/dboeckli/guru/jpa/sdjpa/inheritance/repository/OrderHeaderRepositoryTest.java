package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.mappedsuperclass.OrderHeader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Test
    void testSaveOrderHeader() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomerName("Pumukel");
        OrderHeader savedOrderHeader = orderHeaderRepository.save(orderHeader);

        Optional<OrderHeader> foundOrderHeader = orderHeaderRepository.findById(savedOrderHeader.getId());
        assertAll("Order Header",
            () -> assertTrue(foundOrderHeader.isPresent()),
            () -> foundOrderHeader.ifPresent(header -> {
                assertNotNull(header.getId());
                assertEquals("Pumukel", header.getCustomerName());
            })
        );
    }

    @Test
    void equalsAndHashCodeTestWithProxy() {
        OrderHeader saved = orderHeaderRepository.save(new OrderHeader());

        OrderHeader orderHeader = orderHeaderRepository.findById(saved.getId()).orElseThrow();
        OrderHeader orderHeaderProxy = orderHeaderRepository.getReferenceById(saved.getId());

        assertEquals(orderHeader, orderHeaderProxy);
        assertEquals(orderHeaderProxy, orderHeader);
        assertEquals(orderHeader.hashCode(), orderHeaderProxy.hashCode());
    }

    @Test
    void testNotEqualsWithDifferentIds() {
        OrderHeader orderHeader1 = orderHeaderRepository.save(new OrderHeader());
        OrderHeader orderHeader2 = orderHeaderRepository.save(new OrderHeader());

        assertNotEquals(orderHeader1, orderHeader2);
    }

    @Test
    void testEqualsWithNullAndOtherClass() {
        OrderHeader orderHeader = orderHeaderRepository.save(new OrderHeader());

        assertNotEquals(null, orderHeader);
        assertNotEquals(new Object(), orderHeader);
    }

    @Test
    void testHashCodeConsistency() {
        OrderHeader orderHeader = orderHeaderRepository.save(new OrderHeader());
        int hashCode1 = orderHeader.hashCode();
        int hashCode2 = orderHeader.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

}