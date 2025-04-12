package ch.dboeckli.guru.jpa.sdjpa.inheritance.bootstrap;

import ch.dboeckli.guru.jpa.sdjpa.inheritance.domain.joined.ElectricGuitar;
import ch.dboeckli.guru.jpa.sdjpa.inheritance.repository.ElectricGuitarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final ElectricGuitarRepository electricGuitarRepository;

    @Override
    public void run(String... args) {
        ElectricGuitar eg = new ElectricGuitar();
        eg.setNumberOfStrings(6);
        eg.setNumberOfPickups(2);
        electricGuitarRepository.save(eg);
    }
}
