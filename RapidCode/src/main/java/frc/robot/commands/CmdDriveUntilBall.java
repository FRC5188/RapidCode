package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.BallPath;

public class CmdDriveUntilBall extends CommandBase {
    private Drive m_driveSubsystem; 

    private BallPath m_ballPathSubsystem;

    public CmdDriveUntilBall(Drive driveSubsystem, boolean resetEncoders) {
        m_driveSubsystem = driveSubsystem;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        m_driveSubsystem.arcadeDrive(0.25, 0);
    }

    @Override
    public void end(boolean interrupted) {
        m_driveSubsystem.arcadeDrive(0,0);
    }

    @Override
    public boolean isFinished() {
        return m_ballPathSubsystem.hasEnteredBallPath();
    }
}
