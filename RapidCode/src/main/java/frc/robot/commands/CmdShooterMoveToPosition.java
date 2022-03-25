package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Shooter.HoodPosition;

public class CmdShooterMoveToPosition extends CommandBase {
    private Shooter m_shooterSubsystem;
    private double m_velocity;
    private HoodPosition m_hoodPosition;
    private int m_distanceInInches;
    private double m_timer;

    public CmdShooterMoveToPosition(Shooter shooterSubsystem, ShooterLookupTable lookupTable, int distanceInInches, double timer) {
        m_shooterSubsystem = shooterSubsystem;
        
        m_velocity = lookupTable.getVelocityAtDistance(distanceInInches);
        m_hoodPosition = lookupTable.getHoodPositionAtDistance(distanceInInches);
        m_distanceInInches = distanceInInches;
        m_timer = (int) timer * 50;
    }

    @Override
    public void initialize() {
        m_shooterSubsystem.setCurrentShootingDistance(m_distanceInInches);
        m_shooterSubsystem.setHoodPosition(m_hoodPosition);
    }

    @Override
    public void execute() {
        m_timer--;
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted && m_velocity != 0) m_shooterSubsystem.setReadyToShoot(true);
    }

    @Override
    public boolean isFinished() {
        return m_timer <= 0;
    }
}
