package dev.gallon.algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    void ffs() {
        assertEquals(2, Utility.ffs(10^4));
        assertEquals(3, Utility.ffs(4^0));
        assertEquals(5, Utility.ffs(6^22));
    }

    @Test
    void posDiff() {
        assertEquals(3, Utility.posDiff(10, 4));
        assertEquals(8, Utility.posDiff(6, 22));
        assertEquals(5, Utility.posDiff(4, 0));
    }
}