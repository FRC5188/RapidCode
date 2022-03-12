package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BallPath.BallPathState;
import frc.robot.subsystems.Vision; // We'll use this later to get distance to target. 

public class CmdShooterShoot extends CommandBase {
    Shooter m_shooterSubsystem;
    BallPath m_ballPathSubsystem;
    double m_speed;

    public CmdShooterShoot(Shooter shooterSubsystem, BallPath ballPathSubsystem, double speed) {
        m_shooterSubsystem = shooterSubsystem;
        m_ballPathSubsystem = ballPathSubsystem;
        m_speed = speed;
    }

    @Override
    public void initialize() {
        m_ballPathSubsystem.setBallPathState(BallPathState.Shooting);
    }

    @Override
    public void execute() {
        m_shooterSubsystem.setAcceleratorSpeed(0.4);
        m_shooterSubsystem.setTopFlywheelSpeed(m_speed);
        m_shooterSubsystem.setBottomFlywheelSpeed(m_speed);
    }

    @Override
    public void end(boolean interrupted) {
        // Should set flywheel speed to 0 when the command ends
        m_shooterSubsystem.setTopFlywheelSpeed(0);
        m_shooterSubsystem.setBottomFlywheelSpeed(0);
        m_ballPathSubsystem.setBallPathState(BallPathState.Stopped);
        m_ballPathSubsystem.resetBallCount(); // Resets the ball count to zero. 
    }

    @Override
    public boolean isFinished() {
        // change after merge to finish when ball count is 0
        return false;
    }

}