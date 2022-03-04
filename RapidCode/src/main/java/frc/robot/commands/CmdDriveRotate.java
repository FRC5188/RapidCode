// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class CmdDriveRotate extends CommandBase {
    private Drive m_driveSubsystem; 
    /* Declare variables which will be used to increase the scope of the variables taken in from CmdDriveRotate and let them be used in 
    initialize method.  */
    private double m_heading;
    private boolean m_resetGyro;
    /**
     * Sets the arguements that are passed in into the method to their member-level variables. 
     * @param driveSubsystem the drive subsystem from the RobotContainer
     * @param heading the angle of the robot to rotate to
     * @param resetEncoders true if you want to reset encoders
     * @param resetGyro true if you want to reset gyro
     */
    public CmdDriveRotate(Drive driveSubsystem, double heading, boolean resetGyro) {
        m_driveSubsystem = driveSubsystem;
        m_heading = heading;
        m_resetGyro = resetGyro;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_driveSubsystem.rotatePIDInit(m_heading, m_resetGyro);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_driveSubsystem.rotatePIDExec();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_driveSubsystem.arcadeDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_driveSubsystem.atRotatePIDSetpoint();
    }
}
