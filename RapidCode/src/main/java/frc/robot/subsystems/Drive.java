package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
    public enum EncoderType {
        Left,
        Right,
        Average,
        None
    }

    public enum ShifterState {
        Normal,
        Shifted,
        None
    }

    private WPI_TalonFX m_leftPrimary;
    private WPI_TalonFX m_leftSecondary;
    private WPI_TalonFX m_rightPrimary;
    private WPI_TalonFX m_rightSecondary;

    private AHRS m_gyro;

    private Solenoid m_leftShifter;
    private Solenoid m_rightShifter; 

    private double wheelNonLinearity = .6;
    private double negInertia, oldWheel;
    private double sensitivity;
    private double angularPower;
    private double linearPower;

    private PIDController m_drivePID;
    private PIDController m_rotatePID;

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

        m_gyro = new AHRS();

        m_leftShifter = new Solenoid(Constants.CAN.REV_PH_ID, PneumaticsModuleType.REVPH, Constants.PCM.DRIVE_LEFT_SOLENOID);
        m_rightShifter = new Solenoid(Constants.CAN.REV_PH_ID,PneumaticsModuleType.REVPH, Constants.PCM.DRIVE_RIGHT_SOLENOID);

        m_leftSecondary.follow(m_leftPrimary);
        m_rightSecondary.follow(m_rightPrimary);

        m_rightPrimary.setInverted(InvertType.InvertMotorOutput);
        m_rightSecondary.setInverted(InvertType.InvertMotorOutput);

        m_leftPrimary.setNeutralMode(NeutralMode.Brake);
        m_leftSecondary.setNeutralMode(NeutralMode.Brake);
        m_rightPrimary.setNeutralMode(NeutralMode.Brake);
        m_rightSecondary.setNeutralMode(NeutralMode.Brake);

        m_drivePID = new PIDController(Constants.PID.DRIVE_PROPORTIONAL_COMPETITION, Constants.PID.DRIVE_INTEGRAL_COMPETITION, Constants.PID.DRIVE_DERIVATIVE_COMPETITION);
        m_drivePID.setTolerance(Constants.PID.DRIVE_TOLERANCE_COMPETITION);

        m_rotatePID = new PIDController(Constants.PID.ROTATE_PROPORTIONAL_COMPETITION, Constants.PID.ROTATE_INTEGRAL_COMPETITION, Constants.PID.ROTATE_DERIVATIVE_COMPETITION);
        m_rotatePID.setTolerance(Constants.PID.ROTATE_TOLERANCE_COMPETITION);

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

    public void resetEncoders() {
        m_rightPrimary.setSelectedSensorPosition(0);
        m_leftPrimary.setSelectedSensorPosition(0);
    }

    public void resetGyro() {
        m_gyro.reset();
    }

    public void drivePIDInit(double distance, boolean resetEncoders) {
        //distance driving, how much need to rotate, if at all, if reset encoders, reset gyro
        if (resetEncoders) {
            resetEncoders();
        }
        m_drivePID.setSetpoint(distance);
    }

    public void rotatePIDInit(double heading, boolean resetGyro) {
        if (resetGyro) {
            resetGyro();
        }
        m_rotatePID.setSetpoint(heading);
    }

    public void drivePIDExec() {
        arcadeDrive(m_drivePID.calculate(getEncoderPosition(EncoderType.Average)), 0);
    }

    public void rotatePIDExec() {
        arcadeDrive(0, m_rotatePID.calculate(getGyroPosition()));
    }

    public boolean atDrivePIDSetpoint() {
       return m_drivePID.atSetpoint();

    }

    public boolean atRotatePIDSetpoint() {
        return m_rotatePID.atSetpoint();
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
		if (throttle < -Constants.ARCADE_DRIVE_DEADBAND){
			turn *= -1;
		}

		/*if there is no throttle do a zero point turn, or a "quick turn"*/
		if (Math.abs(throttle) < Constants.QUICK_TURN_DEADBAND) {
			lDrive = turn * Constants.QUICK_TURN_MULTIPLIER;
			rDrive = -turn * Constants.QUICK_TURN_MULTIPLIER;
		} else {
			/*if not driving with quick turn then driveTrain with split arcade*/
			lDrive = throttle * (1 + Math.min(0, turn));
			rDrive = throttle * (1 - Math.max(0, turn));
		}

		driveRaw(lDrive, rDrive);
	}

    public double getEncoderPosition(EncoderType measureType) {
        /*
        This method is used to get the position of the robot's encoders
        Since there are times where we might want to look at both left and right encoders as well as only one side, 
        there is an enumeration named EncoderType that is sent in as a parameter named measureType
        Use a switch-case to assign values to return (go through cases with every possible enumeration value for EncoderType)
        Also, make sure to subtract the respective encoder pos variables from the returned value, as that is what allows us to reset the encoders
        Syntax: switch(measureType) {
            case OneState:
                encoderPos = m_leftPrimary.getSelectedSensorPosition();
                break;
            case AnotherState:
                encoderPos = m_rightPrimary.getSelectedSensorPosition();
                break;
            default:
                break;
        }
        At the end, however, we have a bit of extra math to do, since there is gearing between the encoder and the wheels
        On top of that, since there is a high and low gear mode, the gearing for each is different
        So, the amount that we divide by will change based on m_shifterState
        */
        double encoderPos = 0;

        switch(measureType) {
            case Left:
                encoderPos = m_leftPrimary.getSelectedSensorPosition();
                break;
            case Right:
                encoderPos = m_rightPrimary.getSelectedSensorPosition();
                break;
            case Average:
                encoderPos = (m_leftPrimary.getSelectedSensorPosition() + m_rightPrimary.getSelectedSensorPosition()) / 2;
                break;
            default:
                break;
        }

        return (m_shifterState == ShifterState.Normal) ? encoderPos / 5.6 : encoderPos / 16.36;
    }

    public double getGyroPosition() {
        return m_gyro.getAngle();
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
        */
        return m_shifterState;
    }

    public void setShifterState(ShifterState state) {
        /*
        This method changes the state of the solenoid and the value of the member-level variable of type ShifterState 
        to the parameter state
        This method should only be a couple lines!
        */    
        m_shifterState = state;
        // When you add solenoids, make sure to add a line to set them also equal to the shifterState.

        //shifting is inverted. so we invert the set command GH
        if(this.m_shifterState == ShifterState.Normal){
            m_leftShifter.set(false);
            m_rightShifter.set(false);
        }
        else{
            m_leftShifter.set(true);
            m_rightShifter.set(true);
        }

        // old logic that was inverted GH
        // m_leftShifter.set(m_shifterState == ShifterState.Normal);
        // m_rightShifter.set(m_shifterState == ShifterState.Normal);
    }
}
