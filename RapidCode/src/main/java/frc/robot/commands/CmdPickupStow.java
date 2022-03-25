package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Pickup.PickupState;

public class CmdPickupStow extends CommandBase {
    private Pickup m_pickupSubsystem;
    private Timer m_timer;
     
    public CmdPickupStow(Pickup pickupSubsystem) {
        m_pickupSubsystem = pickupSubsystem;
        m_timer = new Timer();
    }

    @Override
    public void initialize() {
        m_pickupSubsystem.setPickupState(PickupState.Retracted);
        m_pickupSubsystem.setLowerIndexSpeed(0.5); // set intake wheels on when we retract
        m_pickupSubsystem.setPickupSpeed(0.5);
        m_timer.reset();
        m_timer.start(); // start timer
    }

    @Override
    public boolean isFinished() {
        return m_timer.get() > 1.5; // dont end until timer has ran 1.5 seconds
    }

    @Override
    public void end(boolean interrupted) {
        m_timer.stop(); // stop timer
        m_pickupSubsystem.setLowerIndexSpeed(0); // turn off wheels
        m_pickupSubsystem.setPickupSpeed(0);
    }
}
