package test;

import org.junit.jupiter.api.*;
import serengetiPark.GPS;
import serengetiPark.Specimen;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test of some methods of Specimen class.")
class SpecimenTest {

    Specimen sp1;

    @BeforeEach
    void setUp() {
        sp1 = new Specimen("Specimen1", new GPS(-2.5,-2.5));
    }

    @Test @DisplayName("Test of kmsTraveled() with no points/locations registered.")
    void kmsTraveledNoPoints() {
        assertEquals(0.0, sp1.kmsTraveled());
    }

    @Test @DisplayName("Test of kmsTraveled() with one point/location registered.")
    void kmsTraveledOnePoint() {
        assertEquals(0.0, sp1.kmsTraveled());
        GPS ngps0 = sp1.initialLocation();
        GPS ngps1 = sp1.register();
        double dt = ngps0.distanceTo(ngps1);
        assertEquals(dt, sp1.kmsTraveled());
    }

    @Test @DisplayName("Test of kmsTraveled() with more than one point/location registered.")
    void kmsTraveledMorePoints() {
        assertEquals(0.0, sp1.kmsTraveled());
        GPS ngps0 = sp1.initialLocation();
        GPS ngps1 = sp1.register();
        GPS ngps2 = sp1.register();
        GPS ngps3 = sp1.register();
        double dt = ngps0.distanceTo(ngps1) + ngps1.distanceTo(ngps2) + ngps2.distanceTo(ngps3);
        assertEquals(dt, sp1.kmsTraveled());
    }

    @AfterEach
    void tearDown() {
        sp1.clearDayRoute();
    }

}