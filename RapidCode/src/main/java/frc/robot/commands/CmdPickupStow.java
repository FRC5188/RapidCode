package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Pickup.PickupState;

public class CmdPickupStow extends CommandBase {
    private Pickup m_pickupSubsystem;
    private int m_count;
     
    public CmdPickupStow(Pickup pickupSubsystem) {
        m_pickupSubsystem = pickupSubsystem;
        m_count = 13;
    }

    @Override
    public void initialize() {
        m_pickupSubsystem.setPickupState(PickupState.Retracted);
    }

    @Override
    public void execute() {
        m_pickupSubsystem.setLowerIndexSpeed(0.5);
        m_pickupSubsystem.setPickupSpeed(0.5);
        m_count--;
    }

    @Override
    public void end(boolean interrupted) {
        m_pickupSubsystem.setLowerIndexSpeed(0);
        m_pickupSubsystem.setPickupSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return m_count <= 0;
    }
}
