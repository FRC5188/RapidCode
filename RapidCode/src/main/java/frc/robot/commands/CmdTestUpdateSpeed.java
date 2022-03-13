package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class CmdTestUpdateSpeed extends CommandBase {
    private Shooter m_s;
    private double m_add;

    public CmdTestUpdateSpeed(Shooter s, double add) {
        m_s = s;
        m_add = add;
    }

    @Override
    public void initialize() {
        m_s.addToSpeed(m_add);
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
