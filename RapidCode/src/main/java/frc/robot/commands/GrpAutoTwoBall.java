// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;
import frc.robot.subsystems.Vision;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class GrpAutoTwoBall extends ParallelCommandGroup {
  /** Creates a new GrpAuyoTwoBall. */
  public GrpAutoTwoBall(Drive driveSubsystem,BallPath ballPathSubsystem, Pickup pickupSubsystem, Shooter shooterSubsystem, ShooterLookupTable lookupTable, Vision visionSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    GrpAutoTwoBallShoot twoBallShoot = new GrpAutoTwoBallShoot(driveSubsystem, ballPathSubsystem, pickupSubsystem, shooterSubsystem, lookupTable, visionSubsystem, 0);
    addCommands(
      new CmdBallPathDefault(ballPathSubsystem),
      twoBallShoot
    );
  }
}
