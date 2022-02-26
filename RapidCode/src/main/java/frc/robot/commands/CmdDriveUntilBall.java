// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.Constants;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.BallPath.*;

public class CmdDriveUntilBall extends CommandBase {
    private Drive m_driveSubsystem; 

    private BallPath m_ballPathSubsystem;
    private BallPathState m_ballPathState;

    public CmdDriveUntilBall(Drive driveSubsystem, boolean resetEncoders) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_driveSubsystem = driveSubsystem; // Setting values passed in to there equivelent member level variables. 
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_driveSubsystem.arcadeDrive(Constants.AUTO_DRIVE_SPEED,0);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_driveSubsystem.arcadeDrive(0,0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_ballPathSubsystem.hasEnteredBallPath();
    }
}
