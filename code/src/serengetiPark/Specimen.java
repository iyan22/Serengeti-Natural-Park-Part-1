package serengetiPark;

import java.util.ArrayList;

/**
 * Represents a Specimen object on the Serengeti Park.
 *
 * @version Project - Part 1
 * @author Iyán Álvarez
 */
public class Specimen {

    // Attributes
    private String name;
    private GPS initialGPS;
    private TrackingDevice associatedTD;
    private ArrayList<GPS> dayRoute;

    // Constructors
    /**
     * This constructor, creates a Specimen object with the given attributes/values.
     * Creates the first GPS location on the day route.
     * @param name Name of the Specimen.
     * @param initialGPS Initial GPS location of the Specimen.
     */
    public Specimen(String name, GPS initialGPS) {
        this.name = name;
        this.initialGPS = initialGPS;
        this.associatedTD = new TrackingDevice(initialGPS);
        this.dayRoute = new ArrayList<GPS>();
        dayRoute.add(initialGPS);
    }

    // Getters
    /**
     * This method, returns the name of the Specimen.
     * @return Name of the Specimen.
     */
    public String getName() {
        return name;
    }
    /**
     * Obtains the first GPS location registered of the Specimen.
     * @return First registered location of the Specimen.
     */
    public GPS initialLocation() {
        return initialGPS;
    }
    /**
     * Obtains the last GPS location registered of the Specimen.
     * @return Last registered location of the Specimen.
     */
    public GPS lastLocation() {
        return dayRoute.get(dayRoute.size()-1);
    }

    // Methods
    /**
     * Obtains the kilometres that the Specimen has travelled in its day route.
     * @return The kilometres travelled by the Specimen this day.
     */
    public double kmsTraveled() {
        double kms = 0.0;
        for(int i = 0; i < dayRoute.size()-1; i++) {
            kms += dayRoute.get(i).distanceTo(dayRoute.get(i+1));
        }
        return kms;
    }
    /**
     * This method, registers a new GPS location on the day route list and returns that location.
     * @return The new GPS location that has been registered.
     */
    public GPS register() {
        GPS ngps = associatedTD.whereIAm();
        dayRoute.add(ngps);
        return ngps;
    }
    /**
     *  Simulates and updates positions of the Specimen in the period of time (minutes) given.
     * @param minutes Period of time that the Specimen will simulate move and register the locations.
     */
    public void updatePositions(int minutes) {
        while (minutes > 0) {
            register();
            minutes -= 30;
        }
    }
    /**
     * Clears the day route made by the animal, except the initial location.
     */
    public void clearDayRoute() {
        dayRoute.clear();
        dayRoute.add(initialGPS);
    }
    /**
     * Returns true if and only if, the Specimens name and initialLocation are the same.
     * Otherwise, will return false.
     * @param obj Object (Specimen) that will be compared.
     * @return true if and only if, the Specimens name are the same. Otherwise, false.
     */
    @Override public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        else if (!(obj instanceof Specimen)) {
            return false;
        }
        else {
            Specimen otherSpecimen = (Specimen) obj;
            return (this.getName().equals(otherSpecimen.getName()) & this.initialLocation().equals(otherSpecimen.initialLocation()));
        }
    }
    /**
     * Returns the basic information of a Specimen object in a String.
     * @return String with the basic information of a Specimen.
     */
    @Override public String toString() {
        return name + " " + lastLocation().toString();
    }

    // Main
    /**
     * Transitory help to make little tests on Specimen class.
     * @param args Not used
     */
    public static void main(String[] args) {

        System.out.println("Trying to create 3 GPS locations.");
        GPS house, park, river;
        house = new GPS(42.8591338, -2.6818614);
        park = new GPS(43.318334, -1.9812313);
        river = new GPS(43.2630126, -2.9349852);
        System.out.println("They have been created successfully.\n");

        System.out.println("Trying to create 3 Specimens.");
        Specimen dog = new Specimen("Dog", house);
        Specimen cat = new Specimen("Cat", park);
        Specimen bird = new Specimen("Bird", river);
        System.out.println("They have been created successfully.\n");

        System.out.println("Simulating and registering the move of the three Specimens with register() and obtaining kmsTraveled(). \n");
        GPS newL1 = dog.register();
        System.out.println("The dog has moved " + dog.kmsTraveled() + " km from " + house.toString() + " to " + newL1.toString() + ".");
        System.out.println();

        GPS newL2 = cat.register();
        System.out.println("The cat has moved " + cat.kmsTraveled() + " km from " + park.toString() + " to " + newL2.toString() + ".");
        System.out.println();

        GPS newL3 = bird.register();
        System.out.println("The bird has moved " + bird.kmsTraveled() + " km from " + river.toString() + " to " + newL3.toString() + ".");
        System.out.println();

        System.out.println("Simulating and updating positions of Bird for 75 minutes.");
        bird.updatePositions(75);
        System.out.println();

        System.out.println("Testing getName(), initialLocation() and lastLocation() methods.");
        System.out.println("This was the initial location of " + bird.getName() + " " + bird.initialLocation().toString() + ".");
        System.out.println("This was the last location of " + bird.getName() + " " + bird.lastLocation().toString() + ".");
        System.out.println();


        System.out.println("Bird's day route should have 5 registers (1+1+3) and has " + bird.dayRoute.size() + ".");
        System.out.println("Trying to clear Bird's day route.");
        bird.clearDayRoute();
        System.out.println("Bird's day route should have 1 registers and has " + bird.dayRoute.size() + ".");
        System.out.println();



    }

}
