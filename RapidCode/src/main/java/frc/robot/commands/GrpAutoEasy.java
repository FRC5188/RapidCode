package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.subsystems.Drive;

public class GrpAutoEasy extends SequentialCommandGroup {
    ArrayList<Trajectory> m_trajectories = Robot.getAutoTrajectories("AutoEasy");
    Trajectory m_traj1 = m_trajectories.get(0);

    public GrpAutoEasy(Drive driveSubsystem) {
        addCommands(
        new CmdAutoRunTrajectory(driveSubsystem, m_traj1)
        );
    }
}
