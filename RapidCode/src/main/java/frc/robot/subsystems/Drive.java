package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
    public enum ShifterState {
        Normal,
        Shifted,
        None
    }
    private WPI_TalonFX m_leftPrimary;
    private WPI_TalonFX m_leftSecondary;
    private WPI_TalonFX m_rightPrimary;
    private WPI_TalonFX m_rightSecondary;

    private double wheelNonLinearity = .6;
    private double negInertia, oldWheel;
    private double sensitivity;
    private double angularPower;
    private double linearPower;

    private ShifterState m_shifterState;

    public Drive() { 
        /*
        Initialize all objects and variables
        Objects are things like motor controllers, solenoids, and sensors
        Variables are things like important numbers/constants to keep track of

        To initialize an object, use general format: ObjectType objectName = new ObjectType(initParameters)
        If you're giving a port number in the object parameters, reference the Constants class: Constants.PortType.STATIC_VALUE
        */
        m_leftPrimary = new WPI_TalonFX(Constants.CAN.LEFT_PRIMARY_DRIVE_ID);
        m_leftSecondary = new WPI_TalonFX(Constants.CAN.LEFT_SECONDARY_DRIVE_ID);
        m_rightPrimary = new WPI_TalonFX(Constants.CAN.RIGHT_PRIMARY_DRIVE_ID);
        m_rightSecondary = new WPI_TalonFX(Constants.CAN.RIGHT_SECONDARY_DRIVE_ID);

        m_leftSecondary.follow(m_leftPrimary);
        m_rightSecondary.follow(m_rightPrimary);
        m_rightPrimary.setInverted(InvertType.InvertMotorOutput);
        m_rightSecondary.setInverted(InvertType.InvertMotorOutput);

        m_shifterState = ShifterState.None;
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

    public void cheesyDrive(double throttle, double wheel, double quickTurn, boolean shifted) {
        /*
        This code belongs to the poofs
        Please don't touch this code, but feel free to read and ask questions
        */

        negInertia = wheel - oldWheel;
        oldWheel = wheel;

        wheelNonLinearity = (shifted) ? 0.6 : 0.5;

        /*Apply a sine function that's scaled to make it feel better.*/
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);

        sensitivity = (shifted) ? 0.5 : 1.0;

        wheel += negInertia;
        linearPower = throttle;

        angularPower = (quickTurn > 0.5) ? wheel : Math.abs(throttle) * wheel;

        double left = -((linearPower + angularPower) * sensitivity);
        double right = (linearPower - angularPower) * sensitivity;
        driveRaw(left, right);
    }

    public void arcadeDrive(double throttle, double turn) {
        /*Please don't touch this code, but feel free to read and ask questions*/

		double lDrive;
        double rDrive;

        /*reverse turning when driving backwards*/
		if (-throttle < 0){
			turn = turn * -1;
		}

		/*if there is no throttle do a zero point turn, or a "quick turn"*/
		if (Math.abs(throttle) < 0.05) {
			lDrive = -turn * 0.75;
			rDrive = turn * 0.75;
		} else {
			/*if not driving with quick turn then driveTrain with split arcade*/
			lDrive = throttle * (1 + Math.min(0, turn));
			rDrive = throttle * (1 - Math.max(0, turn));
		}

		driveRaw(lDrive, rDrive);
	}

    private void driveRaw(double left, double right) {
	    /*
	    This method is used to supply values to the drive motors
        Variables left and right will be between -1 and 1
        Call the .set(left) for the primary drive motor on the left
        Call the .set(right) for the primary drive motor on the right
	    */
        m_leftPrimary.set(left);
        m_rightPrimary.set(right);
	}

    public ShifterState getShifterState() {
        /*
        This method is used to get the state of the shifter solenoid
        Use a switch-case to assign values to return (go through cases with every possible enumeration value for ShifterState)
        Syntax: switch(m_variableOfTypeShifterState) {
            case OneState:
                returnVariable = ShifterState.OneState;
                break;
            case AnotherState:
                returnVariable = ShifterState.AnotherState;
                break;
            default:
                break;
        }
        */
        return ShifterState.None;
    }

    public void setShifterState(ShifterState state) {
        /*
        This method changes the state of the solenoid and the value of the member-level variable of type ShifterState 
        to the parameter state
        This method should only be a couple lines!
        */
    }
}
