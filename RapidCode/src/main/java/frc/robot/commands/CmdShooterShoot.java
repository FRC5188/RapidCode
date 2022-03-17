package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.BallPath.BallPathState;

public class CmdShooterShoot extends CommandBase {
    Shooter m_shooterSubsystem;
    BallPath m_ballPathSubsystem;
    ShooterLookupTable m_lookupTable;
    private double m_velocity;
    private double m_angle;

    public CmdShooterShoot(Shooter shooterSubsystem, BallPath ballPathSubsystem, ShooterLookupTable lookupTable, int distanceInInches) {
        m_shooterSubsystem = shooterSubsystem;
        m_ballPathSubsystem = ballPathSubsystem;
        m_lookupTable = lookupTable;

        m_velocity = lookupTable.getVelocityAtDistance(distanceInInches);
        m_angle = lookupTable.getAngleAtDistance(distanceInInches);
    }

    @Override
    public void initialize() {
        m_ballPathSubsystem.setBallPathState(BallPathState.Shooting);
        m_shooterSubsystem.setHoodSetPoint(m_angle);
    }

    @Override
    public void execute() {
        m_shooterSubsystem.hoodPIDExecute();
        m_shooterSubsystem.setAcceleratorSpeed(0.4);
        m_shooterSubsystem.setTopFlywheelSpeed(m_velocity);
        m_shooterSubsystem.setBottomFlywheelSpeed(m_velocity);
        System.out.println(m_shooterSubsystem.getTopFlywheelSpeedSetpoint() / 6000);
    }

    @Override
    public void end(boolean interrupted) {
        m_shooterSubsystem.setTopFlywheelSpeed(0);
        m_shooterSubsystem.setBottomFlywheelSpeed(0);
        m_shooterSubsystem.setAcceleratorSpeed(0);
        m_ballPathSubsystem.setBallPathState(BallPathState.Stopped);
        m_ballPathSubsystem.resetBallCount();
        m_shooterSubsystem.setReadyToShoot(false);
    }

    @Override
    public boolean isFinished() {
        // change after merge to finish when ball count is 0
        return false;
    }

}