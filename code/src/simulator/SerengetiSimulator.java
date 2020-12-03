package simulator;

import serengetiPark.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simulator of the Serengeti Natural Park.
 *
 * @author Iyán Álvarez
 * @version Project - Part 1
 *
 */
public class SerengetiSimulator {
    /**
     * This main method, is going to simulate Serengeti Natural Park.
     * @param args Not used
     */
    public static void main(String[] args) {

        // Declare the variables that are going to be used
        SerengetiMonitor sm;
        boolean found = false;
        String sname;
        GPS sgps;
        Specimen s;
        Specimen sfirst = null;
        Scanner sc = new Scanner(System.in);
        Scanner fr;


        // Generate and create the object that represents the monitoring of the Serengeti Park.
        System.out.println("Trying to create SerengetiMonitor instance.");
        sm = SerengetiMonitor.getInstance();
        System.out.println("Created successfully.");
        System.out.println();

        // Create 10 specimens of different types in nearby locations and add them to the Serengeti Park.
        // This task will be done with a file named locations.txt
        System.out.println("Trying to add 10 Specimens to the park on the given coordinates.");
        while (!(found)) {
            try {
                fr = new Scanner(new FileReader("files/locations.txt"));
                for (int i = 0; i < 10; i++) {
                    sname = "Specimen" + (i + 1);
                    String[] data = fr.nextLine().split(" ");
                    double la = Double.parseDouble(data[0]);
                    double lo = Double.parseDouble(data[1]);
                    sgps = new GPS(la, lo);
                    //fr.nextLine();
                    s = new Specimen(sname, sgps);
                    // Save the first added Specimen for later action.
                    if (i == 0) {
                        sfirst = s;
                    }
                    sm.addSpecimen(s);
                }
                fr.close();
                found = true;
            } catch (FileNotFoundException e) {
                System.out.println("File locations.txt should be at files folder on the project. Try again and press enter.");
                sc.nextLine();
            }
        }
        sc.close();
        
        System.out.println("Showing SerengetiMonitor status after adding 10 Specimens.");
        System.out.println(sm.toString());
        System.out.println();


        // Simulate 6 hours of movement registers.
        System.out.println("Simulating 6 hours of movement registers on all the Specimens in the park.");
        sm.updatePositionsSpecimens(360);
        System.out.println("Showing SerengetiMonitor status after 6 hours of movement in the park.");
        System.out.println(sm.toString());
        System.out.println();

        // Obtain all the Specimens in a distance of 300km nearby the first added Specimen.
        // Print the data of all the Specimens showing name, position and distance.
        System.out.println("Showing the data of the Specimens in the ratio of 300km to the first Specimen added.");
        ArrayList<Specimen> nearbySp = sm.closestSpecimensList(300, sfirst);
        for (Specimen sn:  nearbySp) {
            System.out.println(sn.toString() + " " + sn.lastLocation().distanceTo(sfirst.lastLocation()) + "km.");
        }
        System.out.println();

        // Add 3 drones with the same GPS location and add them to the Serengeti Park.
        System.out.println("Adding 3 drones to the park, the first one is not available.");
        Drone d;
        String dname;
        GPS dgps = new GPS(-2.949865, 35.062473);
        for(int i = 0; i < 3; i++) {
            dname = "Drone" + (i+1);
            d = new Drone(dgps, dname);
            // First drone is not available.
            if (i == 0) {
                d.setAvailable(false);
            }
            sm.addDrone(d);
        }
        System.out.println("Showing SerengetiMonitor status after adding the Drones.");
        System.out.println(sm.toString());
        System.out.println();

        // Take photos of the Specimens nearby the first added Specimen in the ratio of 90km and show how many images have been taken.
        System.out.println("Trying to take photos of the Specimens nearby the first added Specimen in the ratio of 90km.");
        ArrayList<byte[][]> imagesTaken = sm.collectImagesClosest(90, sfirst);
        System.out.println(sm.closestSpecimensList(90, sfirst).size() + " specimens are in the ratio of 90km to the first added specimen.");
        System.out.println(imagesTaken.size() + " images have been taken.");
    }

}

