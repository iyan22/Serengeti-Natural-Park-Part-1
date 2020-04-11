package serengetiPark;

import exceptions.CaptureErrorException;

/**
 * Represents a Drone object on the Serengeti Park.
 *
 * @version Project - Part 1
 * @author Iyán Álvarez
 */
public class Drone {

    // Attributes
    private String name;
    private boolean available;
    private GPS gps;
    private final int IMAGE_WIDTH = 1000;
    private final int IMAGE_HEIGHT = 1000;

    // Constructors
    /**
     * Creates a Drone object with the given attributes/values and it is available.
     * @param gps Location of the GPS.
     * @param name Name of the Drone object.
     */
    public Drone(GPS gps, String name) {
        this.gps = gps;
        this.name = name;
        setAvailable(true);
    }

    // Getters and setters
    /**
     * Returns if the Drone object is available (true) or not (false).
     * @return true, if and only if is available; otherwise false.
     */
    public boolean isAvailable() {
        return available;
    }
    /**
     * Sets the availability of the Drone object to available (true) or not (false).
     * @param available true, if it is available; otherwise false.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    /**
     * Returns the GPS location of the drone.
     * @return GPS object's value.
     */
    public GPS whereIAm() {
        return gps;
    }

    // Methods
    /**
     * Sets drone to not available, after that, it moves to the given location and tries to capture an image, if the
     * image has been taken successfully, the drone returns to the initial position, sets to available and returns the
     * taken image. Otherwise, CaptureErrorException will be thrown, when it was not possible to capture the image.
     * Drone must be available (true).
     * @param newLocation GPS location to move the Drone.
     * @return If succeded, the taken image at the position.
     * @throws CaptureErrorException when it was not possible to capture the image.
     */
    public byte[][] moveAndCaptureImage(GPS newLocation) throws CaptureErrorException {
        GPS start = gps;
        setAvailable(false);
        if (!(newLocation.equals(whereIAm()))) {
            move(newLocation);
        }
        byte[][] image = tryToCapture();
        move(start);
        setAvailable(true);
        System.out.println();
        return image;
    }
    /**
     * Simulates the travel time and drone position change.
     * @param newLocation New GPS location to move.
     */
    public void move(GPS newLocation) {
        System.out.print( this.name + " moving to "+ newLocation +" ...");
        double distance= gps.distanceTo(newLocation);
        boolean yes = false;
        while (!yes) {
            try {
                Thread.sleep((long) distance  * 100);
                yes = true;
            } catch (InterruptedException e) {
                System.out.print(" couldn't sleep ... ");
                Thread.currentThread().interrupt();
            }
        }
        gps = newLocation;
        System.out.println(" arrived.");
        }
    /**
     * Simulates image capture with potential failure.
     * @return Byte matrix of the captured image.
     * @throws CaptureErrorException when it was not possible to capture the image.
     */
    private byte[][] tryToCapture() throws CaptureErrorException{
        System.out.print("Trying to take capture ...  ");

        byte[][] image = new byte[IMAGE_WIDTH][IMAGE_HEIGHT];

        // Simulates the possibility or not of image capture
        int time = (int) Math.floor(Math.random()*116 + 1);
        // System.out.print(time);
        if (time % 3 == 0) {
            throw new CaptureErrorException();
        }
        System.out.print(" image was taken successfully.");
        System.out.println();
        return image;
    }
    /**
     * Returns the basic information of a Drone object in a String.
     * @return The basic information of a Drone object in a String.
     */
    @Override public String toString() {
        return name + ", " + available + ", " + gps.toString();
    }

    // Main
    /**
     * Transitory help to make little tests on Drone class.
     * @param args Not used
     */
    public static void main(String[] args) {

        System.out.println("This main method is a transitory help to make little tests on Drone class. \n");

        System.out.println("Trying to create two Drone instances, d1 and d2.");
        Drone d1, d2;
        d1 = new Drone(new GPS(3,8.5),"Drone1");
        System.out.println("d1 created successfully.");
        d2 = new Drone(new GPS(0,0),"Drone2");
        System.out.println("l2 created successfully. \n");

        System.out.println("Trying isAvailable() with d1 Drone instance.");
        System.out.println("Is " + d1.isAvailable() + " and should be true. \n");

        System.out.println("Trying setAvailable() with d1 Drone instance. \n");
        d1.setAvailable(false);
        System.out.println("Is " + d1.isAvailable() + " and should be false. \n");

        System.out.println("Trying to know location of d1 and d2.");
        // There is no need to use toString() method String concatenate does it by default.
        System.out.println("D1 location: " + d1.whereIAm());
        System.out.println("D2 location: " + d2.whereIAm());
        System.out.println("\n");

        System.out.print("Trying to move d1 from + (3, 8.5) to (3.5, 8). \n");
        d1.move(new GPS(3.5,8));
        System.out.println("\n");

        System.out.println("Trying to take image with d2.");
        try {
            d2.tryToCapture();
        } catch (CaptureErrorException e) {
            System.out.println("CaptureErrorException has been thrown, image couldn't be taken. \n");
        }
        System.out.println("\n");

        System.out.print("Trying to move d2 from (0, 0) to (1, 1). \n");
        try {
            d2.moveAndCaptureImage(new GPS(1,1));
        } catch (CaptureErrorException e) {
            System.out.println("CaptureErrorException has been thrown, image couldn't be taken. \n");
        }
        System.out.println("\n");

        System.out.println("Trying toString() with d2 Drone instance.");
        System.out.println(d2.toString());
    }

}
