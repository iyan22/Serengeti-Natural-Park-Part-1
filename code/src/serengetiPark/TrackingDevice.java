package serengetiPark;

import serengetiPark.GPS;

import java.util.Random;

/**
 * Represents a TrackingDevice, that simulate the GPS position of the device.
 *
 * @author PMOO Teachers and Iyán Álvarez
 * @version Project - Part 1
 * 
 */
public class TrackingDevice {

    // Attributes
    private GPS lastPosition;
    private Random displacement = new Random(); // 0 - not move, 1 -low speed, 2 - high speed
    private Random orientation = new Random(); // 0(-135), 1(-90), 2(-45), 3(0), 4(45), ...
    private final double DISTANCE = 10;
    private final double CORRECTION_VALUE = 85;

    // Constructors
    /**
     * This constructor, creates a TrackingDevice object with the given attribute/value.
     * @param lastPosition Last location of the GPS.
     */
    public TrackingDevice(GPS lastPosition) {
        this.lastPosition = lastPosition;
    }

    // Methods
    /**
     *  This method, simulates the movement for a period of time (30 minutes), creating and registering
     *  the new GPS location and returning it.
     * @return The new GPS location after movement simulation.
     */
    public GPS whereIAm() {
        int d = displacement.nextInt(3);
        // System.out.println("Move & velocity " + d);
        if (d > 0) {
            double latitude = lastPosition.getLatitude();
            double longitude = lastPosition.getLongitude();
            int o = orientation.nextInt(8);
            // System.out.println("Orientation: " + o);
            double a = d * DISTANCE / CORRECTION_VALUE;
            // System.out.println("Distance: " + a * CORRECTION_VALUE);
            switch (o) {
                case 0:
                    latitude = latitude + a * Math.sin(-135*Math.PI/180);
                    longitude = longitude + a * Math.cos(-135*Math.PI/180);
                    break;
                case 1:
                    latitude = latitude - a;
                    break;
                case 2:
                    latitude = latitude + a * Math.sin(-45*Math.PI/180);
                    longitude = longitude + a * Math.cos(-45*Math.PI/180);
                    break;
                case 3:
                    longitude = longitude + a;
                    break;
                case 4:
                    latitude = latitude + a * Math.sin(45*Math.PI/180);
                    longitude = longitude + a * Math.cos(45*Math.PI/180);
                    break;
                case 5:
                    latitude = latitude + a;
                    break;
                case 6:
                    latitude = latitude + a * Math.sin(135*Math.PI/180);
                    longitude = longitude + a * Math.cos(135*Math.PI/180);
                    break;
                default:
                    longitude = longitude - a;
            }
            lastPosition = new GPS(latitude, longitude);
        }
        return lastPosition;
    }
    /**
     * This method, returns the basic information of a TrackingDevice object in a String.
     * @return The basic information of a TrackingDevice object in a String.
     */
    @Override public String toString() {
        return lastPosition.toString();
    }

    // Main
    /**
     * This main method, is a transitory help to make little tests on TrackingDevice class.
     * @param args Not used
     */
    public static void main(String[] args) {

        System.out.println("This main method is a transitory help to make little tests on TrackingDevice class. \n");

        System.out.println("Trying to create a TrackingDevice: td1.");
        TrackingDevice td1 = new TrackingDevice(new GPS(0,2.5));
        System.out.println("td1 created successfully.\n");

        System.out.println("Simulating move with method whereIAm(), this is the new GPS location: ");
        System.out.println(td1.whereIAm());
        System.out.println();

        System.out.println("Trying toString() with td1 TrackingDevice instance.");
        System.out.println(td1.toString());
        System.out.println();

    }

}