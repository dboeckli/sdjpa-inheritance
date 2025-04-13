package ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.tableperclass;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.tableperclass.Dolphin;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
class DolphinRepositoryTest {

    @Autowired
    DolphinRepository dolphinRepository;

    @Test
    void testSaveDolphin() {
        Dolphin dolphin = new Dolphin();
        dolphin.setBodyTemp(37);
        dolphin.setSpecies("X");
        Dolphin savedDolphin = dolphinRepository.save(dolphin);

        Optional<Dolphin> foundDolphin = dolphinRepository.findById(savedDolphin.getId());
        assertAll("Dolphin",
            () -> assertTrue(foundDolphin.isPresent(), "Electric guitar should be found"),
            () -> foundDolphin.ifPresent(presentDolphin -> {
                assertNotNull(presentDolphin.getId(), "ID should not be null");
                assertEquals(37, presentDolphin.getBodyTemp());
                assertEquals("X", presentDolphin.getSpecies());
            })
        );
    }

    @Test
    void equalsAndHashCodeTestWithProxy() {
        Dolphin saved = dolphinRepository.save(new Dolphin());

        Dolphin dolphin = dolphinRepository.findById(saved.getId()).orElseThrow();
        Dolphin dolphinProxy = dolphinRepository.getReferenceById(saved.getId());

        assertEquals(dolphin, dolphinProxy);
        assertEquals(dolphinProxy, dolphin);
        assertEquals(dolphin.hashCode(), dolphinProxy.hashCode());
    }

    @Test
    void testNotEqualsWithDifferentIds() {
        Dolphin dolphin1 = dolphinRepository.save(new Dolphin());
        Dolphin dolphin2 = dolphinRepository.save(new Dolphin());

        assertNotEquals(dolphin1, dolphin2);
    }

    @Test
    void testEqualsWithNullAndOtherClass() {
        Dolphin dolphin = dolphinRepository.save(new Dolphin());

        assertNotEquals(null, dolphin);
        assertNotEquals(new Object(), dolphin);
    }

    @Test
    void testHashCodeConsistency() {
        Dolphin dolphin = dolphinRepository.save(new Dolphin());
        int hashCode1 = dolphin.hashCode();
        int hashCode2 = dolphin.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

}