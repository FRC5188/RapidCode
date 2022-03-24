package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CmdDriveJoystick;
import frc.robot.commands.CmdDriveSetShifter;
import frc.robot.commands.CmdManualHoodAdjustment;
import frc.robot.commands.CmdManualShooterSpeedCtrl;
import frc.robot.commands.CmdPickupDefault;
import frc.robot.commands.CmdPickupDeploy;
import frc.robot.commands.CmdPickupStow;
import frc.robot.commands.CmdBallPathChangeBallCount;
import frc.robot.commands.CmdBallPathDefault;
import frc.robot.commands.CmdClimberMove;
import frc.robot.commands.CmdClimberSetCanMove;
import frc.robot.commands.CmdShooterStopShooting;
import frc.robot.commands.GrpDriveForward;
import frc.robot.commands.GrpShootWithoutVision;
import frc.robot.commands.GrpAutoFenderAndTaxi;
import frc.robot.commands.GrpAutoTwoBallShoot;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Drive.ShifterState;
import frc.robot.subsystems.Shooter.HoodPosition;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;

public class RobotContainer {
    private Dashboard m_dashboard = new Dashboard();

    private BallPath m_ballPathSubsystem = new BallPath(m_dashboard);
    private Drive m_driveSubsystem = new Drive(m_dashboard);
    
    private Vision m_visionSubsystem = new Vision(m_dashboard);
    private Shooter m_shooterSubsystem = new Shooter(m_dashboard);
    private ShooterLookupTable m_shooterLookupTable = new ShooterLookupTable();
    private Pickup m_pickupSubsystem = new Pickup(m_dashboard);
    private Climber m_climberSubsystem = new Climber();

    private XboxController m_driveController = new XboxController(0);

    private JoystickButton m_driveAButton = new JoystickButton(m_driveController, Constants.ButtonMappings.A_BUTTON);
    private JoystickButton m_driveYButton = new JoystickButton(m_driveController, Constants.ButtonMappings.Y_BUTTON);
    private JoystickButton m_driveRBButton = new JoystickButton(m_driveController, Constants.ButtonMappings.RIGHT_BUMPER);
    private JoystickButton m_driveLBButton = new JoystickButton(m_driveController, Constants.ButtonMappings.LEFT_BUMPER);

    private XboxController m_operatorController = new XboxController(1);

    private JoystickButton m_operatorRBButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.RIGHT_BUMPER); 
    private JoystickButton m_operatorAButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.A_BUTTON);
    private JoystickButton m_operatorBButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.B_BUTTON);
    private JoystickButton m_operatorXButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.X_BUTTON);
    private JoystickButton m_operatorYButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.Y_BUTTON);
    private JoystickButton m_operatorL3Button = new JoystickButton(m_operatorController, Constants.ButtonMappings.LEFT_JOY_BUTTON);
    private JoystickButton m_operatorR3Button = new JoystickButton(m_operatorController, Constants.ButtonMappings.RIGHT_JOY_BUTTON);



    public RobotContainer() {
        m_dashboard.setDefaultAuto("Drive Forward", new GrpDriveForward(m_driveSubsystem, m_pickupSubsystem));
        //m_dashboard.addAuto("2 Ball: Closest To Hub", new GrpAutoClosestToHubPickupShoot(m_driveSubsystem, m_ballPathSubsystem, m_pickupSubsystem, m_shooterSubsystem, m_visionSubsystem, m_shooterLookupTable));
        m_dashboard.addAuto("1 Ball: On Fender", new GrpAutoFenderAndTaxi(m_driveSubsystem, m_ballPathSubsystem, m_pickupSubsystem, m_shooterSubsystem, m_visionSubsystem, m_shooterLookupTable, 2));
        m_dashboard.addAuto("2 Ball: On Fender", new GrpAutoTwoBallShoot(m_driveSubsystem, m_pickupSubsystem, m_shooterSubsystem, m_shooterLookupTable, m_visionSubsystem, m_ballPathSubsystem, 2));
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        GrpShootWithoutVision closeShot = new GrpShootWithoutVision(m_shooterSubsystem, m_ballPathSubsystem, m_shooterLookupTable, Constants.FRONT_OF_FENDER_DISTANCE, 0);

        m_ballPathSubsystem.setDefaultCommand(new CmdBallPathDefault(m_ballPathSubsystem));
        m_climberSubsystem.setDefaultCommand(new CmdClimberMove(m_climberSubsystem, () -> applyDeadband(m_operatorController.getLeftY(), 0.025), () -> applyDeadband(m_operatorController.getRightY(), 0.025)));

        m_driveSubsystem.setDefaultCommand(new CmdDriveJoystick(m_driveSubsystem, 
                                                                () -> applyDeadband(0.65 * -m_driveController.getLeftY(), Constants.ARCADE_DRIVE_DEADBAND), 
                                                                () -> applyDeadband(0.7 * -m_driveController.getRightX(), Constants.ARCADE_DRIVE_DEADBAND)));

        m_pickupSubsystem.setDefaultCommand(new CmdPickupDefault(m_pickupSubsystem, m_ballPathSubsystem));

        // Driver Controls
       
        // intake stow and deploy
        m_driveAButton.whenPressed(new CmdPickupDeploy(m_pickupSubsystem, m_ballPathSubsystem));
        m_driveYButton.whenPressed(new CmdPickupStow(m_pickupSubsystem));
        
        //shifting
        m_driveRBButton.whenPressed(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Shifted));
        m_driveRBButton.whenReleased(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Normal));

        //shooting
        m_driveRBButton.whenPressed(closeShot);


        // Operator Controls

        // ball count adjust
        m_operatorBButton.whenPressed(new CmdBallPathChangeBallCount(m_ballPathSubsystem, true));
        m_operatorXButton.whenPressed(new CmdBallPathChangeBallCount(m_ballPathSubsystem, false));
       
       // what this do
        m_operatorAButton.whenReleased(new CmdShooterStopShooting(m_shooterSubsystem, m_ballPathSubsystem, closeShot));
      
        // can climber move?
        m_operatorRBButton.whenPressed(new CmdClimberSetCanMove(m_climberSubsystem, true));
        m_operatorRBButton.whenReleased(new CmdClimberSetCanMove(m_climberSubsystem, false));
       
       // hood adjust
        m_operatorYButton.whenPressed(new CmdManualHoodAdjustment(m_shooterSubsystem, HoodPosition.Far));
       m_operatorYButton.whenReleased(new CmdManualHoodAdjustment(m_shooterSubsystem, HoodPosition.Fender));
      
       // manual adjust shooter speed
       // before using zoes code. this command doesnt really work. day before comp
    //    m_operatorL3Button.whenReleased(new CmdManualShooterSpeedCtrl(m_shooterSubsystem, 0.025)); 
    //    m_operatorR3Button.whenReleased(new CmdManualShooterSpeedCtrl(m_shooterSubsystem, -0.025));

    }

    public Command getAutonomousCommand() {
        return m_dashboard.getSelectedAutonomousCommand();
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
