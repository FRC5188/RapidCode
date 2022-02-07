// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;

public class CmdAutoRunTrajectory extends CommandBase {
    private Drive m_driveSubsystem;
    private Trajectory m_trajectory;
    private RamseteCommand m_ramseteCommand;

    public CmdAutoRunTrajectory(Drive driveSubsystem, Trajectory trajectory) {
        m_driveSubsystem = driveSubsystem;
    }

    @Override
    public void initialize() {
        RamseteCommand ramseteCommand = new RamseteCommand(
            m_trajectory,
            m_driveSubsystem::getPose,
            new RamseteController(Constants.K_RAMSETE_B, Constants.K_RAMSETE_ZETA),
            new SimpleMotorFeedforward(Constants.K_S_VOLTS,
                    Constants.K_V_VOLTS_SECONDS_PER_METER,
                    Constants.K_A_VOLTS_SECONDS_SQUARED_PER_METER),
                Constants.K_DRIVE_KINEMATICS,
                m_driveSubsystem::getDiffWheelSpeeds,
            new PIDController(Constants.K_P_DRIVE_VEL, 0, 0),
            new PIDController(Constants.K_P_DRIVE_VEL, 0, 0),
            // RamseteCommand passes volts to the callback
            m_driveSubsystem::tankDriveVolts,
            m_driveSubsystem);

        m_ramseteCommand = ramseteCommand;

        // Reset odometry to the starting pose of the trajectory.
        m_driveSubsystem.resetOdometry(m_trajectory.getInitialPose());
    }

    @Override
    public void execute() {
        m_ramseteCommand.execute();
    }

    @Override
    public void end(boolean interrupted) {
        m_driveSubsystem.tankDriveVolts(0, 0);
    }

    @Override
    public boolean isFinished() {
        return m_ramseteCommand.isFinished();
    }
}
