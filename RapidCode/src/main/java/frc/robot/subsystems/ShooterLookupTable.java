package frc.robot.subsystems;

import java.util.HashMap;

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
        this.addEntry(10, 3000, 20.1);
    }
    /**
     * Returns the velocity for the flywheel for the given distance in inches
     * @param distanceInInches distance in inches that the robot is from the target
     * @return the velocity for the flywheel
     */
    public double getVelocityAtDistance(int distanceInInches) {
        return m_lookupTable.get(roundDistance(distanceInInches))[0];
    }
    /**
     * Returns the hood angle for the given distance in inches
     * @param distanceInInches distance in inches that the robot is from the target 
     * @return the hood angle
     */
    public double getAngleAtDistance(int distanceInInches) {
        return m_lookupTable.get(roundDistance(distanceInInches))[1];
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
