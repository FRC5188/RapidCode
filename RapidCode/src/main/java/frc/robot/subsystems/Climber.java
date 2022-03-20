package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    private WPI_TalonFX m_climberRight;
    private WPI_TalonFX m_climberLeft;

    private boolean m_canMove;
    private double m_leftEncoderPos;
    private double m_rightEncoderPos;

    /** Creates a new climberSubsystem. */
    public Climber() {
        m_climberRight = new WPI_TalonFX(Constants.CAN.CLIMBER_MOTOR_ID_RIGHT);
        m_climberLeft = new WPI_TalonFX(Constants.CAN.CLIMBER_MOTOR_ID_LEFT);
        m_climberRight.setInverted(true);
        m_climberLeft.setInverted(true);
        m_climberRight.setNeutralMode(NeutralMode.Brake);
        m_climberLeft.setNeutralMode(NeutralMode.Brake);
        m_canMove = false;

        m_leftEncoderPos = 0;
        m_rightEncoderPos = 0;
    }

    @Override
    public void periodic() {
    }

    public boolean getCanMove() {
        return m_canMove;
    }

    public double getLeftEncoderPos() {
        m_leftEncoderPos = m_climberLeft.getSelectedSensorPosition();
        return m_leftEncoderPos;
    }

    public double getRightEncoderPos() {
        m_rightEncoderPos = m_climberRight.getSelectedSensorPosition();
        return m_rightEncoderPos;
    }

    public void setClimberSpeed(double speed) {
        m_climberLeft.set((getLeftEncoderPos() > Constants.CLIMBER_ENCODER_MAX) ? 0 : speed);
        m_climberRight.set((getRightEncoderPos() > Constants.CLIMBER_ENCODER_MAX) ? 0 : speed);
    }

    public void setClimberLeftSpeed(double speed) {
        m_climberLeft.set(speed);
    }

    public void setClimberRightSpeed(double speed) {
        m_climberRight.set(speed);
    }

    public void setCanMove(boolean canMove) {
        m_canMove = canMove;
    }

}