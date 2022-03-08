package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;

public class CmdBallPathManual extends CommandBase {
    private BallPath m_ballPathSubsystem;
    private double m_speed;
    
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
