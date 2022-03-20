package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;

public class GrpDriveForward extends SequentialCommandGroup {

    public GrpDriveForward(Drive driveSubsystem, Pickup pickupSubsystem) {
        addCommands(
            new CmdDriveDistance(driveSubsystem, -100, 0.6, true)
        );
    }
}
