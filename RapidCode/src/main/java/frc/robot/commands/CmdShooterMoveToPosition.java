package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;

public class CmdShooterMoveToPosition extends CommandBase {
    private Shooter m_shooterSubsystem;
    private double m_velocity;
    private double m_hoodAngle;
    private double m_turretAngle;

    public CmdShooterMoveToPosition(Shooter shooterSubsystem, Vision visionSubsystem, ShooterLookupTable lookupTable, int distanceInInches) {
        m_shooterSubsystem = shooterSubsystem;
        
        m_velocity = lookupTable.getVelocityAtDistance(distanceInInches);
        m_hoodAngle = lookupTable.getAngleAtDistance(distanceInInches);
    }

    @Override
    public void initialize() {
        m_shooterSubsystem.setHoodSetPoint(m_hoodAngle);
        m_shooterSubsystem.setTopFlywheelSpeed(m_velocity);
        m_shooterSubsystem.setBottomFlywheelSpeed(m_velocity);
    }

    @Override
    public void execute() {
        m_shooterSubsystem.hoodPIDExecute();
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
