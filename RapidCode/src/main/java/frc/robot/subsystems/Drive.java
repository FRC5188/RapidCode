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
        
    }

    @Override
    public void periodic() {
        
    }

    private void driveRaw(double left, double right) {
	    /*
	       Percent output takes a "speed" from -1 to 1.
	       Follow tells a motor controller to match the speed of another.
	     */
	}

    public void cheesyDrive(double throttle, double wheel, double quickTurn, boolean shifter) {
        /*This code belongs to the poofs*/

        negInertia = wheel - oldWheel;
        oldWheel = wheel;

        wheelNonLinearity = 0.5;
        //wheelNonLinearity = shifter ? 0.6:0.5;

        /*Apply a sin function that's scaled to make it feel better.*/
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel)
                / Math.sin(Math.PI / 2.0 * wheelNonLinearity);

        if(shifter){
            sensitivity = .5;
        } else {
            sensitivity = 1;
        }

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
}
