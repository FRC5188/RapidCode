package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;
import frc.robot.subsystems.BallPath;

public class GrpAutoFenderAndTaxi extends SequentialCommandGroup {

    public GrpAutoFenderAndTaxi(Drive driveSubsystem, BallPath ballPathSubsystem, Pickup pickupSubsystem, Shooter shooterSubsystem, Vision visionSubsystem, ShooterLookupTable lookupTable) {
        GrpShootWithoutVision shoot = new GrpShootWithoutVision(shooterSubsystem, ballPathSubsystem, lookupTable, Constants.FRONT_OF_FENDER_DISTANCE);

        addCommands(
            shoot,
            new CmdWait(100),
            new CmdShooterStopShooting(shooterSubsystem, ballPathSubsystem, shoot),
            new CmdDriveDistance(driveSubsystem, -100, 0.5, true)
        );
    }
}
