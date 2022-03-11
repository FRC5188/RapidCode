package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Vision; // We'll use this later ;)

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
            /* 
            This purpose of this section of code is assuming that the robot is faced away from the hub in the tarmac, and also that
            the technician has place the robot where it is a straight path to the ball. 
            
            The section will leave the tarmac, pick up one ball, and then shoot the one which is stored and also the one which it's just
            picked up. 
            */
            // new CmdPickupDeploy(pickupSubsystem), // Engages the grabber at the front of the robot. 
            new CmdDriveDistance(driveSubsystem, 45, .5, true) /* Drives 99 Inches torward the ball, we plan on adding code which will check if a ball
            has been passed in. */
            // new CmdDriveRotate(driveSubsystem, 180, true), // Turns the robot 180 degrees. 
            // new CmdPickupStow(pickupSubsystem) // Stop the grabby grabby thing
        );
    }
}
