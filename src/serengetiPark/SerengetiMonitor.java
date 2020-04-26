package serengetiPark;

import exceptions.CaptureErrorException;
import exceptions.ImpossibleToCaptureException;

import java.util.ArrayList;

/**
 * Represents the unique SerengetiMonitor object to control the Serengeti Park.
 *
 * @version Project - Part 1
 * @author Iyán Álvarez
 */
public class SerengetiMonitor {

    // Attributes
    private ArrayList<Drone> listDrone;
    private ArrayList<Specimen> listSpecimen;
    private final int COLLECT_IMAGE_ATTEMPTS = 5;

    // Singleton
    private static SerengetiMonitor instance;
    /**
     * Creates an instance of SerengetiMonitor. Based on Singleton concept.
     */
    private SerengetiMonitor() {
        listDrone = new ArrayList<Drone>();
        listSpecimen = new ArrayList<Specimen>();
    }
    /**
     * Returns the unique SerengetiMonitor instance, if exists returns the one that already exists, if not, it
     * creates a new instance of SerengetiMonitor and returns it. Based on Singleton concept.
     * @return The unique instance of SerengetiMonitor.
     */
    public static SerengetiMonitor getInstance() {
        if (instance == null) {
            instance = new SerengetiMonitor();
        }
        return instance;
    }

    // Methods
    /**
     * Adds a new Drone to the list of Drones that track Specimens in the Serengeti.
     * @param newDrone New Drone that is going to track Specimens at the Serengeti.
     */
    public void addDrone(Drone newDrone) {
        listDrone.add(newDrone);
    }
    /**
     * Adds a new Specimen to the list of Specimens that are being tracked in the Serengeti.
     * @param newSpecimen New Specimen that is going to be tracked.
     */
    public void addSpecimen(Specimen newSpecimen) {
        listSpecimen.add(newSpecimen);
    }
    /**
     * Obtains a list of the Specimens that are at a shortest distance that the given one, taking the
     * closestSpecimen Specimen as the start point of that distance, the closestSpecimen object won't be included in the
     * list.
     * @param kmRatio The maximum km distance/ratio to track Specimens near from specimen.
     * @param specimen The Specimen "start point" to find the other Specimens near it.
     * @return A list of the Specimens that are at a shortest distance that the given one from closestSpecimen.
     */
    public ArrayList<Specimen> closestSpecimensList(double kmRatio, Specimen specimen) {
        ArrayList<Specimen> listCl = new ArrayList<Specimen>();
        for (Specimen spt : listSpecimen) {
            if ((spt.lastLocation().distanceTo(specimen.lastLocation()) < kmRatio) && !(spt.equals(specimen))) {
                listCl.add(spt);
            }
        }
        return listCl;
    }
    /**
     * Communicate with the first available Drone, and this will try to take the capture of the Specimen
     * in the given attempts number.
     * @param attempts Attempts that each drone will try to take the image.
     * @param specimen Specimen that we want to photograph with the Drone.
     * @return Image of the specimen, it has been taken.
     * @throws ImpossibleToCaptureException when all the drones have tried to take the photograph and
     * it has been impossible to take it.
     */
    public byte[][] captureWithAttempts(int attempts, Specimen specimen) throws ImpossibleToCaptureException {
        Drone d;
        int i = 0;
        while (i < listDrone.size()) {                                                      // While more drones exists iterate
            d = listDrone.get(i);                                                           // Save the Drone on d variable
            GPS igps = d.whereIAm();                                                        // Save initial location on igps variable
            if (d.isAvailable()) {                                                          // If the drone is available we try
                int done = 0;
                while (done < attempts) {                                                   // While done < max attempts
                    try {
                        return d.moveAndCaptureImage(specimen.lastLocation());              // If capture taken successfully return it
                    } catch (CaptureErrorException e) {
                        done++;                                                             // If capture couldn't be taken done++;
                    }
                }                                                                           // If image couldn't be taken with that drone
                d.move(igps);                                                               // Moves to initial location
                d.setAvailable(true);                                                       // Sets state to available

            }                                                                               // After finishing attempts couldn't be taken, next drone
            i++;                                                                            // Or drone wasn't available, next drone
        }
        throw new ImpossibleToCaptureException();                                           // After trying with all the available drones couldn't be
                                                                                            // taken throw ImpossibleToCaptureException
    }
    /**
     * Obtain all the Specimens in the km ratio of the given specimen, it will communicate with the closest available Drone
     * of each Specimen, and this will try to take the capture of the Specimens in  the attempts specified on COLLECT_IMAGE_ATTEMPTS
     * constant.
     * @param kmRatio The maximum km distance/ratio to track Specimens near from specimen.
     * @param specimen The Specimen "start point" to find the other Specimens near it, won't be photographed.
     * @return List of the images taken, could be empty if no photo was taken.
     */
    public ArrayList<byte[][]> collectImagesClosest(double kmRatio, Specimen specimen) {
        // Optimisation on founding the closest drone to each specimen, has been finally implemented.
        // captureWithAttempts() won't be used to different functioning implemented at the method.
        ArrayList<Specimen> spList = closestSpecimensList(kmRatio, specimen);
        ArrayList<byte[][]> images = new ArrayList<byte[][]>();
        boolean notCap = false;
        int notCapT = 0;
        double dAct;
        double dMin = -1;
        Drone dBest = null;
        GPS igps = null;
        for (Specimen s : spList) {                                         // Finding the closest drone to each specimen
            for (Drone d : listDrone) {                                     // Iterating on all the drones at the park
                if (d.isAvailable()) {                                      // If it is available
                    dAct = d.whereIAm().distanceTo(s.lastLocation());       // Calculate distance from that drone to specimen
                    if ((dMin == -1 | dAct < dMin)) {                       // If there isn't a drone or is better
                        dBest = d;                                          // Save the drone for later use
                        dMin = dAct;                                        // Save distance for next drones searched
                    }
                }
            }                                                               // After finding the best available drone, if exists
            notCap = false;
            if (dBest != null) {
                int done = 0;
                igps = dBest.whereIAm();
                boolean taken = false;
                while (done < COLLECT_IMAGE_ATTEMPTS && !taken) {           // While done < determined attempts at constant.
                    try {                                                   // Try to take image;
                        byte[][] image = dBest.moveAndCaptureImage(s.lastLocation());
                        taken = true;                                       // // If exception not thrown, so capture has been taken
                        images.add(image);                                  // Add taken image to images ArrayList
                    } catch (CaptureErrorException e) {
                        done++;                                             // If attempt failed done++;
                    }
                }
                if (!taken) {                                               // If couldn't been taken within COLLECT_IMAGE_ATTEMPTS attempts
                    notCap = true;
                }
            }
            if (notCap) {                                                   // If specimen hasn't been photographed
                System.out.println(s.getName() + " couldn't be photographed.");
                notCapT++;                                                  // Added to total not captured
                dBest.move(igps);                                           // Return to initial location
                dBest.setAvailable(true);                                   // Set available
                System.out.println("");                                     // Empty line for keeping print format
            }
        }
        if (notCapT != 0) {                                              // If some specimen hasn't been photographed
            System.out.println(notCapT + " of " + spList.size() + " Specimens couldn't be photographed.");
        }
        return images;                                                      // Return taken images, could be an empty list
    }
    /**
     * Simulates the movement of all the Specimens at the Serengeti Park for the given minutes.
     * @param minutes Time in minutes for the Specimens movements simulation.
     */
    public void updatePositionsSpecimens(int minutes) {
        for(Specimen s: listSpecimen) {
            s.updatePositions(minutes);
        }
    }
    /**
     * Returns the basic information of a SerengetiMonitor object in a String.
     * @return String with the basic information of a SerengetiMonitor.
     */
    @Override public String toString() {
        String stDr, stSp;
        stDr = "(";
        for(Drone d: listDrone) {
            stDr += d.toString() + "  ";
        }
        stDr += ")";
        stSp = "(";
        for(Specimen s: listSpecimen) {
            stSp += s.toString() + "  ";
        }
        stSp += ")";
        return "SerengetiMonitor:  " + "\n" +
                " - Drones: " + stDr + "\n" +
                " - Specimens: " + stSp;
    }

