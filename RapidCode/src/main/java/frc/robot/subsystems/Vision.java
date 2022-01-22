package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase {
    PhotonCamera m_visionCamera;
    PhotonTrackedTarget m_bestTarget;

    public Vision() {
        m_visionCamera = new PhotonCamera(NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("Microsoft_LifeCam_HD-3000"));
        m_visionCamera.setDriverMode(false);

        m_bestTarget = m_visionCamera.getLatestResult().getBestTarget();
    }

    @Override
    public void periodic() {
        m_bestTarget = m_visionCamera.getLatestResult().getBestTarget();
    }

    public double getRotationAngle() {
        return m_bestTarget.getYaw();
    }

    public double getDistanceToTarget() {
        return PhotonUtils.calculateDistanceToTargetMeters(Constants.CAMERA_HEIGHT_METERS, Constants.TARGET_HEIGHT_METERS, Constants.CAMERA_PITCH_RADIANS, 0);
    }
}
