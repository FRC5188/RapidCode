package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BallPath.BallPathState;

public class CmdShooterShoot extends CommandBase {
    Shooter m_shooterSubsystem;
    BallPath m_ballPathSubsystem;
    double m_hoodAngle;
    double m_speed;

    public CmdShooterShoot(Shooter shooterSubsystem, BallPath ballPathSubsystem, double hoodAngle, double speed) {
        m_shooterSubsystem = shooterSubsystem;
        m_ballPathSubsystem = ballPathSubsystem;
        m_hoodAngle = hoodAngle;
        m_speed = speed;
    }

    @Override
    public void initialize() {
        m_shooterSubsystem.setHoodSetPoint(m_hoodAngle);
        m_ballPathSubsystem.setBallPathState(BallPathState.Shooting);
    }

    @Override
    public void execute() {
        // make flywheels go and move hood to position.
        // check if flywheels are at full speed and if hood is in position
        // start a shoot procedure
        if (m_shooterSubsystem.atHoodSetpoint() && (m_shooterSubsystem.getFlywheelRPM() >= m_shooterSubsystem.getFlywheelSpeedSetpoint())) {
            //shoot :)
            m_ballPathSubsystem.setMotorSpeed(Constants.BALL_PATH_SHOOTING_SPEED);
        } else {
            m_shooterSubsystem.hoodPIDExecute();
            m_shooterSubsystem.setTopFlywheelSpeed(m_speed);
            m_shooterSubsystem.setBottomFlywheelSpeed(m_speed);
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        // Should set flywheel speed to 0 when the command ends
        m_shooterSubsystem.setTopFlywheelSpeed(0);
        m_shooterSubsystem.setBottomFlywheelSpeed(0);
        m_ballPathSubsystem.setMotorSpeed(0);
    }

    @Override
    public boolean isFinished() {
        // change after merge to finish when ball count is 0
        return (m_ballPathSubsystem.getBallCount() <= 0);
    }
}