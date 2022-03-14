package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision; 
import frc.robot.subsystems.BallPath;

public class GrpAutoCompetition extends SequentialCommandGroup {
    // put any subsystems used in the auto routine in the parameters
    // you don't need any member level variables
    public GrpAutoCompetition(Drive driveSubsystem, BallPath ballPathSubsystem, Pickup pickupSubsystem, Shooter shooterSubsystem, Vision visionSubsystem, ShooterLookupTable lookupTable) {
        addCommands(
            new CmdPickupDeploy(pickupSubsystem), 
            new CmdDriveDistance(driveSubsystem, 40.44, .5, true), 
            new CmdDriveRotate(driveSubsystem, 180, 0.5, true),
            new CmdShooterMoveToPosition(shooterSubsystem, visionSubsystem, lookupTable, 153),
            new CmdShooterShoot(shooterSubsystem, ballPathSubsystem, lookupTable.getVelocityAtDistance(153)),
            new CmdPickupStow(pickupSubsystem)
        );
    }
}
