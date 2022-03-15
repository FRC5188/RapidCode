package frc.robot;

import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CmdDriveJoystick;
import frc.robot.commands.CmdDriveSetShifter;
import frc.robot.commands.CmdPickupDefault;
import frc.robot.commands.CmdPickupDeploy;
import frc.robot.commands.CmdPickupStow;
import frc.robot.commands.CmdShooterManual;
import frc.robot.commands.CmdShooterMoveToPosition;
import frc.robot.commands.CmdBallPathChangeBallCount;
import frc.robot.commands.CmdBallPathDefault;
import frc.robot.commands.CmdBallPathManual;
import frc.robot.commands.CmdClimberMove;
import frc.robot.commands.CmdClimberSetCanMove;
import frc.robot.commands.CmdShooterShoot;
import frc.robot.commands.CmdTestUpdateSpeed;
import frc.robot.commands.GrpAutoClosestToHubPickupShoot;
import frc.robot.commands.GrpDriveForward;
import frc.robot.commands.GrpAutoFurthestFromHubPickupShoot;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Drive.ShifterState;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;

public class RobotContainer {
    private Dashboard m_dashboard = new Dashboard();

    private BallPath m_ballPathSubsystem = new BallPath(m_dashboard);
    private Drive m_driveSubsystem = new Drive(m_dashboard);
    
    private Vision m_visionSubsystem = new Vision(m_dashboard);
    private Shooter m_shooterSubsystem = new Shooter(m_dashboard, m_visionSubsystem);
    private ShooterLookupTable m_shooterLookupTable = new ShooterLookupTable();
    private Pickup m_pickupSubsystem = new Pickup(m_dashboard);
    private Climber m_climberSubsystem = new Climber();

    private XboxController m_driveController = new XboxController(0);

    private JoystickButton m_driveAButton = new JoystickButton(m_driveController, Constants.ButtonMappings.A_BUTTON);
    private JoystickButton m_driveYButton = new JoystickButton(m_driveController, Constants.ButtonMappings.Y_BUTTON);
    private JoystickButton m_driveXButton = new JoystickButton(m_driveController, Constants.ButtonMappings.X_BUTTON);
    private JoystickButton m_driveBButton = new JoystickButton(m_driveController, Constants.ButtonMappings.B_BUTTON);
    private JoystickButton m_driveRBButton = new JoystickButton(m_driveController, Constants.ButtonMappings.RIGHT_BUMPER);
    private JoystickButton m_driveLBButton = new JoystickButton(m_driveController, Constants.ButtonMappings.LEFT_BUMPER);
    
    private XboxController m_operatorController = new XboxController(1);

    private JoystickButton m_operatorRBButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.RIGHT_BUMPER); 
    private JoystickButton m_operatorLBButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.LEFT_BUMPER);
    private JoystickButton m_operatorAButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.A_BUTTON);
    private JoystickButton m_operatorBButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.B_BUTTON);
    private JoystickButton m_operatorYButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.Y_BUTTON);
    private JoystickButton m_operatorXButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.X_BUTTON);

    public RobotContainer() {
        m_dashboard.setDefaultAuto("Drive Forward", new GrpDriveForward(m_driveSubsystem, m_pickupSubsystem));
        m_dashboard.addAuto("2 Ball: Closest To Hub", new GrpAutoClosestToHubPickupShoot(m_driveSubsystem, m_ballPathSubsystem, m_pickupSubsystem, m_shooterSubsystem, m_visionSubsystem, m_shooterLookupTable));
        m_dashboard.addAuto("2 Ball: Farthest From Hub", new GrpAutoFurthestFromHubPickupShoot(m_driveSubsystem, m_ballPathSubsystem, m_pickupSubsystem, m_shooterSubsystem, m_visionSubsystem, m_shooterLookupTable));

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        CmdShooterShoot shootingCommand = new CmdShooterShoot(m_shooterSubsystem, m_ballPathSubsystem);

        m_driveRBButton.whenPressed(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Shifted));
        m_driveRBButton.whenReleased(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Normal));

        m_driveLBButton.whenPressed(new CmdShooterMoveToPosition(m_shooterSubsystem, m_shooterLookupTable, 120));

        m_driveAButton.whenPressed(new CmdTestUpdateSpeed(m_shooterSubsystem, -0.05));
        m_driveYButton.whenPressed(new CmdTestUpdateSpeed(m_shooterSubsystem, 0.05));
        m_driveXButton.whenPressed(shootingCommand);
        m_driveBButton.cancelWhenPressed(shootingCommand);

        m_ballPathSubsystem.setDefaultCommand(new CmdBallPathDefault(m_ballPathSubsystem, m_pickupSubsystem));
        // m_climberSubsystem.setDefaultCommand(new CmdClimberMove(m_climberSubsystem, () -> applyDeadband(m_operatorController.getLeftY(), 0.025)));

        // m_driveSubsystem.setDefaultCommand(new CmdDriveJoystick(m_driveSubsystem, 
        //                                                         () -> applyDeadband(0.6 * -m_driveController.getLeftY(), Constants.ARCADE_DRIVE_DEADBAND), 
        //                                                         () -> applyDeadband(0.65 * -m_driveController.getRightX(), Constants.ARCADE_DRIVE_DEADBAND)));

        m_pickupSubsystem.setDefaultCommand(new CmdPickupDefault(m_pickupSubsystem, m_ballPathSubsystem));

        m_shooterSubsystem.setDefaultCommand(new CmdShooterManual(m_shooterSubsystem, 
                                                                  () -> applyDeadband(m_driveController.getRightX(), 0.025), 
                                                                  () -> applyDeadband(-m_driveController.getLeftY(), 0.025)));

       
        m_operatorBButton.whenPressed(new CmdClimberSetCanMove(m_climberSubsystem, true));
        m_operatorBButton.whenReleased(new CmdClimberSetCanMove(m_climberSubsystem, false));

        //m_operatorAButton.whenPressed(new CmdShooterShoot(m_shooterSubsystem, m_ballPathSubsystem, hoodAngle, shooterSpeed));
        m_operatorRBButton.whenPressed(new CmdBallPathChangeBallCount(m_ballPathSubsystem, true)); // When RB Button Pressed increments ball count.
        m_operatorLBButton.whenPressed(new CmdBallPathChangeBallCount(m_ballPathSubsystem, false)); // When LB Button Pressed decrements ball count

        m_operatorAButton.whenPressed(new CmdPickupDeploy(m_pickupSubsystem, m_ballPathSubsystem));
        m_operatorYButton.whenPressed(new CmdPickupStow(m_pickupSubsystem));
    }

    public Command getAutonomousCommand() {
        return new GrpDriveForward(m_driveSubsystem, m_pickupSubsystem);
        //return m_dashboard.getSelectedAutonomousCommand();
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
