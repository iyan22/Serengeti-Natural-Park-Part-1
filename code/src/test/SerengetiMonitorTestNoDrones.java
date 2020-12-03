package test;

import exceptions.ImpossibleToCaptureException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import serengetiPark.GPS;
import serengetiPark.SerengetiMonitor;
import serengetiPark.Specimen;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test of some methods of SerengetiMonitor class with no drones at the park.")
class SerengetiMonitorTestNoDrones {


    static SerengetiMonitor sm;
    static Specimen spx;
    static Specimen sp1;
    static Specimen sp2;
    static Specimen sp3;

    @BeforeAll
    static void setUpBeforeClass() {
        sm = SerengetiMonitor.getInstance();
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
    @DisplayName("Test of captureWithAttempts() with 0 attempts and no Drones at the park.")
    void testCaptureWithAttemptsZeroNoDrones() {
        assertThrows(ImpossibleToCaptureException.class, () -> {
            sm.captureWithAttempts(0, sp1);
        });
    }

    @Test @DisplayName("Test of captureWithAttempts() with many attempts and no Drones at the park.")
    void testCaptureWithAttemptsManyNoDrones() {
        assertThrows(ImpossibleToCaptureException.class, () -> {
            sm.captureWithAttempts(100, sp2);
        });
    }

    @Test @DisplayName("Test of collectImage() with no Specimens near and no Drones at the park.")
    void testCollectImageZeroSpecimenNoDrones() {
        ArrayList<byte[][]> spIm = sm.collectImagesClosest(0, spx);
        assertEquals(0, spIm.size());
    }

    @Test @DisplayName("Test of collectImage() with one Specimen near and no Drones at the park.")
    void testCollectImageOneSpecimenNoDrones() {
        ArrayList<byte[][]> spIm = sm.collectImagesClosest(200, spx);
        assertEquals(0, spIm.size());
    }
}