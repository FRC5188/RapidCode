package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.BallPath.BallPathState;

public class CmdBallPathAutonomousUse extends CommandBase {
    //I don;t want to use this but i a=hvae to
    private BallPath m_ballPathSubsystem;
    private BallPathState m_ballPathState;

    public CmdBallPathAutonomousUse(BallPath ballPathSubsystem) {
        m_ballPathSubsystem = ballPathSubsystem;
        m_ballPathState = BallPathState.None;

        addRequirements(m_ballPathSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_ballPathSubsystem.updateBallPathState();
        m_ballPathState = m_ballPathSubsystem.getBallPathState();
        // System.out.println("ball path state: " + m_ballPathState);

        switch (m_ballPathState) {
            case Loading:
                // Need motors to be running in this state
                m_ballPathSubsystem.setMotorSpeed(Constants.BALL_PATH_LOADING_SPEED);
                break;
            case MovingToPosition:
                // Motors running
                m_ballPathSubsystem.setMotorSpeed(Constants.BALL_PATH_POSITION_SPEED);
                break;
            case Shooting:
                // Need motors to be running in this state
                m_ballPathSubsystem.setMotorSpeed(Constants.BALL_PATH_SHOOTING_SPEED);
                break;
            case Stopped:
                // Need motors to not be running in this state
                m_ballPathSubsystem.setMotorSpeed(0);
                break;
            case None:
                // Need motors to not be running in this state
                m_ballPathSubsystem.setMotorSpeed(0);
            default:
                break;
        }

    }

    @Override
    public void end(boolean interrupted) {
        m_ballPathSubsystem.setMotorSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return m_ballPathSubsystem.getBallCount() == 2 && m_ballPathState == BallPathState.Stopped;
    }
}