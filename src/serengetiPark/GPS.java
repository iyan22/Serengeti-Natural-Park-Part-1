package serengetiPark;


/**
 * Represents a GPS object.
 *
 * @version Project - Part 1
 * @author Iyán Álvarez
 */
public class GPS {

    // Attributes
    private double latitude;
    private double longitude;
    private final int CORRECTION_VALUE = 85;

    // Constructors
    /**
     * Creates a GPS object that contains the given attributes for latitude and longitude.
     * @param latitude Latitude of the GPS.
     * @param longitude Longitude of the GPS.
     */
    public GPS(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    /**
     * Gets the latitude value of the GPS object.
     * @return  Latitude of the GPS.
     */
    public double getLatitude() {
        return latitude;
    }
    /**
     * Gets the longitude value of the GPS object.
     * @return Longitude of the GPS.
     */
    public double getLongitude() {
        return longitude;
    }

    // Methods
    /**
     * Obtains the distance from one GPS object to the other GPS object.
     * @param otherGPS The other GPS object.
     * @return The distance between this GPS and the other GPS.
     */
    public double distanceTo(GPS otherGPS) {
        return CORRECTION_VALUE * (Math.sqrt(Math.pow(this.getLatitude() - otherGPS.getLatitude(), 2) +
                                             Math.pow(this.getLongitude() - otherGPS.getLongitude(), 2)));
    }
    /**
     * Returns the basic information of a GPS object in a String.
     * @return Basic information of a GPS object in a String.
     */
    @Override public String toString() {
        return latitude + " " + longitude;
    }
    /**
     * Returns if two GPS instances are equal, this means the latitude and longitude is the same.
     * @param obj The other Object (GPS) instance that will be compared.
     * @return true if an only if the two GPS instances have the same latitude and longitude values,
     * otherwise false.
     */
   @Override public boolean equals(Object obj) {
       if (obj == null) {
           return false;
       }
       else if (!(obj instanceof GPS)) {
           return false;
       }
       else {
           GPS otherGPS = (GPS) obj;
           return ((this.getLatitude() == otherGPS.getLatitude()) &
                   (this.getLongitude() == otherGPS.getLongitude()));
       }
    }

    // Main
    /**
     * Transitory help to make little tests on GPS class.
     * @param args Not used
     */
    public static void main(String[] args) {

        System.out.println("This main method is a transitory help to make little tests on GPS class. \n");

        System.out.println("Trying to create two GPS instances, l1 and l2.");
        GPS l1, l2;
        l1 = new GPS(3,8);
        System.out.println("l1 created successfully.");
        l2 = new GPS(0,0);
        System.out.println("l2 created successfully. \n");

        System.out.println("Trying getLatitude() and getLongitude() with l1 GPS instance.");
        System.out.println("Latitude: " + l1.getLatitude() + " Longitude: " + l1.getLongitude() + "\n");

        System.out.println("Trying distanceTo() from l1 to l2.");
        System.out.println("Is " + l1.distanceTo(l2) + " and should be approximately 726.2403... \n");

        System.out.println("Trying equals() with l1 and l2.");
        System.out.println("Is " + l1.equals(l2) + " and should be false. \n");

        System.out.println("Trying toString() with l2 GPS instance.");
        System.out.println(l2.toString());
    }

}
