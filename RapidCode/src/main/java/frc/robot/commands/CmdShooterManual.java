package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class CmdShooterManual extends CommandBase {
    private Shooter m_shooterSubsystem;
    private double m_turretSpeed;
    private double m_hoodSpeed;
    private double m_flywheelSpeed;

    public CmdShooterManual(Shooter shooterSubsystem, DoubleSupplier turretSpeed, DoubleSupplier hoodSpeed, double flywheelSpeed) {
        m_shooterSubsystem = shooterSubsystem;
        m_turretSpeed = turretSpeed.getAsDouble();
        m_hoodSpeed = hoodSpeed.getAsDouble();
        m_flywheelSpeed = flywheelSpeed;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_shooterSubsystem.setTurretSpeed(m_turretSpeed * 0.5);
        m_shooterSubsystem.setHoodSpeed(m_hoodSpeed * 0.25);
        m_shooterSubsystem.setBottomFlywheelSpeed(m_flywheelSpeed);
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
