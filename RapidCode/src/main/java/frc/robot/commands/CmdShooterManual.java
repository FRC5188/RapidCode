package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class CmdShooterManual extends CommandBase {
    private Shooter m_shooterSubsystem;
    private DoubleSupplier m_turretSpeed;
    private DoubleSupplier m_hoodSpeed;
    private double m_flywheelSpeed;

    public CmdShooterManual(Shooter shooterSubsystem, DoubleSupplier turretSpeed, DoubleSupplier hoodSpeed) {
        m_shooterSubsystem = shooterSubsystem;
        m_turretSpeed = turretSpeed;
        m_hoodSpeed = hoodSpeed;

        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooterSubsystem.setTurretSpeed(m_turretSpeed.getAsDouble() * 0.5);
        m_shooterSubsystem.setHoodSpeed(m_hoodSpeed.getAsDouble() * 0.125);
        m_shooterSubsystem.setBottomFlywheelSpeed(m_shooterSubsystem.getShooterSpeed());
    }

    @Override
    public void end(boolean interrupted) {
        m_shooterSubsystem.setTurretSpeed(0);
        m_shooterSubsystem.setHoodSpeed(0);
        m_shooterSubsystem.setBottomFlywheelSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
