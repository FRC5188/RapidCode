package frc.robot.subsystems;

import java.util.HashMap;

import frc.robot.Constants;
import frc.robot.subsystems.Shooter.HoodPosition;

public class ShooterLookupTable {
    private HashMap<Integer, Object[]> m_lookupTable;

    /**
     * Creates a new instance of the shooter's lookup table
     */
    public ShooterLookupTable() {
        m_lookupTable = new HashMap<Integer, Object[]>();

        // Add entries here using this syntax
        // First is distance in feet, second is flywheel speed, last is hood angle
        // Below is an example
        // !!EVERY ANGLE FOR THE HOOD MUST BE RELATIVE TO THE LOW POT STOP!!
        // This is done so that if the sensor has to be realigned, all of this data can still be accurate
        
        this.addEntry(Constants.FRONT_OF_FENDER_DISTANCE, 0.45, HoodPosition.Fender);
        this.addEntry(Constants.BACK_OF_FENDER_DISTANCE, 0.55, HoodPosition.Far);
        this.addEntry(Constants.NO_SHOOTER_SPEED_DISTANCE, 0, HoodPosition.Fender);
    }
    /**
     * Returns the velocity for the flywheel for the given distance in inches
     * @param distanceInInches distance in inches that the robot is from the target
     * @return the velocity for the flywheel. Will return 0 if the distance has no data in the table.
     */
    public Double getVelocityAtDistance(int distanceInInches) {
        if (m_lookupTable.get(roundDistance(distanceInInches)) == null) return (double) 0;
        else return (Double) m_lookupTable.get(roundDistance(distanceInInches))[0];
    }
    /**
     * Returns the hood angle for the given distance in inches
     * @param distanceInInches distance in inches that the robot is from the target 
     * @return the hood position. Will return the fender state if the distance has no data in the table.
     */
    public HoodPosition getHoodPositionAtDistance(int distanceInInches) {
        if (m_lookupTable.get(roundDistance(distanceInInches)) == null) return HoodPosition.Fender;
        else return (HoodPosition) m_lookupTable.get(roundDistance(distanceInInches))[1];
        
    }

    public void editVelocityEntry(int distanceInInches, double addTo) {
        Object[] o = {((double) m_lookupTable.get(roundDistance(distanceInInches))[0]) + addTo, m_lookupTable.get(roundDistance(distanceInInches))[1]};
        m_lookupTable.put(roundDistance(distanceInInches), o);
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
     * @param hoodPosition hood position at distance
     */
    private void addEntry(int distanceInFeet, double flywheelSpeed, HoodPosition hoodPosition) {
        Object[] e = {flywheelSpeed, hoodPosition};
        m_lookupTable.put(distanceInFeet, e);
    }
}
