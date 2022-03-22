// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;
import frc.robot.subsystems.BallPath;

public class GrpAutoTwoBallShoot extends SequentialCommandGroup {

  public GrpAutoTwoBallShoot(Drive driveSubsystem, Pickup pickupSubsystem, Shooter shooterSubsystem,
      ShooterLookupTable lookupTable, Vision visionSubsystem, BallPath ballPathSubsystem, double timer) {
    GrpShootWithoutVision shoot = new GrpShootWithoutVision(shooterSubsystem, ballPathSubsystem, lookupTable,
        Constants.FRONT_OF_FENDER_AUTO, timer);

    addCommands(
        new CmdPickupDeploy(pickupSubsystem, ballPathSubsystem),
        new CmdDriveDistance(driveSubsystem, 41, 0.5, true),
        new CmdPickupStow(pickupSubsystem),
        new CmdDriveDistance(driveSubsystem, 0, 0.5, false),
        shoot,
        new CmdShooterStopShooting(shooterSubsystem, ballPathSubsystem, shoot));
  }
}
