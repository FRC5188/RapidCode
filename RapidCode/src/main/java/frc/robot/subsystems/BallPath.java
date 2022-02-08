package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BallPath extends SubsystemBase {

    public enum BallPathState {
        Loading,
        MovingToPosition,
        Stopped,
        Shooting,
        None
    }

    //need 2 neos and 3 light sensors

    private int m_ballCount;
    private BallPathState m_ballPathState;

    public BallPath() {

    }

    @Override
    public void periodic() {

    }

    public int getBallCount() {
        return m_ballCount;
    }

    public void incrementBallCount() {
        m_ballCount++;
    }

    public BallPathState getBallPathState() {
        return BallPathState.None;
    }

    public void updateBallPathState() {
        /*
        A setter method plus some extra logic to decide what to set to.
        This takes some of the logic out of the default command by letting the subsystem do some of the thinking (which means fewer getters across files)
        We will not set any motors here; that will be done in the default command
        We will be setting the ball path state here, however
        */
    }

    private boolean entranceSensorHasTransitioned() {
        /*
        This applies to the other hasTransitioned methods
        A sensor has transitioned if it has previously detected a ball, but then doesn't detect a ball
        We will probably need to add in some private member-level variables to keep track of this
        */
        return false;
    }

    private boolean middleSensorHasTransitioned() {
        return false;
    }

    private boolean shooterSensorHasTransitioned() {
        return false;
    }
}
