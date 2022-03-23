package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class CmdWait extends CommandBase {
    private int m_time;

    public CmdWait(int timeIn20Ms) {
        m_time = timeIn20Ms;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_time--;
        System.out.println(m_time);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("TIMER DONE");
    }

    @Override
    public boolean isFinished() {
        return (m_time <= 0);
    }
}
