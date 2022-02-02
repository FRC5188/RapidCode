package frc.robot;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.commands.CmdDriveJoystick;
import frc.robot.subsystems.Drive;

public class RobotContainer {
    Drive m_driveSubsystem = new Drive();

    XboxController m_driveController = new XboxController(0);
    
    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        m_driveSubsystem.setDefaultCommand(new CmdDriveJoystick(m_driveSubsystem, 
                                                                () -> applyDeadband(0.6 * -m_driveController.getLeftY(), Constants.ARCADE_DRIVE_DEADBAND), 
                                                                () -> applyDeadband( 0.65 * -m_driveController.getRightX(), Constants.ARCADE_DRIVE_DEADBAND)));
    }

    public Command getAutonomousCommand() {
        var autoVoltageConstraint =
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(Constants.K_S_VOLTS,
                                        Constants.K_V_VOLTS_SECONDS_PER_METER,
                                        Constants.K_A_VOLTS_SECONDS_SQUARED_PER_METER),
                                        Constants.K_DRIVE_KINEMATICS,
                                        10);
        
        TrajectoryConfig config =
            new TrajectoryConfig(Constants.K_MAX_SPEED_METERS_PER_SECOND,
                                 Constants.K_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(Constants.K_DRIVE_KINEMATICS)
                // Apply the voltage constraint
                .addConstraint(autoVoltageConstraint);

        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(1, 1),
                new Translation2d(2, -1)
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(3, 0, new Rotation2d(0)),
            // Pass config
            config
        );
        
        // Reset odometry to the starting pose of the trajectory.
        m_driveSubsystem.resetOdometry(exampleTrajectory.getInitialPose());

        RamseteCommand ramseteCommand = new RamseteCommand(
            exampleTrajectory,
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
            m_driveSubsystem
        );

        // Reset odometry to the starting pose of the trajectory.
        m_driveSubsystem.resetOdometry(exampleTrajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> m_driveSubsystem.tankDriveVolts(0, 0));
    }

    private double applyDeadband(double raw, double deadband) {
        /* Please don't modify, but please do ask if you wanna know how it works! */
        
        double modified = 0.0;

        deadband = Math.abs(deadband);

        if (raw < -deadband)
            modified = ((raw + 1) / (1 - deadband)) - 1;
        else if (raw > deadband)
            modified = ((raw - 1) / (1 - deadband)) + 1;

        return modified;
    }
}
