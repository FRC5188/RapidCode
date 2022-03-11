package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pickup extends SubsystemBase {

    /**
     * Represents the different possible states of the pickup
     */
    public enum PickupState {
        Deployed,
        Retracted,
        None
    }
    
    private CANSparkMax m_pickupMotor;
    private CANSparkMax m_indexMotorBottom;

    private Solenoid m_pickupLeftSolenoid;
    private Solenoid m_pickupRightSolenoid;

    private PickupState m_pickupState;

    /**
     * Creates a new instance of the Pickup subsystem
     */
    public Pickup() {
        m_pickupMotor = new CANSparkMax(Constants.CAN.PICKUP_MOTOR_ID, MotorType.kBrushless);
        m_pickupMotor.setIdleMode(IdleMode.kCoast);

        m_indexMotorBottom = new CANSparkMax(Constants.CAN.INDEX_MOTOR_BOTTOM_ID, MotorType.kBrushless);
        m_indexMotorBottom.setIdleMode(IdleMode.kCoast);
        m_indexMotorBottom.setInverted(true);

        m_pickupLeftSolenoid = new Solenoid(Constants.CAN.REV_PH_ID, PneumaticsModuleType.REVPH, Constants.PCM.PICKUP_LEFT_SOLENOID);
        m_pickupRightSolenoid = new Solenoid(Constants.CAN.REV_PH_ID, PneumaticsModuleType.REVPH, Constants.PCM.PICKUP_RIGHT_SOLENOID);

        m_pickupState = PickupState.None;
    }

    @Override
    public void periodic() {

    }

    /**
     * Gets the current state of the pickup
     * @return the current state of the pickup
     */
    public PickupState getPickupState() {
        return m_pickupState;
    }

    /**
     * Sets the speed of the lower index belt
     * @param speed speed of the lower index belt in percent output notation
     */
    public void setLowerIndexSpeed(double speed) {
        m_indexMotorBottom.set(speed);
    }

    /**
     * Sets the state of the pickup
     * @param state desired state of the pickup
     */
    public void setPickupState(PickupState state) {
        m_pickupState = state;
        
        switch (m_pickupState){
            case Deployed:
                m_pickupLeftSolenoid.set(true);
                m_pickupRightSolenoid.set(true);
                m_pickupMotor.set(0.5);
                m_indexMotorBottom.set(0.5);
                break;
            case Retracted:
                m_pickupLeftSolenoid.set(false);
                m_pickupRightSolenoid.set(false);
                m_pickupMotor.set(0);
                m_indexMotorBottom.set(0);
                break;
            case None:
            default:
                break;
        }
    }
}
