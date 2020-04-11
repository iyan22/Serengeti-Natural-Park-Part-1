package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import serengetiPark.GPS;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test of some methods of GPS class.")
class GPSTest {

    static double CORRECTION_VALUE = 85;
    static GPS p1, p2, p3;

    @BeforeAll
    static void setUpBeforeClass() {
        p1 = new GPS(3.5,9);
        p2 = new GPS(0,0);
        p3 = new GPS(3.5,9);
    }

    @Test @DisplayName("Test of distanceTo() with different locations.")
    void testDistanceToDifferent() {
        double expected = CORRECTION_VALUE * (Math.sqrt(Math.pow(3.5,2) + Math.pow(9,2)));
        assertEquals(expected, p1.distanceTo(p2));
    }
    @Test @DisplayName("Test of distanceTo() with the same locations.")
    void testDistanceToSame() {
        double expected = (CORRECTION_VALUE * (Math.sqrt(Math.pow((3.5 - 3.5), 2) + Math.pow((9 - 9), 2))));
        assertEquals(expected, p1.distanceTo(p3));

    }


    static class SerengetiMonitorTestDrones {

    }
}