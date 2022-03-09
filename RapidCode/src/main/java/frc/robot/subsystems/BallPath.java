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

    private DigitalInput m_entranceSensor;
    private DigitalInput m_middleSensor;
    private DigitalInput m_shooterSensor;

    private int m_ballCount;
    private BallPathState m_ballPathState;

    private boolean m_entranceWasDetected;
    private boolean m_middleWasDetected;
    private boolean m_shooterWasDetected;

    public BallPath() {
        // You need to initialize each member level thing. See example below
        // The ids for each motor/sensor have been made. Find where they are and reference
        
        m_indexMotorTop = new CANSparkMax(Constants.CAN.INDEX_MOTOR_TOP_ID, MotorType.kBrushless);

        m_entranceSensor = new DigitalInput(Constants.DIO.ENTRANCE_SENSOR_PORT);
        m_middleSensor = new DigitalInput(Constants.DIO.MIDDLE_SENSOR_PORT);
        m_shooterSensor = new DigitalInput(Constants.DIO.SHOOTER_SENSOR_PORT);

        m_ballPathState = BallPathState.None;
    }

    @Override
    public void periodic() {

    }

    //negative will take ball up
    public void setMotorSpeed(double speed) {
        m_indexMotorTop.set(speed);
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

    public boolean inBallPath() { 
        // incorrect, fix later
        if (m_ballPathState == BallPathState.None) {
            return false;
        } else {
            return true;
        }
    }

    public void setBallPathState(BallPathState state) {
        m_ballPathState = state; 
    }

    public boolean hasEnteredBallPath() { // For CmdDriveUntilBall command
        // incorrect, fix later
        return m_entranceSensor.get();
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
            m_ballPathState = BallPathState.Loading;
        } else if (entranceSensorHasTransitioned()) {
            incrementBallCount();
            m_ballPathState = BallPathState.MovingToPosition;
        } else if (middleSensorHasTransitioned()) {
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
        boolean isDetected = m_entranceSensor.get();

        if (m_entranceWasDetected && isDetected) {
            m_entranceWasDetected = isDetected;
            return true;
        } else {
            m_entranceWasDetected = isDetected;
            return false;
        }
    }

    private boolean middleSensorHasTransitioned() {
        boolean isDetected = m_middleSensor.get();

        if (m_middleWasDetected && isDetected) {
            m_middleWasDetected = isDetected;
            return true;
        } else {
            m_middleWasDetected = isDetected;
            return false;
        }
    }

    private boolean shooterSensorHasTransitioned() {
        boolean isDetected = m_shooterSensor.get();

        if (m_shooterWasDetected && isDetected) {
            m_shooterWasDetected = isDetected;
            return true;
        } else {
            m_shooterWasDetected = isDetected;
            return false;
        }
    }
}
