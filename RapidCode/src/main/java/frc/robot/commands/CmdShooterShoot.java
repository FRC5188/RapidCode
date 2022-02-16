package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class CmdShooterShoot extends CommandBase {
    Shooter m_shooterSubsystem;

    DoubleSupplier m_hoodAngle;
    DoubleSupplier m_turretAngle;

    public CmdShooterShoot(Shooter shooterSubsystem, DoubleSupplier hoodAngle, DoubleSupplier turretAngle) {
        m_shooterSubsystem = shooterSubsystem;
        m_hoodAngle = hoodAngle;
        m_turretAngle = turretAngle;

        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // make flywheels go and move hood to position.
        // check if flywheels are at full speed and if hood is in position
        // start a shoot procedure
        if (m_shooterSubsystem.atHoodSetpoint() && (m_shooterSubsystem.getFlywheelRPM() >= m_shooterSubsystem.getFlywheelSpeedSetpoint())) {
            //shoot :)
        } else {
            m_shooterSubsystem.setHoodSetPoint(m_hoodAngle.getAsDouble());
            m_shooterSubsystem.hoodPIDExecute();
            m_shooterSubsystem.setTopFlywheelSpeed(1);
            m_shooterSubsystem.setBottomFlywheelSpeed(1);
        }
        
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}