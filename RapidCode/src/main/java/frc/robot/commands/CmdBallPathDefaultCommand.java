package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.BallPath.BallPathState;

public class CmdBallPathDefaultCommand extends CommandBase {
    private BallPath m_ballPathSubsystem;

    private BallPathState m_ballPathState;

    public CmdBallPathDefaultCommand(BallPath ballPathSubsystem) {
        m_ballPathSubsystem = ballPathSubsystem;

        addRequirements(m_ballPathSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
       switch (m_ballPathState) {
        case Loading:
        //Need motors to be running in this state
        m_ballPathSubsystem.setMotorSpeed(0.5);
        break;
        case MovingToPosition:
        //Motors running
        m_ballPathSubsystem.setMotorSpeed(0.5);
        break;
        case Shooting:
        //Need motors to be running in this state
        m_ballPathSubsystem.setMotorSpeed(0.5);
        break;
        case Stopped:
        //Need motors to not be running in this state
        m_ballPathSubsystem.setMotorSpeed(0);
        break;
        case None:
        break;
    }

    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
