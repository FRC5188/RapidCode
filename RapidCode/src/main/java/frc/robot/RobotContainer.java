package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.CmdDriveJoystick;
import frc.robot.subsystems.Drive;

public class RobotContainer {
    Drive m_driveSubsystem = new Drive();

    XboxController m_driveController = new XboxController(0);
    
    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        m_driveSubsystem.setDefaultCommand(new CmdDriveJoystick(m_driveSubsystem, 
                                                                () -> applyDeadband(-m_driveController.getLeftY(), 0.025), 
                                                                () -> applyDeadband(-m_driveController.getRightX(), 0.025)));
    }

    public Command getAutonomousCommand() {
        return null;
    }

    private double applyDeadband(double raw, double deadband) {
        /* Please don't modify, but please do ask if you wanna know how it works! */
        
        double modified = 0.0;

        deadband = Math.abs(deadband);

        if (raw < -deadband)
            modified = ((raw + 1) / (1 - deadband)) - 1;
        else if (raw > deadband)
            modified = ((raw - 1) / (1 - deadband)) + 1;

        return modified;
    }
}
