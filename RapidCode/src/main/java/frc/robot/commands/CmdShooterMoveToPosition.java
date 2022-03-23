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

    public CmdShooterMoveToPosition(Shooter shooterSubsystem, ShooterLookupTable lookupTable, int distanceInInches) {
        m_shooterSubsystem = shooterSubsystem;
        
        m_velocity = lookupTable.getVelocityAtDistance(distanceInInches);
        m_hoodPosition = lookupTable.getHoodPositionAtDistance(distanceInInches);
        m_distanceInInches = distanceInInches;
    }

    @Override
    public void initialize() {
        m_shooterSubsystem.setCurrentShootingDistance(m_distanceInInches);
        m_shooterSubsystem.setHoodPosition(m_hoodPosition);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted && m_velocity != 0) m_shooterSubsystem.setReadyToShoot(true);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
