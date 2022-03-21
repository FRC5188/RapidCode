package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
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
    private int m_timer;
    private boolean m_useTimer;

    public CmdShooterShoot(Shooter shooterSubsystem, BallPath ballPathSubsystem, ShooterLookupTable lookupTable, int distanceInInches, double timer) {
        m_shooterSubsystem = shooterSubsystem;
        m_ballPathSubsystem = ballPathSubsystem;
        m_lookupTable = lookupTable;

        // m_velocity = lookupTable.getVelocityAtDistance(distanceInInches);
        // m_angle = lookupTable.getAngleAtDistance(distanceInInches);

        m_timer = (int) (timer * 50);
        m_useTimer = false;

        addRequirements(ballPathSubsystem);
    }

    @Override
    public void initialize() {
        m_ballPathSubsystem.setBallPathState(BallPathState.Shooting);

        if (m_timer > 0) {
            m_useTimer = true;
        }
    }

    @Override
    public void execute() {
        m_timer--;
        m_ballPathSubsystem.setMotorSpeed(Constants.BALL_PATH_SHOOTING_SPEED);
        m_shooterSubsystem.setAcceleratorSpeed(0.4);
        m_shooterSubsystem.setTopFlywheelSpeed(m_velocity);
        m_shooterSubsystem.setBottomFlywheelSpeed(m_velocity);
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
        return (m_useTimer) && (m_timer <= 0);
    }

}