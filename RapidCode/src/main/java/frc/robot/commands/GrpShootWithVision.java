package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;

public class GrpShootWithVision extends SequentialCommandGroup {
    public GrpShootWithVision(Shooter shooterSubsystem, Vision visionSubsystem, BallPath ballPathSubsystem, ShooterLookupTable lookupTable, double timer) {
        addCommands(
            new CmdShooterMoveToPosition(shooterSubsystem, lookupTable, (int) visionSubsystem.getDistanceToTarget()),
            new CmdShooterShoot(shooterSubsystem, ballPathSubsystem, lookupTable, timer)
        );
    }
}
