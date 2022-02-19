package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.BallPath.BallPathState;

public class CmdBallPathDefault extends CommandBase {
    private BallPath m_ballPathSubsystem;

    private BallPathState m_ballPathState;

    public CmdBallPathDefault(BallPath ballPathSubsystem) {
        m_ballPathSubsystem = ballPathSubsystem;

        addRequirements(m_ballPathSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_ballPathSubsystem.updateBallPathState();
        m_ballPathState = m_ballPathSubsystem.getBallPathState();

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
        return false;
    }
}
