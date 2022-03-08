package frc.robot;

import com.revrobotics.EncoderType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CmdDriveJoystick;
import frc.robot.commands.CmdDriveSetShifter;
import frc.robot.commands.CmdPickupDeploy;
import frc.robot.commands.CmdPickupStow;
import frc.robot.commands.CmdBallPathDefault;
import frc.robot.commands.CmdBallPathManual;
import frc.robot.commands.CmdShooterShoot;
import frc.robot.commands.GrpAutoExample;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
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
    private Pickup m_pickupSubsystem = new Pickup();

    private XboxController m_driveController = new XboxController(0);

    private JoystickButton m_driveAButton = new JoystickButton(m_driveController, Constants.ButtonMappings.A_BUTTON);
    private JoystickButton m_driveRBButton = new JoystickButton(m_driveController, Constants.ButtonMappings.RIGHT_BUMPER);
    
    private XboxController m_operatorController = new XboxController(1);
    /* 
    Declares the RB Joystick Bumber For The Operator's Controller
     */
    private JoystickButton m_operatorRBButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.RIGHT_BUMPER); 
    /*
    Declares the LB Joystick Bumber For The Operator's Controller
    */
    private JoystickButton m_operatorLBButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.LEFT_BUMPER);
    private double hoodAngle = 0;
    private double shooterSpeed = 0;
    private JoystickButton m_operatorAButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.A_BUTTON);
    private JoystickButton m_operatorYButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.Y_BUTTON);
    private JoystickButton m_operatorXButton = new JoystickButton(m_operatorController, Constants.ButtonMappings.X_BUTTON);


    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        m_driveRBButton.whenPressed(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Shifted));
        m_driveRBButton.whenReleased(new CmdDriveSetShifter(m_driveSubsystem, ShifterState.Normal));

        //command is broken; defaults to turning on both commands in the indexer
        //m_ballPathSubsystem.setDefaultCommand(new CmdBallPathDefault(m_ballPathSubsystem));
        // if there is a default command auto doesnt work
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
        m_operatorXButton.whenPressed(new CmdBallPathManual(m_ballPathSubsystem, 1));
        m_operatorXButton.whenReleased(new CmdBallPathManual(m_ballPathSubsystem, 0));
        m_operatorYButton.whenPressed(new CmdBallPathManual(m_ballPathSubsystem, -1));
        m_operatorYButton.whenReleased(new CmdBallPathManual(m_ballPathSubsystem, 0));

        m_operatorAButton.whenPressed(new CmdShooterShoot(m_shooterSubsystem, m_ballPathSubsystem, hoodAngle, shooterSpeed));
        m_operatorRBButton.whenPressed(new CmdPickupDeploy(m_pickupSubsystem)); // When RB Button Pressed Activates The Depoly Cmd.
        m_operatorLBButton.whenPressed(new CmdPickupStow(m_pickupSubsystem)); // When LB Button Pressed Activates The Stow Cmd.
    }

    public Command getAutonomousCommand() {
        return new GrpAutoExample(m_driveSubsystem, m_pickupSubsystem);
    }

    public void setAutoRampRate(double rampRate) {
        this.m_driveSubsystem.setAutoRampRate(rampRate);
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

    public void resetEncoders() {
        m_driveSubsystem.resetEncoders();
    }

    public double getEncoderPosition(){
        return this.m_driveSubsystem.getEncoderPosition();
    }
}
