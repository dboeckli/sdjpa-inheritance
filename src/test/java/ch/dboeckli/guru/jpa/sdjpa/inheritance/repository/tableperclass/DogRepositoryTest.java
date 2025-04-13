package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.tableperclass;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.tableperclass.Dog;
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
    void testHashCodeConsistency() {
        Dog dog = dogRepository.save(new Dog());
        int hashCode1 = dog.hashCode();
        int hashCode2 = dog.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

}