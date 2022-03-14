package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision; 
import frc.robot.subsystems.BallPath;

public class GrpAutoFurthestFromHubPickupShoot extends SequentialCommandGroup {
    // put any subsystems used in the auto routine in the parameters
    // you don't need any member level variables
    public GrpAutoFurthestFromHubPickupShoot(Drive driveSubsystem, BallPath ballPathSubsystem, Pickup pickupSubsystem, Shooter shooterSubsystem, Vision visionSubsystem, ShooterLookupTable lookupTable) {
        addCommands(
<<<<<<< HEAD
            new CmdPickupDeploy(pickupSubsystem, ballPathSubsystem), 
            new CmdDriveDistance(driveSubsystem, 40.44, 0.5, true), // Positive is towards the pickup side
            new CmdDriveRotate(driveSubsystem, 180 + 9.45, 0.5, true), // 180 degrees + the angle between the perpendicular and the angle to the target, assuming clockwise is positive
            new CmdShooterMoveToPosition(shooterSubsystem, lookupTable, 153),
            new CmdShooterShoot(shooterSubsystem, ballPathSubsystem),
=======
            new CmdPickupDeploy(pickupSubsystem), 
            new CmdDriveDistance(driveSubsystem, 40.44, .5, true), // Positive is towards the pickup side
            new CmdDriveRotate(driveSubsystem, 180 + 9.45, 0.5, true), // 180 degrees + the angle between the perpendicular and the angle to the target, assuming clockwise is positive
            new CmdShooterMoveToPosition(shooterSubsystem, visionSubsystem, lookupTable, 153),
            new CmdShooterShoot(shooterSubsystem, ballPathSubsystem, lookupTable.getVelocityAtDistance(153)),
>>>>>>> c699f62b15f5e32e5b19d1d83a3aa2727045cc10
            new CmdPickupStow(pickupSubsystem)
        );
    }
}