    // Main
    /**
     * This main method, is a transitory help to make little tests on SerengetiMonitor class.
     * @param args Not used
     */
    public static void main(String[] args) {

        System.out.println("Creating SerengetiMonitor unique instance.");
        SerengetiMonitor sm = SerengetiMonitor.getInstance();
        System.out.println("Created successfully.");
        System.out.println();

        System.out.println("Creating 4 Specimen instance and adding them to SerengetiMonitor.");
        Specimen spx = new Specimen("SpecimenX",new GPS(0,0));
        Specimen sp1 = new Specimen("Specimen1",new GPS(1,-1));
        Specimen sp2 = new Specimen("Specimen2",new GPS(-2.5,2.5));
        Specimen sp3 = new Specimen("Specimen3",new GPS(10,10));
        sm.addSpecimen(spx);
        sm.addSpecimen(sp1);
        sm.addSpecimen(sp2);
        sm.addSpecimen(sp3);
        System.out.println("Specimens created and added successfully.");
        System.out.println();

        System.out.println("Creating 2 Drone instance and adding them to SerengetiMonitor.");
        Drone dr1 = new Drone(new GPS(1,1.2), "Drone1");
        Drone dr2 = new Drone(new GPS(-2.2,2.5), "Drone2");
        sm.addDrone(dr1);
        sm.addDrone(dr2);
        System.out.println("Drones created and added successfully.");
        System.out.println();

        System.out.println("Finding and printing Specimens in the ratio of 350km of " + spx.getName());
        ArrayList<Specimen> obt = sm.closestSpecimensList(350,spx);
        System.out.println("This are the Specimens nearby:");
        for(Specimen s: obt) {
            System.out.println(s.toString());
        }
        System.out.println("They should be Specimen1 and Specimen2.");
        System.out.println();

        System.out.println("Trying to capture Specimen 1 with 4 attempts.");
        try {
            sm.captureWithAttempts(4, sp1);
        } catch (ImpossibleToCaptureException e) {
            System.out.println("Specimen 1 is impossible to capture.");
        }
        System.out.println();

        System.out.println("Trying to take photos of the Specimens nearby the SpecimenX in the ratio of 500km.");
        ArrayList<byte[][]> imagesTaken = sm.collectImagesClosest(500, spx);
        System.out.println(sm.closestSpecimensList(500, spx).size() + " specimens are in the ratio of 500km to the first added specimen.");
        System.out.println(imagesTaken.size() + " images have been taken.");
        System.out.println();

        System.out.println("Showing SerengetiMonitor status after adding the Drones.");
        System.out.println(sm.toString());
        System.out.println();

    }

}
