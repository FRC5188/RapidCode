package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    private WPI_TalonFX m_climberRight;
    private WPI_TalonFX m_climberLeft;

    private boolean m_canMove;

    /** Creates a new climberSubsystem. */
    public Climber() {
        m_climberRight = new WPI_TalonFX(Constants.CAN.CLIMBER_MOTOR_ID_RIGHT);
        m_climberLeft = new WPI_TalonFX(Constants.CAN.CLIMBER_MOTOR_ID_LEFT);
        m_climberRight.setInverted(true);
        m_climberLeft.setInverted(true);
        m_canMove = false;
    }

    @Override
    public void periodic() {
        //System.out.println("Can Move: " + m_canMove + " Speed: " + m_climberLeft.get());
    }

    public boolean getCanMove() {
        return m_canMove;
    }

    public void setClimberSpeed(double speed) {
        m_climberLeft.set(speed);
        //m_climberRight.set(speed);
    }

    public void setCanMove(boolean canMove) {
        m_canMove = canMove;
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
}