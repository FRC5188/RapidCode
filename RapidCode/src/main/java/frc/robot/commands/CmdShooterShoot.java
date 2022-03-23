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
    private int m_timer;
    private boolean m_useTimer;

    public CmdShooterShoot(Shooter shooterSubsystem, BallPath ballPathSubsystem, ShooterLookupTable lookupTable, double timeBetweenShots) {
        m_shooterSubsystem = shooterSubsystem;
        m_ballPathSubsystem = ballPathSubsystem;
        m_lookupTable = lookupTable;

        m_velocity = lookupTable.getVelocityAtDistance(shooterSubsystem.getCurrentShootingDistance());

        m_timer = (int) (timeBetweenShots * 50);
        m_useTimer = false;

        addRequirements(ballPathSubsystem);
    }

    @Override
    public void initialize() {
        m_ballPathSubsystem.setBallPathState(BallPathState.Shooting);
    }

    @Override
    public void execute() {
        if (m_ballPathSubsystem.hasLeftBallPath() && m_ballPathSubsystem.getBallCount() > 1) {
            m_useTimer = true;
        }

        if (m_useTimer) m_timer--;
        else {
            m_ballPathSubsystem.setMotorSpeed(Constants.BALL_PATH_SHOOTING_SPEED);
            m_shooterSubsystem.setAcceleratorSpeed(0.4);
            m_shooterSubsystem.setTopFlywheelSpeed(m_velocity);
            m_shooterSubsystem.setBottomFlywheelSpeed(m_velocity);
        }

        if (m_timer <= 0) m_useTimer = false;
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
        return m_ballPathSubsystem.getBallCount() == 0;
    }

}