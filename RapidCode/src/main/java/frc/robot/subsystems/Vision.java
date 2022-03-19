package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase {
    private Dashboard m_dashboard;
    
    private static double HEIGHT_TO_TARGET = Constants.TARGET_HEIGHT_INCHES - Constants.CAMERA_HEIGHT_INCHES;

    private NetworkTable m_networkTable;
    private NetworkTableEntry m_tx;
    private NetworkTableEntry m_ty;
    private NetworkTableEntry m_tv;
    private NetworkTableEntry m_ledMode;

    private double m_horizontalRotation;
    private double m_verticalRotation;
    private boolean m_hasTarget;

    /**
     * Creates a new instance of the Vision subsystem
     * @param dashboard the dashboard instance for the robot
     */
    public Vision(Dashboard dashboard) {
        m_dashboard = dashboard;

        m_networkTable = NetworkTableInstance.getDefault().getTable("limelight");
        m_tx = m_networkTable.getEntry("tx");
        m_ty = m_networkTable.getEntry("ty");
        m_tv = m_networkTable.getEntry("tv");
        m_ledMode = m_networkTable.getEntry("ledMode");
        NetworkTableEntry camMode = m_networkTable.getEntry("camMode");
        camMode.setValue(0);
        m_ledMode.setValue(1);
    }

    @Override
    public void periodic() {
        m_horizontalRotation = m_tx.getDouble(0.0);
        m_verticalRotation = m_ty.getDouble(0.0);
        m_hasTarget = (m_tv.getDouble(0.0) == 1.0);

        m_dashboard.setHasTarget(m_hasTarget);
        m_dashboard.setDistanceToTarget((int) getDistanceToTarget());
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
