package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase {
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
        return 0;
    }

    public double getDistanceToTarget() {
        return 0;
    }

    public boolean hasTarget() {
        return false;
    }
}
