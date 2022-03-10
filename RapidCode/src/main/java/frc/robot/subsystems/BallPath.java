package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BallPath extends SubsystemBase {

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

    public BallPath() {
        m_indexMotorTop = new CANSparkMax(Constants.CAN.INDEX_MOTOR_TOP_ID, MotorType.kBrushless);
        m_indexMotorTop.setInverted(true);
        m_indexMotorTop.setIdleMode(IdleMode.kBrake);

        m_entranceSensor = new DigitalInput(Constants.DIO.ENTRANCE_SENSOR_PORT);
        m_middleSensor = new DigitalInput(Constants.DIO.MIDDLE_SENSOR_PORT);
        m_shooterSensor = new DigitalInput(Constants.DIO.SHOOTER_SENSOR_PORT);

        m_ballPathState = BallPathState.None;
    }

    @Override
    public void periodic() {

    }

    public void setMotorSpeed(double speed) {
        m_indexMotorTop.set(speed);
    }

    public int getBallCount() {
        return m_ballCount;
    }

    public void incrementBallCount() {
        if (m_ballCount < 2) m_ballCount++;
    }

    public void decrementBallCount() {
        if (m_ballCount > 0) m_ballCount--;
    }

    public BallPathState getBallPathState() {
        return m_ballPathState;
    }

    public void setBallPathState(BallPathState state) {
        m_ballPathState = state; 
    }

    public boolean hasEnteredBallPath() { 
        return (m_ballPathState == BallPathState.Loading || m_ballPathState == BallPathState.MovingToPosition);
    }

    public void updateBallPathState() {
        /*
         * A setter method plus some extra logic to decide what to set to.
         * This takes some of the logic out of the default command by letting the
         * subsystem do some of the thinking (which means fewer getters across files)
         * We will not set any motors here; that will be done in the default command
         * We will be setting the ball path state here, however
         */
        if (!m_entranceSensor.get()) {
            System.out.println("Ball Entering");
            m_ballPathState = BallPathState.Loading;
        } else if (m_entranceSensor.get() && m_entranceWasDetected) {
            System.out.println("Added to count");
            incrementBallCount();
            m_ballPathState = BallPathState.MovingToPosition;
        } else if (m_middleSensor.get() && m_middleWasDetected) {
            System.out.println("At position");
            m_ballPathState = BallPathState.Stopped;
        } else if (m_shooterSensor.get() && m_shooterWasDetected) {
            System.out.println("Ball Exited");
            decrementBallCount();
        }

        m_entranceWasDetected = !m_entranceSensor.get();
        m_middleWasDetected = !m_middleSensor.get();
        m_shooterWasDetected = !m_shooterSensor.get();
    }
}
