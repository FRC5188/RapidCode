package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Pickup.PickupState;

public class CmdPickupDeploy extends CommandBase {
    private Pickup m_pickupSubsystem;

    public CmdPickupDeploy(Pickup pickupSubsystem) {
        m_pickupSubsystem = pickupSubsystem;

    }

    @Override
    public void initialize() {
        m_pickupSubsystem.setPickupState(PickupState.Deployed);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
