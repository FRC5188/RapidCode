package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Shooter.HoodPosition;

public class CmdShooterMoveToPosition extends CommandBase {
    private Shooter m_shooterSubsystem;
    private double m_velocity;
    private int m_count;
    private HoodPosition m_hoodPosition;

    public CmdShooterMoveToPosition(Shooter shooterSubsystem, ShooterLookupTable lookupTable, int distanceInInches) {
        m_shooterSubsystem = shooterSubsystem;

        m_velocity = lookupTable.getVelocityAtDistance(distanceInInches);
        m_hoodPosition = lookupTable.getHoodPositionAtDistance(distanceInInches);
        m_count = 0;
    }

    @Override
    public void initialize() {
        m_shooterSubsystem.setHoodPosition(m_hoodPosition);
    }

    @Override
    public void execute() {
        m_shooterSubsystem.setTopFlywheelSpeed(m_velocity);
        m_shooterSubsystem.setBottomFlywheelSpeed(m_velocity);
        m_count++;
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted && m_velocity != 0) m_shooterSubsystem.setReadyToShoot(true);
        m_count = 0;
    }

    @Override
    public boolean isFinished() {
        return m_count >= 50;
    }
}
