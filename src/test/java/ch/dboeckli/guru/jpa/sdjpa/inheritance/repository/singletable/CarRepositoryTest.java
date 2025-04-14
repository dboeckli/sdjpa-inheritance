package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.singletable;

import static org.junit.jupiter.api.Assertions.*;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.singletable.Car;
import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.singletable.Truck;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Slf4j
class CarRepositoryTest {

  @Autowired CarRepository carRepository;

  @Test
  void testSaveCar() {
    Car car = new Car();
    car.setTrimLevel("abc");
    Car savedCar = carRepository.save(car);

    Optional<Car> foundCar = carRepository.findById(savedCar.getId());
    assertAll(
        "Car",
        () -> assertTrue(foundCar.isPresent(), "Electric guitar should be found"),
        () ->
            foundCar.ifPresent(
                presentCar -> {
                  assertNotNull(presentCar.getId(), "ID should not be null");
                  assertEquals("abc", presentCar.getTrimLevel());
                }));
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
    Car car1 = carRepository.save(new Car());
    Car car2 = carRepository.findById(car1.getId()).orElseThrow();
    assertEquals(car1, car2);
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

  @Test
  void testHashCodeWithProxy() {
    Car saved = carRepository.save(new Car());
    Car car = carRepository.findById(saved.getId()).orElseThrow();
    Car carProxy = carRepository.getReferenceById(saved.getId());

    assertEquals(car.hashCode(), carProxy.hashCode());
  }

  @Test
  void testHashCodeDifferentObjects() {
    Car car1 = carRepository.save(new Car());
    Car car2 = carRepository.save(new Car());

    assertEquals(car1.hashCode(), car2.hashCode());
  }

  @Test
  void testHashCodeWithNullId() {
    Car car1 = new Car();
    Car car2 = new Car();

    assertEquals(car1.hashCode(), car2.hashCode());
  }
}
