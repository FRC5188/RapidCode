package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Pickup.PickupState;

public class CmdPickupDeploy extends CommandBase {
    private Pickup m_pickupSubsystem;
    private BallPath m_ballPathSubsystem;

    public CmdPickupDeploy(Pickup pickupSubsystem, BallPath ballPathSubsystem) {
        m_pickupSubsystem = pickupSubsystem;
        m_ballPathSubsystem = ballPathSubsystem;
    }

    @Override
    public void initialize() {
        if (m_ballPathSubsystem.getBallCount() < 2) m_pickupSubsystem.setPickupState(PickupState.Deployed);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
