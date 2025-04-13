package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.tableperclass;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.tableperclass.Dog;
import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.tableperclass.Dolphin;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j

class DogRepositoryTest {

    @Autowired
    DogRepository dogRepository;

    @Test
    void testSaveDog() {
        Dog dog = new Dog();
        dog.setBreed("abc");
        dog.setBodyTemp(37);
        dog.setSpecies("X");
        Dog savedDog = dogRepository.save(dog);

        Optional<Dog> foundDog = dogRepository.findById(savedDog.getId());
        assertAll("Dog",
            () -> assertTrue(foundDog.isPresent(), "Electric guitar should be found"),
            () -> foundDog.ifPresent(presentDog -> {
                assertNotNull(presentDog.getId(), "ID should not be null");
                assertEquals("abc", presentDog.getBreed());
                assertEquals(37, presentDog.getBodyTemp());
                assertEquals("X", presentDog.getSpecies());
            })
        );
    }

    @Test
    void equalsAndHashCodeTestWithProxy() {
        Dog saved = dogRepository.save(new Dog());

        Dog dog = dogRepository.findById(saved.getId()).orElseThrow();
        Dog dogProxy = dogRepository.getReferenceById(saved.getId());

        assertEquals(dog, dogProxy);
        assertEquals(dogProxy, dog);
        assertEquals(dog.hashCode(), dogProxy.hashCode());
    }

    @Test
    void testNotEqualsWithDifferentIds() {
        Dog dog1 = dogRepository.save(new Dog());
        Dog dog2 = dogRepository.save(new Dog());

        assertNotEquals(dog1, dog2);
    }

    @Test
    void testEqualsWithNullAndOtherClass() {
        Dog dog = dogRepository.save(new Dog());

        assertNotEquals(null, dog);
        assertNotEquals(new Object(), dog);
    }

    @Test
    void testEqualsWithSameId() {
        Dog dog1 = dogRepository.save(new Dog());
        Dog dog2 = dogRepository.findById(dog1.getId()).orElseThrow();
        assertEquals(dog1, dog2);
    }

    @Test
    void testEqualsWithDifferentTypes() {
        Dog dog = dogRepository.save(new Dog());
        Dolphin dolphin = new Dolphin();
        dolphin.setId(dog.getId());
        assertNotEquals(dog, dolphin);
    }

    @Test
    void testEqualsWithNullId() {
        Dog dog1 = new Dog();
        Dog dog2 = new Dog();
        assertNotEquals(dog1, dog2);
    }

    @Test
    void testHashCodeConsistency() {
        Dog dog = dogRepository.save(new Dog());
        int hashCode1 = dog.hashCode();
        int hashCode2 = dog.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void testHashCodeWithProxy() {
        Dog saved = dogRepository.save(new Dog());
        Dog dog = dogRepository.findById(saved.getId()).orElseThrow();
        Dog dogProxy = dogRepository.getReferenceById(saved.getId());

        assertEquals(dog.hashCode(), dogProxy.hashCode());
    }

    @Test
    void testHashCodeDifferentObjects() {
        Dog dog1 = dogRepository.save(new Dog());
        Dog dog2 = dogRepository.save(new Dog());

        assertNotEquals(dog1.hashCode(), dog2.hashCode());
    }

    @Test
    void testHashCodeWithNullId() {
        Dog dog1 = new Dog();
        Dog dog2 = new Dog();

        assertEquals(dog1.hashCode(), dog2.hashCode());
    }

}