// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class CmdDriveDistance extends CommandBase {
    /** Creates a new CmdDriveDistance. */
    private Drive m_driveSubsystem; /* Member-Level private variables which serve to increase the scope of the variables that are passed
    in in the CmdDriveDistance method located below.  */
    private double m_distance;
    private boolean m_resetEncoders;

    public CmdDriveDistance(Drive driveSubsystem, double distance, boolean resetEncoders) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_driveSubsystem = driveSubsystem; // Setting values passed in to there equivelent member level variables. 
        m_distance = distance;
        m_resetEncoders = resetEncoders;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {//Member-level varibles passed in which were orgin from the CmdDriveDistance method above.
        m_driveSubsystem.drivePIDInit(m_distance, m_resetEncoders); 
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_driveSubsystem.drivePIDExec();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_driveSubsystem.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_driveSubsystem.atDrivePIDSetpoint();
    }
}
