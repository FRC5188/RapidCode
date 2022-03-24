package frc.robot.commands;

import java.security.PublicKey;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class CmdManualShooterSpeedCtrl extends CommandBase {
    private Shooter m_s;
    private double m_add;

    public CmdManualShooterSpeedCtrl(Shooter s, double add) {
        m_s = s;
        m_add = add;
    }

    @Override
    public void initialize() {
        System.out.println("manual speed adjust cmd");
        m_s.setTopFlywheelSpeed(m_s.getTopFlywheelSpeed() - m_add);

        m_s.setBottomFlywheelSpeed(m_s.getBottomFlywheelSpeed() - m_add);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Manual shooter speed ctrl end");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}