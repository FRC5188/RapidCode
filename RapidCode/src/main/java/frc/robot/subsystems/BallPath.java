package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BallPath extends SubsystemBase {

    public enum BallPathState {
        Loading,
        MovingToPosition,
        Stopped,
        Shooting,
        None
    }

    // need 2 neos and 3 light sensors

    private CANSparkMax m_indexMotorTop;
    private CANSparkMax m_indexMotorBottom;

    private DigitalInput m_entranceSensor;
    private DigitalInput m_middleSensor;
    private DigitalInput m_shooterSensor;

    private int m_ballCount;
    private BallPathState m_ballPathState;

    private boolean m_ballPreviouslyThere;

    public BallPath() {
        // You need to initialize each member level thing. See example below
        // The ids for each motor/sensor have been made. Find where they are and reference
        m_indexMotorBottom = new CANSparkMax(Constants.CAN.INDEX_MOTOR_BOTTOM_ID, MotorType.kBrushless);
    }

    @Override
    public void periodic() {

    }

    public void setMotorSpeed(double speed) {
        m_indexMotorTop.set(speed);
        m_indexMotorBottom.set(speed);
    }

    public int getBallCount() {
        return m_ballCount;
    }

    public void incrementBallCount() {
        m_ballCount++;
    }

    public void decrementBallCount() {
        m_ballCount--;
    }

    public BallPathState getBallPathState() {
        return m_ballPathState;
    }

    public void setBallPathState(BallPathState state) {
        m_ballPathState = state; 
    }

    public void updateBallPathState() {
        /*
         * A setter method plus some extra logic to decide what to set to.
         * This takes some of the logic out of the default command by letting the
         * subsystem do some of the thinking (which means fewer getters across files)
         * We will not set any motors here; that will be done in the default command
         * We will be setting the ball path state here, however
         */
        if (m_entranceSensor.get()) {
            m_ballPathState = BallPathState.Loading;
        } else if (entranceSensorHasTransitioned()) {
            incrementBallCount();
            m_ballPathState = BallPathState.MovingToPosition;
        } else if (!m_middleSensor.get()) {
            m_ballPathState = BallPathState.Stopped;
        } else if (shooterSensorHasTransitioned()) {
            decrementBallCount();
        }
    }

    private boolean entranceSensorHasTransitioned() {
        /*
         * This applies to the other hasTransitioned methods
         * A sensor has transitioned if it has previously detected a ball, but then
         * doesn't detect a ball
         * We will probably need to add in some private member-level variables to keep
         * track of this
         */
        if (m_ballPreviouslyThere && !m_entranceSensor.get())
            return true;
        else
            return false;
    }

    private boolean middleSensorHasTransitioned() {
        if (!m_ballPreviouslyThere && m_middleSensor.get())
            return true;
        else
            return false;
    }

    private boolean shooterSensorHasTransitioned() {
        if (!m_ballPreviouslyThere && m_shooterSensor.get())
            return true;
        else
            return false;
    }
}
