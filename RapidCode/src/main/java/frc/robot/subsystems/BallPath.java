package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BallPath extends SubsystemBase {
    private Dashboard m_dashboard;

    /**
     * Represents the different states of the ball path
     */
    public enum BallPathState {
        Loading,
        MovingToPosition,
        Stopped,
        Shooting,
        None
    }

    private CANSparkMax m_indexMotorTop;

    private DigitalInput m_entranceSensor;
    private DigitalInput m_middleSensor;
    private DigitalInput m_shooterSensor;

    private int m_ballCount;
    private BallPathState m_ballPathState;

    private boolean m_entranceWasDetected;
    private boolean m_middleWasDetected;
    private boolean m_shooterWasDetected;

    /**
     * Creates a new instance of the BallPath subsystem
     */
    public BallPath(Dashboard dashboard) {
        m_dashboard = dashboard;

        m_indexMotorTop = new CANSparkMax(Constants.CAN.INDEX_MOTOR_TOP_ID, MotorType.kBrushless);
        m_indexMotorTop.setInverted(true);
        m_indexMotorTop.setIdleMode(IdleMode.kBrake);

        m_entranceSensor = new DigitalInput(Constants.DIO.ENTRANCE_SENSOR_PORT);
        m_middleSensor = new DigitalInput(Constants.DIO.MIDDLE_SENSOR_PORT);
        m_shooterSensor = new DigitalInput(Constants.DIO.SHOOTER_SENSOR_PORT);

        m_ballPathState = BallPathState.None;
        m_ballCount = 1;
    }

    @Override
    public void periodic() {
        m_dashboard.setBallCount(m_ballCount);
        m_dashboard.setBallPathState(m_ballPathState);
        m_dashboard.setEntranceSensorState(!m_entranceSensor.get());
        m_dashboard.setMiddleSensorState(!m_middleSensor.get());
    }

    /**
     * Sets the speed of the top indexing motor
     * @param speed the speed of the motor in percent output notation
     */
    public void setMotorSpeed(double speed) {
        m_indexMotorTop.set(speed);
    }

    /**
     * Gets the current number of balls held by the robot
     * @return the number of balls
     */
    public int getBallCount() {
        return m_ballCount;
    }

    /**
     * Increments the ball count by one
     */
    public void incrementBallCount() {
        if (m_ballCount < 2) m_ballCount++;
    }

    /**
     * Decrements the ball count by one
     */
    public void decrementBallCount() {
        if (m_ballCount > 0) m_ballCount--;
    }

    public void resetBallCount() { // Resets ball count to zero.
        m_ballCount = 0;
    }

    /**
     * Gets the current state of the ball path
     * @return the current state of the ball path
     */
    public BallPathState getBallPathState() {
        return m_ballPathState;
    }

    /**
     * Sets the ball path's state
     * @param state the desired state of the ball path
     */
    public void setBallPathState(BallPathState state) {
        m_ballPathState = state; 
    }

    /**
     * Returns true if a ball has entered the ball path
     * @return true if a ball has entered the ball path
     */
    public boolean hasEnteredBallPath() { 
        return (m_ballPathState == BallPathState.Loading || m_ballPathState == BallPathState.MovingToPosition);
    }

    public boolean hasLeftBallPath() {
        return (m_shooterSensor.get() && m_shooterWasDetected);
    }

    /**
     * Updates the state of the ball path based on light sensor detection
     */
    public void updateBallPathState() {
        if (!m_entranceSensor.get()) {
            m_ballPathState = BallPathState.Loading;
        } else if (m_entranceSensor.get() && m_entranceWasDetected) {
            incrementBallCount();
            m_ballPathState = BallPathState.MovingToPosition;
        } else if (m_middleSensor.get() && m_middleWasDetected) {
            m_ballPathState = BallPathState.Stopped;
        }
         else if (m_shooterSensor.get() && m_shooterWasDetected) {
            decrementBallCount();
        }

        m_entranceWasDetected = !m_entranceSensor.get();
        m_middleWasDetected = !m_middleSensor.get();
        m_shooterWasDetected = !m_shooterSensor.get();
    }
}
