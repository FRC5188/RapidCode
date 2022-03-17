package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.BallPath.BallPathState;

public class CmdShooterShootM extends CommandBase {
    Shooter m_shooterSubsystem;
    BallPath m_ballPathSubsystem;

    public CmdShooterShootM(Shooter shooterSubsystem, BallPath ballPathSubsystem) {
        m_shooterSubsystem = shooterSubsystem;
        m_ballPathSubsystem = ballPathSubsystem;
    }

    @Override
    public void initialize() {
        m_ballPathSubsystem.setBallPathState(BallPathState.Shooting);
        m_shooterSubsystem.setHoodSetPoint(m_shooterSubsystem.getHoodPotentiometerAngle());
    }

    @Override
    public void execute() {
        m_shooterSubsystem.setAcceleratorSpeed(0.4);
        m_shooterSubsystem.hoodPIDExecute();
        m_shooterSubsystem.setTopFlywheelSpeed(m_shooterSubsystem.getShooterSpeed());
        m_shooterSubsystem.setBottomFlywheelSpeed(m_shooterSubsystem.getShooterSpeed());
    }

    @Override
    public void end(boolean interrupted) {
      m_shooterSubsystem.setTopFlywheelSpeed(0);
      m_shooterSubsystem.setBottomFlywheelSpeed(0);
      m_shooterSubsystem.setAcceleratorSpeed(0);
      m_ballPathSubsystem.setBallPathState(BallPathState.Stopped);
      m_ballPathSubsystem.resetBallCount();
      m_shooterSubsystem.setReadyToShoot(false);
    }

    @Override
    public boolean isFinished() {
        // change after merge to finish when ball count is 0
        return false;
    }

}