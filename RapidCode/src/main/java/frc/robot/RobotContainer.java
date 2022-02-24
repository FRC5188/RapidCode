package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CmdDriveJoystick;
import frc.robot.commands.CmdDriveSetShifter;
import frc.robot.commands.CmdBallPathDefault;
import frc.robot.commands.CmdShooterShoot;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Drive.ShifterState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;

public class RobotContainer {
    private BallPath m_ballPathSubsystem = new BallPath();
    private Drive m_driveSubsystem = new Drive();
    private Shooter m_shooterSubsystem = new Shooter();
    private Vision m_visionSubsystem = new Vision();
    private ShooterLookupTable m_shooterLookupTable = new ShooterLookupTable();

    private XboxController m_driveController = new XboxController(0);

    private JoystickButton m_driveAButton = new JoystickButton(m_driveController, Constants.ButtonMappings.A_BUTTON);
    
    private XboxController m_operatorController = new XboxController(1);
    private double hoodAngle = 0;
    private double shooterSpeed = 0;
    private JoystickButton m_operatorAButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.A_BUTTON);

    private void configureButtonBindings() {
        m_driveAButton.whenPressed(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Shifted));
        m_driveAButton.whenReleased(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Normal));

        m_ballPathSubsystem.setDefaultCommand(new CmdBallPathDefault(m_ballPathSubsystem));
        m_driveSubsystem.setDefaultCommand(new CmdDriveJoystick(m_driveSubsystem, 
                                                                () -> applyDeadband(0.6 * -m_driveController.getLeftY(), Constants.ARCADE_DRIVE_DEADBAND), 
                                                                () -> applyDeadband( 0.65 * -m_driveController.getRightX(), Constants.ARCADE_DRIVE_DEADBAND)));
        // Adjusts hood angle and flywheel speed on D-Pad presses
        switch(m_operatorController.getPOV()){ 
            case 0:
                shooterSpeed += 0.01;
                break;
            case 90:
                hoodAngle += 3;
                //new CmdShooterShoot(m_shooterSubsystem, m_ballPathSubsystem, hoodAngle, 0);
                break;
            case 180:
                shooterSpeed -= 0.01;
                break;
            case 270:
                hoodAngle -= 3;
                //new CmdShooterShoot(m_shooterSubsystem, m_ballPathSubsystem, hoodAngle, 0);
                break; 
        }
           
        // Change speed and hood angle after testing
        m_operatorAButton.whenPressed(new CmdShooterShoot(m_shooterSubsystem, m_ballPathSubsystem, hoodAngle, shooterSpeed));
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
