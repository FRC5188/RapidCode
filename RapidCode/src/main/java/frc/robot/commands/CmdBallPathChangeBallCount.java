package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;

public class CmdBallPathChangeBallCount extends CommandBase {
    private BallPath m_ballPathSubsystem;
    private boolean m_isIncrementing;

    public CmdBallPathChangeBallCount(BallPath ballPathSubsystem, boolean isIncrementing) {
        m_ballPathSubsystem = ballPathSubsystem;
        m_isIncrementing = isIncrementing;
    }

    @Override
    public void initialize() {
        if (m_isIncrementing) {
            m_ballPathSubsystem.incrementBallCount();
        } else {
            m_ballPathSubsystem.decrementBallCount();
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
