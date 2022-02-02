package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pickup extends SubsystemBase {

    public enum PickupState {
        Deployed,
        Retracted,
        None
    }
    //We need one neo 550 and one solenoid

    private PickupState m_pickupState;

    public Pickup() {
        m_pickupState = PickupState.None;
    }

    @Override
    public void periodic() {

    }

    public PickupState getPickupState() {
        return null;
    }

    public void setPickupState(PickupState state) {
        
    }
}
