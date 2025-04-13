package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.singletable;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.singletable.Car;
import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.singletable.Truck;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
class TruckRepositoryTest {

    @Autowired
    TruckRepository truckRepository;

    @Test
    void testSaveTruck() {
        Truck truck = new Truck();
        truck.setPayload(999);
        Truck savedTruck = truckRepository.save(truck);

        Optional<Truck> foundTruck = truckRepository.findById(savedTruck.getId());
        assertAll("Car",
            () -> assertTrue(foundTruck.isPresent(), "Electric guitar should be found"),
            () -> foundTruck.ifPresent(presentTruck -> {
                assertNotNull(presentTruck.getId(), "ID should not be null");
                assertEquals(999, presentTruck.getPayload());
            })
        );
    }

    @Test
    void equalsAndHashCodeTestWithProxy() {
        Truck saved = truckRepository.save(new Truck());

        Truck truck = truckRepository.findById(saved.getId()).orElseThrow();
        Truck truckProxy = truckRepository.getReferenceById(saved.getId());

        assertEquals(truck, truckProxy);
        assertEquals(truckProxy, truck);
        assertEquals(truck.hashCode(), truckProxy.hashCode());
    }

    @Test
    void testNotEqualsWithDifferentIds() {
        Truck truck1 = truckRepository.save(new Truck());
        Truck truck2 = truckRepository.save(new Truck());

        assertNotEquals(truck1, truck2);
    }

    @Test
    void testEqualsWithNullAndOtherClass() {
        Truck truck = truckRepository.save(new Truck());

        assertNotEquals(null, truck);
        assertNotEquals(new Object(), truck);
    }

    @Test
    void testEqualsWithSameId() {
        Truck truck1 = truckRepository.save(new Truck());
        Truck truck2 = truckRepository.findById(truck1.getId()).orElseThrow();
        assertEquals(truck1, truck2);
    }

    @Test
    void testEqualsWithDifferentTypes() {
        Truck truck = truckRepository.save(new Truck());
        Car car = new Car();
        car.setId(truck.getId());
        assertNotEquals(truck, car);
    }

    @Test
    void testEqualsWithNullId() {
        Truck truck1 = new Truck();
        Truck truck2 = new Truck();
        assertNotEquals(truck1, truck2);
    }

    @Test
    void testHashCodeConsistency() {
        Truck truck = truckRepository.save(new Truck());
        int hashCode1 = truck.hashCode();
        int hashCode2 = truck.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

}