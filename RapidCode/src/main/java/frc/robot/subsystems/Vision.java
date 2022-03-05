package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase {
    
    private static double HEIGHT_TO_TARGET = Constants.TARGET_HEIGHT_INCHES - Constants.CAMERA_HEIGHT_INCHES;

    NetworkTable m_networkTable;
    NetworkTableEntry m_tx;
    NetworkTableEntry m_ty;
    NetworkTableEntry m_tv;
    NetworkTableEntry m_ledMode;

    double m_horizontalRotation;
    double m_verticalRotation;
    boolean m_hasTarget;

    private double m_count;
    /**
     * Get the Network Table from the LimeLight and then get the Horizontial Rotation(tx) the Vertical Rotation(ty) the mode of the LED(ledMode)
     * and then if it has a target(tv).
     */
    public Vision() {
        m_networkTable = NetworkTableInstance.getDefault().getTable("limelight");
        m_tx = m_networkTable.getEntry("tx");
        m_ty = m_networkTable.getEntry("ty");
        m_tv = m_networkTable.getEntry("tv");
        m_ledMode = m_networkTable.getEntry("ledMode");
        m_ledMode.setValue(0);

        m_count = 0;
    }

    @Override
    public void periodic() {
        m_horizontalRotation = m_tx.getDouble(0.0);
        m_verticalRotation = m_ty.getDouble(0.0);
        m_hasTarget = (m_tv.getDouble(0.0) == 1.0);

        //commented out, casing confusion. 
        // also, this command is running even in disabled. not sure if it should GH
        // Prints out some limelight information every half second
        // if (m_count % 25 == 0) {
            // System.out.printf("Distance: %f Angle: %f Target: %b\n", getDistanceToTarget(), m_verticalRotation, m_hasTarget);
        // }
        // m_count++;
    }
    /**
     * Returns the horizontal angle of the robot relative to the target
     * @return the horizontal angle of the robot relative to the target
     */
    public double getRotationAngle() {
        return m_horizontalRotation;
    }
    /**
     * Method which returns the distance of the robot to the base of the target in inches using math class in Java.
     * @return The distance in inches that the robot is from the base of the target which it has.
     */
    public double getDistanceToTarget() {
        // input of m_verticalRotation, output horizontal distance to target
        return HEIGHT_TO_TARGET / (Math.tan(Units.degreesToRadians(m_verticalRotation + Constants.CAMERA_PITCH_DEGREES)));
    }
    /**
     * Method which returns if the LimeLight(Camera) has a aquired a target.
     * @return primative boolean which if true means the LimeLight has a target. 
     */
    public boolean hasTarget() {
        return m_hasTarget;
    }
}
