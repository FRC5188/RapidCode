package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase {
    private static double HEIGHT_TO_TARGET = Constants.TARGET_HEIGHT_METERS - Constants.CAMERA_HEIGHT_METERS;

    NetworkTable m_networkTable;
    NetworkTableEntry m_tx;
    NetworkTableEntry m_ty;
    NetworkTableEntry m_tv;

    double m_horizontalRotation;
    double m_verticalRotation;
    boolean m_hasTarget;

    public Vision() {
        m_networkTable = NetworkTableInstance.getDefault().getTable("limelight");
        m_tx = m_networkTable.getEntry("tx");
        m_ty = m_networkTable.getEntry("ty");
        m_tv = m_networkTable.getEntry("tv");
    }

    @Override
    public void periodic() {
        m_horizontalRotation = m_tx.getDouble(0.0);
        m_verticalRotation = m_ty.getDouble(0.0);
        m_hasTarget = m_tv.getBoolean(false);
    }

    public double getRotationAngle() {
        return m_horizontalRotation;
    }

    public double getDistanceToTarget() {
        // input of m_verticalRotation, output horizontal distance to target
        return HEIGHT_TO_TARGET / (Math.tan(m_verticalRotation + Constants.CAMERA_PITCH_RADIANS));
    }

    public boolean hasTarget() {
        return m_hasTarget;
    }
}
