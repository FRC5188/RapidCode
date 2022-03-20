package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;

public class CmdShooterMoveToPosition extends CommandBase {
    private Shooter m_shooterSubsystem;
    private double m_velocity;
    private double m_hoodAngle;
    private double m_turretAngle;
    private int m_count;

    public CmdShooterMoveToPosition(Shooter shooterSubsystem, ShooterLookupTable lookupTable, int distanceInInches) {
        m_shooterSubsystem = shooterSubsystem;
        
        m_velocity = lookupTable.getVelocityAtDistance(distanceInInches);
        m_hoodAngle = lookupTable.getAngleAtDistance(distanceInInches);
        m_count = 0;
    }

    @Override
    public void initialize() {
        m_shooterSubsystem.setHoodSetPoint(m_hoodAngle);
    }

    @Override
    public void execute() {
        m_shooterSubsystem.setTopFlywheelSpeed(m_velocity);
        m_shooterSubsystem.setBottomFlywheelSpeed(m_velocity);
        //m_shooterSubsystem.hoodPIDExecute();
        m_count++;
    }

    @Override
    public void end(boolean interrupted) {
        //m_shooterSubsystem.setHoodSpeed(0);
        if (!interrupted && m_velocity != 0) m_shooterSubsystem.setReadyToShoot(true);
        System.out.println("DONE MOVING");
        m_count = 0;
    }

    @Override
    public boolean isFinished() {
        // return m_shooterSubsystem.atHoodSetpoint(); //&& m_shooterSubsystem.flywheelsAtSpeed();//Add in after testing
        return m_count >= 50;
    }
}
