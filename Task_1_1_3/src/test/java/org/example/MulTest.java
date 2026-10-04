package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void evalTest() {
        Expression mul = new Mul(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = Map.of("x", 4, "y", 3);

        assertEquals(12, mul.eval(vars));
    }

    @Test
    void constructorNullOperandThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Mul(null, new Number(1)));
        assertThrows(IllegalArgumentException.class, () -> new Mul(new Number(1), null));
    }

    @Test
    void mulToString() {
        Expression mul = new Mul(new Number(3), new Variable("y"));

        assertEquals("(3*y)", mul.toString());
    }
}
