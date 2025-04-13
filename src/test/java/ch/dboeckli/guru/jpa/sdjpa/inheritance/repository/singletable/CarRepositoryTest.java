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
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;

    @Test
    void testSaveCar() {
        Car car = new Car();
        car.setTrimLevel("abc");
        Car savedCar = carRepository.save(car);

        Optional<Car> foundCar = carRepository.findById(savedCar.getId());
        assertAll("Car",
            () -> assertTrue(foundCar.isPresent(), "Electric guitar should be found"),
            () -> foundCar.ifPresent(presentCar -> {
                assertNotNull(presentCar.getId(), "ID should not be null");
                assertEquals("abc", presentCar.getTrimLevel());
            })
        );
    }

    @Test
    void equalsAndHashCodeTestWithProxy() {
        Car saved = carRepository.save(new Car());

        Car car = carRepository.findById(saved.getId()).orElseThrow();
        Car carProxy = carRepository.getReferenceById(saved.getId());

        assertEquals(car, carProxy);
        assertEquals(carProxy, car);
        assertEquals(car.hashCode(), carProxy.hashCode());
    }

    @Test
    void testNotEqualsWithDifferentIds() {
        Car car1 = carRepository.save(new Car());
        Car car2 = carRepository.save(new Car());

        assertNotEquals(car1, car2);
    }

    @Test
    void testEqualsWithNullAndOtherClass() {
        Car car = carRepository.save(new Car());

        assertNotEquals(null, car);
        assertNotEquals(new Object(), car);
    }

    @Test
    void testEqualsWithSameId() {
        Car electricGuitar1 = carRepository.save(new Car());
        Car electricGuitar2 = carRepository.findById(electricGuitar1.getId()).orElseThrow();
        assertEquals(electricGuitar1, electricGuitar2);
    }

    @Test
    void testEqualsWithDifferentTypes() {
        Car car = carRepository.save(new Car());
        Truck truck = new Truck();
        truck.setId(car.getId());
        assertNotEquals(car, truck);
    }

    @Test
    void testEqualsWithNullId() {
        Car car1 = new Car();
        Car car2 = new Car();
        assertNotEquals(car1, car2);
    }

    @Test
    void testHashCodeConsistency() {
        Car car = carRepository.save(new Car());
        int hashCode1 = car.hashCode();
        int hashCode2 = car.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

}