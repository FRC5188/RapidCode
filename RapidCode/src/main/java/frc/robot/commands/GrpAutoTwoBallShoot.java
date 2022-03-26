// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;
import frc.robot.subsystems.BallPath;

public class GrpAutoTwoBallShoot extends SequentialCommandGroup {

  public GrpAutoTwoBallShoot(Drive driveSubsystem,  BallPath ballPathSubsystem, Pickup pickupSubsystem, Shooter shooterSubsystem,
        ShooterLookupTable lookupTable, Vision visionSubsystem,  double timer) {
    GrpShootWithoutVision shoot = new GrpShootWithoutVision(shooterSubsystem, ballPathSubsystem, lookupTable,
        Constants.BACK_OF_FENDER_DISTANCE, timer);

    addCommands(
        new CmdPickupDeploy(pickupSubsystem, ballPathSubsystem),
        new ParallelCommandGroup( // need to run ball path default while moving
          new CmdBallPathAutonomousUse(ballPathSubsystem),
          new SequentialCommandGroup( //literally disgusting
            new CmdDriveDistance(driveSubsystem, 93, 0.4, true), // add wait after this 
            new WaitCommand(1),
            new CmdPickupStow(pickupSubsystem)
          )
        ),
         // wait two seconds before picking up intake
        new CmdDriveDistance(driveSubsystem, 0, 0.40, false),
        shoot,
        new CmdShooterStopShooting(shooterSubsystem, ballPathSubsystem, shoot));
  }
}