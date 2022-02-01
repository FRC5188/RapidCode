package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
    public enum ShifterState {
        Normal,
        Shifted,
        None
    }

    public enum EncoderType {
        Left,
        Right,
        Average,
        None
    }
    private final DifferentialDriveOdometry m_odometry;
    private final DifferentialDrive m_drive;

    private WPI_TalonFX m_leftPrimary;
    private WPI_TalonFX m_leftSecondary;
    private WPI_TalonFX m_rightPrimary;
    private WPI_TalonFX m_rightSecondary;

    private AHRS m_gyro;

    private double wheelNonLinearity = .6;
    private double negInertia, oldWheel;
    private double sensitivity;
    private double angularPower;
    private double linearPower;

    private double m_leftEncoderResetPos;
    private double m_rightEncoderResetPos;

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

        m_leftPrimary.setNeutralMode(NeutralMode.Brake);
        m_leftSecondary.setNeutralMode(NeutralMode.Brake);
        m_rightPrimary.setNeutralMode(NeutralMode.Brake);
        m_rightSecondary.setNeutralMode(NeutralMode.Brake);

        m_gyro = new AHRS();

        m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
        m_drive = new DifferentialDrive(m_leftPrimary, m_rightPrimary);

        m_leftEncoderResetPos = 0;
        m_rightEncoderResetPos = 0;

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
        m_odometry.update(m_gyro.getRotation2d(), getEncoderPosition(EncoderType.Left) * (Math.PI * Units.inchesToMeters(4)), getEncoderPosition(EncoderType.Right) * (Math.PI * Units.inchesToMeters(4)));
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
    
    public DifferentialDriveWheelSpeeds getDiffWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getEncoderVelocity(EncoderType.Left), getEncoderVelocity(EncoderType.Left));
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
        Since I'm nice, I've already made that last bit of math for you, so only focus on the switch-case statement
        */
        double encoderPos = 0;

        return (m_shifterState == ShifterState.Shifted) ? encoderPos / 5.6 : encoderPos / 16.36;
    }

    public double getEncoderVelocity(EncoderType measureType) {
        /*
        This method is used to get the position of the robot's encoders
        Since there are times where we might want to look at both left and right encoders as well as only one side, 
        there is an enumeration named EncoderType that is sent in as a parameter named measureType
        Use a switch-case to assign values to return (go through cases with every possible enumeration value for EncoderType)
        Syntax: switch(measureType) {
            case OneState:
                encoderVel = m_leftPrimary.getSelectedSensorVelocity();
                break;
            case AnotherState:
                encoderVel = m_leftPrimary.getSelectedSensorVelocity();
                break;
            default:
                break;
        }
        At the end, however, we have a bit of extra math to do, since there is gearing between the encoder and the wheels
        On top of that, since there is a high and low gear mode, the gearing for each is different
        So, the amount that we divide by will change based on m_shifterState
        Since I'm nice, I've already made that last bit of math for you, so only focus on the switch-case statement
        */
        double encoderVel = 0;

        return (m_shifterState == ShifterState.Shifted) ? encoderVel / 5.6 : encoderVel / 16.36;
    }

    public double getHeading() {
        return m_gyro.getRotation2d().getDegrees();
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
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
        switch (m_shifterState) {
            case Normal:
                m_shifterState = ShifterState.Normal;
                break;
            case Shifted:
                m_shifterState = ShifterState.Shifted;
                break;
            case None:
            default:
                m_shifterState = ShifterState.None;
                break;

        }
        return m_shifterState;
    }

    public void resetEncoders() {
        m_leftEncoderResetPos = getEncoderPosition(EncoderType.Left);
        m_rightEncoderResetPos = getEncoderPosition(EncoderType.Right);
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        m_odometry.resetPosition(pose, m_gyro.getRotation2d());
    }

    public void setShifterState(ShifterState state) {
        /*
        This method changes the state of the solenoid and the value of the member-level variable of type ShifterState 
        to the parameter state
        This method should only be a couple lines!
        */
        m_shifterState = state;
        // When you add solenoids, make sure to add a line to set them also equal to the shifterState.
    }

    public void tankDriveVolts(double leftVolts, double rightVolts) {
        m_leftPrimary.setVoltage(leftVolts);
        m_leftSecondary.setVoltage(leftVolts);
        m_rightPrimary.setVoltage(rightVolts);
        m_rightSecondary.setVoltage(rightVolts);
        m_drive.feed();
      }
}
