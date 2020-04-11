package test;

import exceptions.ImpossibleToCaptureException;
import org.junit.jupiter.api.*;
import serengetiPark.*;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test of some default methods of SerengetiMonitor class.")
class SerengetiMonitorTestDefault {

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

    @Test @DisplayName("Test of closestSpecimensList() with 0 km ratio.")
    void testClosestSpecimensListZeroRatio() {
        ArrayList<Specimen> exp = new ArrayList<Specimen>();
        assertEquals(0, sm.closestSpecimensList(0,spx).size());
    }

    @Test @DisplayName("Test of closestSpecimensList() with 350 km ratio.")
    void testClosestSpecimensListBigRatio() {
        ArrayList<Specimen> exp = new ArrayList<Specimen>();
        exp.add(sp1);
        exp.add(sp2);
        assertEquals(exp, sm.closestSpecimensList(350,spx));
    }

}