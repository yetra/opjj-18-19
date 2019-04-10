import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LSystemBuilderImplTest {

    @Test
    public void testGenerate() {
        String[] data = new String[] {
                "axiom F",
                "production F F+F--F+F"
        };
        LSystemBuilder builder = new LSystemBuilderImpl();
        LSystem system = builder.configureFromText(data).build();

        assertEquals(system.generate(0), "F");
        assertEquals(system.generate(1), "F+F--F+F");
        assertEquals(system.generate(2), "F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F");
    }
}
