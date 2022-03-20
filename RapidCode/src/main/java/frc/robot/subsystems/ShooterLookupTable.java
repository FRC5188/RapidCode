package frc.robot.subsystems;

import java.util.HashMap;

import frc.robot.Constants;

public class ShooterLookupTable {
    private HashMap<Integer, double[]> m_lookupTable;

    /**
     * Creates a new instance of the shooter's lookup table
     */
    public ShooterLookupTable() {
        m_lookupTable = new HashMap<Integer, double[]>();

        // Add entries here using this syntax
        // First is distance in feet, second is flywheel speed, last is hood angle
        // Below is an example
        // !!EVERY ANGLE FOR THE HOOD MUST BE RELATIVE TO THE LOW POT STOP!!
        // This is done so that if the sensor has to be realigned, all of this data can still be accurate
        this.addEntry(-1, 0.45, Constants.LOW_POT_STOP + 57);
        this.addEntry(Constants.FRONT_OF_FENDER_DISTANCE, 0.45, Constants.LOW_POT_STOP + 57);
        this.addEntry(1, 0.42, Constants.LOW_POT_STOP + 57);
        this.addEntry(2, 0.42, Constants.LOW_POT_STOP + 57);
        this.addEntry(3, 0.42, Constants.LOW_POT_STOP + 57);
        this.addEntry(4, 0.47, Constants.LOW_POT_STOP + 200);
        this.addEntry(Constants.BACK_OF_FENDER_DISTANCE, 0.40, Constants.LOW_POT_STOP + 160);
        this.addEntry(6, 0.44, Constants.LOW_POT_STOP + 260);
        this.addEntry(7, 0.45, Constants.LOW_POT_STOP + 200);
        this.addEntry(8, 0.46, Constants.LOW_POT_STOP + 230);
        this.addEntry(9, 0.47, Constants.LOW_POT_STOP + 260);
        this.addEntry(10, 0.47, Constants.LOW_POT_STOP + 270);
    }
    /**
     * Returns the velocity for the flywheel for the given distance in inches
     * @param distanceInInches distance in inches that the robot is from the target
     * @return the velocity for the flywheel. Will return 0 if the distance has no data in the table.
     */
    public double getVelocityAtDistance(int distanceInInches) {
        if (m_lookupTable.get(roundDistance(distanceInInches)) == null) return 0;
        else return m_lookupTable.get(roundDistance(distanceInInches))[0];
    }
    /**
     * Returns the hood angle for the given distance in inches
     * @param distanceInInches distance in inches that the robot is from the target 
     * @return the hood angle. Will return the low pot stop constant + 50 if the distance has no data in the table.
     */
    public double getAngleAtDistance(int distanceInInches) {
        if (m_lookupTable.get(roundDistance(distanceInInches)) == null) return Constants.LOW_POT_STOP + 50;
        else return m_lookupTable.get(roundDistance(distanceInInches))[1];
    }
    /**
     * Rounds a distance in inches to feet, which is then used to get velocity and hood angle
     * @param distanceInInches distance in inches
     * @return distance in feet rounded to the nearest foot
     */
    private int roundDistance(int distanceInInches) {
        float inFeet = (distanceInInches / 12);
        return Math.round(inFeet);
    }
    /**
     * Adds an entry to the lookup table
     * @param distanceInFeet distance in feet from target
     * @param flywheelSpeed speed of flywheel at distance
     * @param hoodAngle hood angle at distance
     */
    private void addEntry(int distanceInFeet, double flywheelSpeed, double hoodAngle) {
        double[] e = {flywheelSpeed, hoodAngle};
        m_lookupTable.put(distanceInFeet, e);
    }
}
