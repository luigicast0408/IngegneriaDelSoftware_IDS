package com.example.gradient.factory;

import com.example.gradient.algorithm.GradientAlgorithm;
import com.example.gradient.algorithm.SobelAlgorithm;
import com.example.gradient.algorithm.PrewittAlgorithm;
import com.example.gradient.algorithm.RobertsAlgorithm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradientFactoryTest {
    /**
     * This class tests the Factory Design Pattern implementation
     * by verifying that the correct GradientAlgorithm instance is
     * created based on the given input type.
     */


    @Test
    public void testCreateSobelAlgorithm() {
        GradientAlgorithm algorithm = GradientFactory.create("sobel");
        assertNotNull(algorithm);
        assertInstanceOf(SobelAlgorithm.class, algorithm);
    }

    @Test
    public void testCreatePrewittAlgorithm() {
        GradientAlgorithm algorithm = GradientFactory.create("prewitt");
        assertNotNull(algorithm);
        assertInstanceOf(PrewittAlgorithm.class, algorithm);
    }

    @Test
    public void testCreateRobertsAlgorithm() {
        GradientAlgorithm algorithm = GradientFactory.create("roberts");
        assertNotNull(algorithm);
        assertInstanceOf(RobertsAlgorithm.class, algorithm);
    }

    @Test
    public void testCreateUnknownAlgorithmThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GradientFactory.create("unknown");
        });
        assertTrue(exception.getMessage().contains("Type of algorithm unknown"));
    }
}
