package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.joined;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.joined.ElectricGuitar;
import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.joined.Piano;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
class ElectricGuitarRepositoryTest {

    @Autowired
    ElectricGuitarRepository electricGuitarRepository;

    @Test
    void testSaveElectricGuitar() {
        ElectricGuitar electricGuitar = new ElectricGuitar();
        electricGuitar.setNumberOfStrings(6);
        electricGuitar.setNumberOfPickups(2);
        ElectricGuitar savedElectricGuitar = electricGuitarRepository.save(electricGuitar);

        Optional<ElectricGuitar> foundElectricGuitar = electricGuitarRepository.findById(savedElectricGuitar.getId());
        assertAll("Electric Guitar",
            () -> assertTrue(foundElectricGuitar.isPresent(), "Electric guitar should be found"),
            () -> foundElectricGuitar.ifPresent(guitar -> {
                assertNotNull(guitar.getId(), "ID should not be null");
                assertEquals(2, guitar.getNumberOfPickups(), "Number of pickups should be 2");
                assertEquals(6, guitar.getNumberOfStrings(), "Number of strings should be 6");
            })
        );
    }

    @Test
    void equalsAndHashCodeTestWithProxy() {
        ElectricGuitar saved = electricGuitarRepository.save(new ElectricGuitar());

        ElectricGuitar electricGuitar = electricGuitarRepository.findById(saved.getId()).orElseThrow();
        ElectricGuitar electricGuitarProxy = electricGuitarRepository.getReferenceById(saved.getId());

        assertEquals(electricGuitar, electricGuitarProxy);
        assertEquals(electricGuitarProxy, electricGuitar);
        assertEquals(electricGuitar.hashCode(), electricGuitarProxy.hashCode());
    }

    @Test
    void testNotEqualsWithDifferentIds() {
        ElectricGuitar electricGuitar1 = electricGuitarRepository.save(new ElectricGuitar());
        ElectricGuitar electricGuitar2 = electricGuitarRepository.save(new ElectricGuitar());

        assertNotEquals(electricGuitar1, electricGuitar2);
    }

    @Test
    void testEqualsWithNullAndOtherClass() {
        ElectricGuitar electricGuitar = electricGuitarRepository.save(new ElectricGuitar());

        assertNotEquals(null, electricGuitar);
        assertNotEquals(new Object(), electricGuitar);
    }

    @Test
    void testEqualsWithSameId() {
        ElectricGuitar electricGuitar1 = electricGuitarRepository.save(new ElectricGuitar());
        ElectricGuitar electricGuitar2 = electricGuitarRepository.findById(electricGuitar1.getId()).orElseThrow();
        assertEquals(electricGuitar1, electricGuitar2);
    }

    @Test
    void testEqualsWithDifferentTypes() {
        ElectricGuitar electricGuitar = electricGuitarRepository.save(new ElectricGuitar());
        Piano piano = new Piano();
        piano.setId(electricGuitar.getId());
        assertNotEquals(electricGuitar, piano);
    }

    @Test
    void testEqualsWithNullId() {
        ElectricGuitar electricGuitar1 = new ElectricGuitar();
        ElectricGuitar electricGuitar2 = new ElectricGuitar();
        assertNotEquals(electricGuitar1, electricGuitar2);
    }

    @Test
    void testHashCodeConsistency() {
        ElectricGuitar electricGuitar = electricGuitarRepository.save(new ElectricGuitar());
        int hashCode1 = electricGuitar.hashCode();
        int hashCode2 = electricGuitar.hashCode();
        assertEquals(hashCode1, hashCode2);
    }
}