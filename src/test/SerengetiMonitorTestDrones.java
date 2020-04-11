package test;

import exceptions.ImpossibleToCaptureException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import serengetiPark.Drone;
import serengetiPark.GPS;
import serengetiPark.SerengetiMonitor;
import serengetiPark.Specimen;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test of some methods of SerengetiMonitor class with drones at the park.")
class SerengetiMonitorTestDrones {


    static SerengetiMonitor sm;
    static Drone d1;
    static Drone d2;
    static Specimen spx;
    static Specimen sp1;
    static Specimen sp2;
    static Specimen sp3;


    @BeforeAll
    static void setUpBeforeClass() {
        sm = SerengetiMonitor.getInstance();
        d1 = new Drone(new GPS(-1,1), "Drone2");
        d2 = new Drone(new GPS(-2.75, 2.75), "Drone2");
        sm.addDrone(d1);
        sm.addDrone(d2);
        spx = new Specimen("SpecimenX",new GPS(0,0));
        sp1 = new Specimen("Specimen1",new GPS(1,-1));
        sp2 = new Specimen("Specimen2",new GPS(-2.5,2.5));
        sp3 = new Specimen("Specimen3",new GPS(10,10));
        sm.addSpecimen(spx);
        sm.addSpecimen(sp1);
        sm.addSpecimen(sp2);
        sm.addSpecimen(sp3);
    }

    @Test
    @DisplayName("Test of captureWithAttempts() with 0 attempts and Drones at the park.")
    void testCaptureWithAttemptsZeroDrones() {
        assertThrows(ImpossibleToCaptureException.class, () -> {
            sm.captureWithAttempts(0, spx);
        });
    }

    @Test @DisplayName("Test of captureWithAttempts() with many attempts and Drones at the park.")
    void testCaptureWithAttemptsManyDrones() {
        try {
            sm.captureWithAttempts(100, sp2);
        } catch (ImpossibleToCaptureException e) {
            fail("Try again (random method), if keeps failing the method is not correct.");
        }
    }

    @Test @DisplayName("Test of collectImage() with no Specimens near and Drones at the park.")
    void testCollectImageZeroSpecimenDrones() {
        ArrayList<byte[][]> spIm = sm.collectImagesClosest(0, spx);
        assertEquals(0, spIm.size());
    }

    @Test @DisplayName("Test of collectImage() with one Specimen near and Drones at the park.")
    void testCollectImageOneSpecimenDrones() {
        ArrayList<byte[][]> spIm = sm.collectImagesClosest(200, spx);
        assertEquals(1, spIm.size());
    }
}