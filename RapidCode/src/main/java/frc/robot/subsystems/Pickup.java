package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pickup extends SubsystemBase {

    public enum PickupState {
        Deployed,
        Retracted,
        None
    }
    
    private CANSparkMax m_pickupMotor;
    private Solenoid m_pickupSolenoid;

    private PickupState m_pickupState;

    public Pickup() {
        m_pickupState = PickupState.None;
    }

    @Override
    public void periodic() {

    }

    public PickupState getPickupState() {
        return m_pickupState;
    }

    public void setPickupState(PickupState state) {
        m_pickupState = state;
        
        switch (m_pickupState){
            case Deployed:
                m_pickupSolenoid.set(true);
                m_pickupMotor.set(0.5);
                break;
            case Retracted:
                m_pickupSolenoid.set(false);
                m_pickupMotor.set(0);
                break;
            case None:
            default:
                break;
        }
    }
}
