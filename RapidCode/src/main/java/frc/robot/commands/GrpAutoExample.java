package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;

public class GrpAutoExample extends SequentialCommandGroup {
    public GrpAutoExample(Drive driveSubsystem, Pickup pickupSubsystem) {

        addCommands(
            new CmdDriveDistance(driveSubsystem, 12, true),
            new CmdDriveRotate(driveSubsystem, 90, true),
            new CmdPickupDeploy(pickupSubsystem),
            new CmdPickupStow(pickupSubsystem)
        );
    }
}
