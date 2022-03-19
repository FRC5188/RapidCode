package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BallPath.BallPathState;

public class CmdShooterStopShooting extends CommandBase {
    Shooter m_shooterSubsystem;
    BallPath m_ballPathSubsystem;
    GrpShootWithoutVision m_shoot;

    public CmdShooterStopShooting(Shooter shooterSubsystem, BallPath ballPathSubsystem, GrpShootWithoutVision shootingCommand) {
        m_shooterSubsystem = shooterSubsystem;
        m_ballPathSubsystem = ballPathSubsystem;
        m_shoot = shootingCommand;
    }

    @Override
    public void initialize() {
        m_shoot.cancel();
        m_shooterSubsystem.setTopFlywheelSpeed(0);
        m_shooterSubsystem.setBottomFlywheelSpeed(0);
        m_shooterSubsystem.setAcceleratorSpeed(0);
        m_ballPathSubsystem.setBallPathState(BallPathState.Stopped);
        m_ballPathSubsystem.resetBallCount();
        m_shooterSubsystem.setReadyToShoot(false);
    }

    @Override
    public void execute() {
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
        return true;
    }
}
