package frc.robot.subsystems;

import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.BallPath.BallPathState;

public class Dashboard extends SubsystemBase {
    private SendableChooser<Command> m_autonomousChooser;

    private NetworkTableEntry m_ballCountEntry;
    private int m_ballCount;

    private NetworkTableEntry m_ballPathStateEntry;
    private NetworkTableEntry m_entranceSensorStateEntry;
    private NetworkTableEntry m_middleSensorStateEntry;
    private NetworkTableEntry m_shooterSensorStateEntry;
    private String m_ballPathState;
    private boolean m_entranceSensorState;
    private boolean m_middleSensorState;
    private boolean m_shooterSensorState;

    private NetworkTableEntry m_isDriveShiftedEntry;
    private boolean m_isDriveShifted;

    private NetworkTableEntry m_pickupIsDeployedEntry;
    private boolean m_pickupIsDeployed;

    private NetworkTableEntry m_hasTargetEntry;
    private NetworkTableEntry m_distanceToTargetEntry;
    private NetworkTableEntry m_readyToShootEntry;
    private boolean m_hasTarget;
    private int m_distanceToTarget;
    private boolean m_readyToShoot;
    
    
    public Dashboard() {
        ShuffleboardTab dashboard = Shuffleboard.getTab("Dashboard");

        m_autonomousChooser = new SendableChooser<Command>();

        dashboard.add("Autonomous Selector", m_autonomousChooser)
                 .withPosition(13, 0)
                 .withSize(12, 2)
                 .withWidget(BuiltInWidgets.kComboBoxChooser);

        m_ballCount = 1;
        m_ballCountEntry = dashboard.add("Ball Count", m_ballCount)
                                    .withPosition(25, 0)
                                    .withSize(8, 8)
                                    .withWidget(BuiltInWidgets.kDial)
                                    .withProperties(Map.of("Min", 0, "Max", 2, "Show value", true))
                                    .getEntry();
         
        ShuffleboardLayout ballPath = dashboard.getLayout("Ball Path Subsystem", BuiltInLayouts.kList)
            .withPosition(33, 0)
            .withSize(6, 9)
            .withProperties(Map.of("Label position", "BOTTOM"));
        ballPath.add("Ball Path State", m_ballPathState).withWidget(BuiltInWidgets.kTextView).getEntry();
        ballPath.add("Entrance Sensor", m_entranceSensorState).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        ballPath.add("Middle Sensor", m_middleSensorState).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        ballPath.add("Shooter Sensor", m_shooterSensorState).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();

        ShuffleboardLayout climber = dashboard.getLayout("Climber Subsystem", BuiltInLayouts.kList)
            .withPosition(33, 10)
            .withSize(6, 7)
            .withProperties(Map.of("Label position", "BOTTOM"));

        ShuffleboardLayout drivebase = dashboard.getLayout("Drivebase Subsystem", BuiltInLayouts.kList)
            .withPosition(0, 0)
            .withSize(6, 10)
            .withProperties(Map.of("Label position", "BOTTOM"));

        drivebase.add("Gyro", new AHRS());
        drivebase.add("Shifter is Active", m_isDriveShifted).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("Color when true", "Red", "Color when false", "Lime")).getEntry();

        ShuffleboardLayout pickup = dashboard.getLayout("Pickup Subsystem", BuiltInLayouts.kList)
            .withPosition(0, 11)
            .withSize(6, 4)
            .withProperties(Map.of("Label position", "BOTTOM"));

        pickup.add("Pickup is Deployed", m_pickupIsDeployed).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("Color when true", "Red", "Color when false", "Lime")).getEntry();

        ShuffleboardLayout shooter = dashboard.getLayout("Shooter Subsystem", BuiltInLayouts.kList)
            .withPosition(7, 0)
            .withSize(6, 12)
            .withProperties(Map.of("Label position", "BOTTOM"));
        shooter.add("Has Target", m_hasTarget).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("Color when true", "Red", "Color when false", "Lime")).getEntry();
        shooter.add("Distance To Target", m_distanceToTarget).withWidget(BuiltInWidgets.kTextView).getEntry();
        shooter.add("Ready To Shoot", m_readyToShoot).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("Color when true", "Red", "Color when false", "Lime")).getEntry();
    }

    @Override
    public void periodic() {
        m_ballCountEntry.setValue(m_ballCount);

        m_ballPathStateEntry.setString(m_ballPathState.toString());
        m_entranceSensorStateEntry.setBoolean(m_entranceSensorState);
        m_middleSensorStateEntry.setBoolean(m_middleSensorState);
        m_shooterSensorStateEntry.setBoolean(m_shooterSensorState);

        m_isDriveShiftedEntry.setBoolean(m_isDriveShifted);

        m_pickupIsDeployedEntry.setBoolean(m_pickupIsDeployed);

        m_hasTargetEntry.setBoolean(m_hasTarget);
        m_distanceToTargetEntry.setValue(m_distanceToTarget);
        m_readyToShootEntry.setBoolean(m_readyToShoot);
    }

    public void addAuto(String name, Command command) {
        m_autonomousChooser.addOption(name, command);
    }

    public void setCameraFeed(VideoSource cameraFeed) {
        Shuffleboard.getTab("Dashboard").add("Camera", cameraFeed).withPosition(13, 2).withSize(12, 10).withWidget(BuiltInWidgets.kCameraStream).withProperties(Map.of("Show crosshair", false, "Show controls", false));
    }

    public void setBallCount(int count) {
        m_ballCount = count;
    }

    public void setBallPathstate(BallPathState state) {
        m_ballPathState = state.toString();
    }

    public void setEntranceSensorState(boolean state) {
        m_entranceSensorState = state;
    }

    public void setMiddleSensorState(boolean state) {
        m_middleSensorState = state;
    }

    public void setShooterSensorState(boolean state) {
        m_shooterSensorState = state;
    }

    public void setIsDriveShifted(boolean isShifted) {
        m_isDriveShifted = isShifted;
    }

    public void setPickupIsDeployed(boolean isDeployed) {
        m_pickupIsDeployed = isDeployed;
    }

    public void setHasTarget(boolean hasTarget) {
        m_hasTarget = hasTarget;
    }

    public void setDistanceToTarget(int distance) {
        m_distanceToTarget = distance;
    }

    public void setReadyToShoot(boolean ready) {
        m_readyToShoot = ready;
    }
}
