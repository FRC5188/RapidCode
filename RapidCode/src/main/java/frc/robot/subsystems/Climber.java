package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    private WPI_TalonFX m_climberRight;
    private WPI_TalonFX m_climberLeft;

    /** Creates a new climberSubsystem. */
    public Climber() {
        m_climberRight = new WPI_TalonFX(Constants.CAN.CLIMBER_MOTOR_ID_RIGHT);
        m_climberLeft = new WPI_TalonFX(Constants.CAN.CLIMBER_MOTOR_ID_LEFT);
    }

    @Override
    public void periodic() {
    }

    public void climberUp() {
        m_climberRight.set(ControlMode.PercentOutput, -Constants.CLIMBER_SPEED);
        m_climberLeft.set(ControlMode.PercentOutput, -Constants.CLIMBER_SPEED);
    }

    public void climberDown() {
        m_climberRight.set(ControlMode.PercentOutput, Constants.CLIMBER_SPEED);
        m_climberLeft.set(ControlMode.PercentOutput, Constants.CLIMBER_SPEED);
    }

    // checks how much the trigger is pressed down, .2 being 20%, that way we dont
    // accidentally start the climber
    public void climberMove(Double upSpeed, Double downSpeed) {
        if (upSpeed < -.2) {
            m_climberRight.set(ControlMode.PercentOutput, -upSpeed);
            m_climberLeft.set(ControlMode.PercentOutput, -upSpeed);
        } else if (downSpeed < -.2) {
            m_climberRight.set(ControlMode.PercentOutput, downSpeed);
            m_climberLeft.set(ControlMode.PercentOutput, downSpeed);
        }
    }

    public void stop() {
        m_climberRight.set(ControlMode.PercentOutput, 0);
        m_climberLeft.set(ControlMode.PercentOutput, 0);
    }
}