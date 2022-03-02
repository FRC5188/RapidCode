package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;

public class CmdBallPathManual extends CommandBase {
    // bad, will break
    // private BallPath m_ballPathSubsystem = new BallPath();
    private BallPath m_ballPathSubsystem;

    double m_speed = 0;
    public CmdBallPathManual(BallPath ballPathSubsystem, double speed) {
        m_ballPathSubsystem = ballPathSubsystem;
        m_speed = speed;

        addRequirements(m_ballPathSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_ballPathSubsystem.setMotorSpeed(m_speed);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
