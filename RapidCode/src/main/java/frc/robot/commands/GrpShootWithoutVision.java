package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;

public class GrpShootWithoutVision extends SequentialCommandGroup {
    public GrpShootWithoutVision(Shooter shooterSubsystem, BallPath ballPathSubsystem, ShooterLookupTable lookupTable, int distanceInInches) {

        addCommands(
            new CmdShooterMoveToPosition(shooterSubsystem, lookupTable, distanceInInches),
            new CmdShooterShoot(shooterSubsystem, ballPathSubsystem, lookupTable, distanceInInches)
        );
    }
}
