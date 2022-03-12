package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class CmdTestUpdateSpeed extends CommandBase {
    private RobotContainer m_rc;
    private double m_add;

    public CmdTestUpdateSpeed(RobotContainer rc, double add) {
        m_rc = rc;
        m_add = add;
    }

    @Override
    public void initialize() {
        m_rc.addToSpeed(m_add);
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
