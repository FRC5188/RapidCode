package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;

public class CmdBallPathDefaultCommand extends CommandBase {
    private BallPath m_ballPathSubsystem;

    public CmdBallPathDefaultCommand(BallPath ballPathSubsystem) {
        m_ballPathSubsystem = ballPathSubsystem;

        addRequirements(m_ballPathSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
