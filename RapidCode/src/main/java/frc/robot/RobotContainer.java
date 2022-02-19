package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CmdDriveJoystick;
import frc.robot.commands.CmdDriveSetShifter;
import frc.robot.commands.CmdBallPathDefault;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Drive.ShifterState;
import frc.robot.commands.CmdShooterShoot;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
    BallPath m_ballPathSubsystem = new BallPath();
    Drive m_driveSubsystem = new Drive();
    Shooter m_shooterSubsystem = new Shooter();

    XboxController m_driveController = new XboxController(0);

    JoystickButton m_driveAButton = new JoystickButton(m_driveController, Constants.ButtonMappings.A_BUTTON);
    
    XboxController m_operatorController = new XboxController(1);

    JoystickButton m_operatorAButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.A_BUTTON);

    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        m_driveAButton.whenPressed(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Shifted));
        m_driveAButton.whenReleased(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Normal));

        m_ballPathSubsystem.setDefaultCommand(new CmdBallPathDefault(m_ballPathSubsystem));
        m_driveSubsystem.setDefaultCommand(new CmdDriveJoystick(m_driveSubsystem, 
                                                                () -> applyDeadband(0.6 * -m_driveController.getLeftY(), Constants.ARCADE_DRIVE_DEADBAND), 
                                                                () -> applyDeadband( 0.65 * -m_driveController.getRightX(), Constants.ARCADE_DRIVE_DEADBAND)));
        
        // Change speed and hood angle after testing
        m_operatorAButton.whenPressed(new CmdShooterShoot(m_shooterSubsystem, m_ballPathSubsystem, 0, 0));
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
