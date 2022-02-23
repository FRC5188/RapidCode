package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Vision;

public class GrpAutoExample extends SequentialCommandGroup {
    // put any subsystems used in the auto routine in the parameters
    // you don't need any member level variables
    public GrpAutoExample(Drive driveSubsystem, Pickup pickupSubsystem) {

        addCommands(
            // add a command here. The order you put them in is the order they will run
            new CmdPickupDeploy(pickupSubsystem),
            new CmdDriveDistance(driveSubsystem, 99, true), //values are in inches
            new CmdDriveRotate(driveSubsystem, 180, true),
            new CmdPickupStow(pickupSubsystem)
        );
    }
}
