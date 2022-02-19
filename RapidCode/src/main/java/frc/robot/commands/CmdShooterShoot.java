package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class CmdShooterShoot extends CommandBase {
    Shooter m_shooterSubsystem;
    // Should not need to use a DoubleSupplier; just a double is good
    // Also the turret control will probably be a separate command, but we want speed to be variable, so I added that to parameters
    double m_hoodAngle;
    double m_speed;

    public CmdShooterShoot(Shooter shooterSubsystem, double hoodAngle, double speed) {
        m_shooterSubsystem = shooterSubsystem;
        m_hoodAngle = hoodAngle;
        m_speed = speed;
        // Don't need to require subsystem; only do that if it is the default command
    }

    @Override
    public void initialize() {
        //only set the hood setpoint once, so in init, not execute
        m_shooterSubsystem.setHoodSetPoint(m_hoodAngle);
    }

    @Override
    public void execute() {
        // make flywheels go and move hood to position.
        // check if flywheels are at full speed and if hood is in position
        // start a shoot procedure
        if (m_shooterSubsystem.atHoodSetpoint() && (m_shooterSubsystem.getFlywheelRPM() >= m_shooterSubsystem.getFlywheelSpeedSetpoint())) {
            //shoot :)
        } else {
            m_shooterSubsystem.hoodPIDExecute();
            m_shooterSubsystem.setTopFlywheelSpeed(m_speed);
            m_shooterSubsystem.setBottomFlywheelSpeed(m_speed);
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        // Should set flywheel speed to 0 when the command ends
        m_shooterSubsystem.setTopFlywheelSpeed(0);
        m_shooterSubsystem.setBottomFlywheelSpeed(0);
    }

    @Override
    public boolean isFinished() {
        // change after merge to finish when ball count is 0
        return false;
    }
}