package frc.robot.subsystems;

import java.util.Map;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Dashboard extends SubsystemBase {
    private SendableChooser<Command> m_autonomousChooser;

    private NetworkTableEntry m_isDriveShiftedEntry;
    private boolean m_isDriveShifted;
    
    public Dashboard() {
        ShuffleboardTab dashboard       = Shuffleboard.getTab("Dashboard");

        m_autonomousChooser             = new SendableChooser<Command>();

        ShuffleboardLayout  drivebase   = dashboard.getLayout("Drivebase Subsystem", BuiltInLayouts.kList)
        .withPosition(0, 0)
        .withSize(12, 24)
        .withProperties(Map.of("Label position", "BOTTOM"));

        drivebase.add("Gyro", new AHRS());
        drivebase.add("Shifter is Active", m_isDriveShifted).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("Color when true", "Red", "Color when false", "Lime")).getEntry();

        // m_upperTrackState               = UpperTrackState.Raised;
        // m_upperTrackStateEntry          = ballPath.add("Upper Track State", m_upperTrackState.toString()).withWidget(BuiltInWidgets.kTextView).getEntry();
    }

    @Override
    public void periodic() {
    }
}
