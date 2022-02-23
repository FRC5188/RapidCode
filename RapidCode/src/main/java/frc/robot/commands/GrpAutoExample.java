package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Vision;

public class GrpAutoExample extends SequentialCommandGroup {
    // put any subsystems used in the auto routine in the parameters
    // you don't need any member level variables
    public GrpAutoExample(Drive driveSubsystem, Pickup pickupSubsystem) {
        /**
         * Autonomous Mode Procedure Version 1
         * Purpose: It will drive from the tarmac, pick up one ball, and shoot two balls into the high goal.
         * Result: We'll hopefully get ten points, we can get a RP if our other alliance members are able to score three more cargo in AUTO. 
         */
        addCommands(
            // add a command here. The order you put them in is the order they will run
            new CmdPickupDeploy(pickupSubsystem),
            new CmdDriveDistance(driveSubsystem, 99, true), //values are in inches
            new CmdDriveRotate(driveSubsystem, 180, true),
            new CmdPickupStow(pickupSubsystem)
            /* 
            Add shooter statement later, we'll need the speed and then the hoodAngle for the shooter method, which will be
            given by the table which we'll also make later.
             */
        );
    }
}
