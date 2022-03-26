package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;

public class GrpAutoTwoBallToDefense extends SequentialCommandGroup {
  public GrpAutoTwoBallToDefense(Drive driveSubsystem, BallPath ballPathSubsystem, Pickup pickupSubsystem, Shooter shooterSubsystem, 
        ShooterLookupTable lookupTable, Vision visionSubsystem, double timer) {
    GrpAutoTwoBallShoot twoBall = new GrpAutoTwoBallShoot(driveSubsystem, ballPathSubsystem, pickupSubsystem, 
        shooterSubsystem, lookupTable, visionSubsystem, timer);
    addCommands(
      twoBall,
      new CmdDriveRotate(driveSubsystem, Constants.DEFENSE_AUTO_ANGLE, Constants.DEFENSE_AUTO_ROTATE_SPEED, false),
      new CmdPickupDeploy(pickupSubsystem, ballPathSubsystem),
      new CmdDriveDistance(driveSubsystem, 104, Constants.DEFENSE_AUTO_DRIVE_SPEED, true),
      new CmdPickupStow(pickupSubsystem)
    );
  }
}
