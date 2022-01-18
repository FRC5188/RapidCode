package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
    private double wheelNonLinearity = .6;
    private double negInertia, oldWheel;
    private double sensitivity;
    private double angularPower;
    private double linearPower;

    public Drive() { 
        /*
        Initialize all objects and variables
        Objects are things like motor controllers, solenoids, and sensors
        Variables are things like important numbers/constants to keep track of

        To initialize an object, use general format: ObjectType objectName = new ObjectType(initParameters)
        If you're giving a port number in the object parameters, reference the Constants class: Constants.PortType.STATIC_VALUE
        */
    }

    @Override
    public void periodic() {
        /*
        A method that loops every 20ms
        Use this to update important values
        Try and avoid using this method; rather, use a command
        Ask mentors before putting things in here!
        */
    }

    private void driveRaw(double left, double right) {
	    /*
	    This method is used to supply values to the drive motors
        Variables left and right will be between -1 and 1
        Call the .set(left) for the primary drive motor on the left
        Call the .set(right) for the primary drive motor on the right
	    */
	}

    public void cheesyDrive(double throttle, double wheel, double quickTurn, boolean shifted) {
        /*
        This code belongs to the poofs
        Please don't touch this code, but feel free to read and ask questions
        */

        negInertia = wheel - oldWheel;
        oldWheel = wheel;

        wheelNonLinearity = (shifted) ? 0.6 : 0.5;

        /*Apply a sine function that's scaled to make it feel better.*/
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / Math.sin(Math.PI / 2.0 * wheelNonLinearity);

        sensitivity = (shifted) ? 0.5 : 1.0;

        wheel += negInertia;
        linearPower = throttle;

        if (quickTurn > 0.5){
            angularPower = wheel;
        } else {
            angularPower = Math.abs(throttle) * wheel;
        }

        double left = -((linearPower + angularPower) * sensitivity);
        double right = (linearPower - angularPower) * sensitivity;
        driveRaw(left, right);
    }

    public void driveArcade(double throttle, double turn, boolean shifter) {
        /*Please don't touch this code, but feel free to read and ask questions*/

	    /*shifter multiplier*/
		double shiftVal = shifter ? 1 : 0.5;

		double lDrive;
        double rDrive;

        /*reverse turning when driving backwards*/
		if (-throttle < 0){
			turn = turn * -1;
		}

		/*if there is no throttle do a zero point turn, or a "quick turn"*/
		if (Math.abs(throttle) < 0.05) {
			lDrive = -turn * shiftVal * 0.75;
			rDrive = turn * shiftVal * 0.75;
		} else {
			/*if not driving with quick turn then driveTrain with split arcade*/
			lDrive = shiftVal * throttle * (1 + Math.min(0, turn));
			rDrive = shiftVal * throttle * (1 - Math.max(0, turn));
		}

		driveRaw(lDrive, rDrive);
	}
}
