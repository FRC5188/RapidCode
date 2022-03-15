package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
    private Dashboard m_dashboard;

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

    private PIDController m_drivePID;
    private PIDController m_rotatePID;
    private double m_drivePIDMaxSpeed;
    private double m_rotatePIDMaxSpeed;

    private ShifterState m_shifterState;

    /**
     * Creates a new instance of the Drive subsystem
     * @param dashboard
     */
    public Drive(Dashboard dashboard) { 
        m_dashboard = dashboard;

        m_leftPrimary = new WPI_TalonFX(Constants.CAN.LEFT_PRIMARY_DRIVE_ID);
        m_leftSecondary = new WPI_TalonFX(Constants.CAN.LEFT_SECONDARY_DRIVE_ID);
        m_rightPrimary = new WPI_TalonFX(Constants.CAN.RIGHT_PRIMARY_DRIVE_ID);
        m_rightSecondary = new WPI_TalonFX(Constants.CAN.RIGHT_SECONDARY_DRIVE_ID);

        m_leftPrimary.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_leftSecondary.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_rightPrimary.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_rightSecondary.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

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
        m_dashboard.setIsDriveShifted(m_shifterState == ShifterState.Shifted);
    }

    /**
     * Resets the encoders on the drive motors to 0
     */
    public void resetEncoders() {
        m_rightPrimary.setSelectedSensorPosition(0);
        m_leftPrimary.setSelectedSensorPosition(0);
    }

    /**
     * resets the gyro angle to 0
     */
    public void resetGyro() {
        m_gyro.reset();
    }

    /**
     * Initializes the PID controller for driving forwards or backwards
     * @param distance distance, in inches, to drive
     * @param resetEncoders true to reset encoders, false if no
     * @param speed the max speed to drive, in percent output
     */
    public void drivePIDInit(double distance, boolean resetEncoders, double speed) {
        if (resetEncoders) resetEncoders();

        m_drivePID.setSetpoint(distance);
        m_drivePIDMaxSpeed = speed;
    }

    /**
     * Initializes the PID controller for rotating the drivebase
     * @param heading the desired angle to rotate to, in degrees. Clockwise is positive, counterclockwise is negative
     * @param speed the max speed to rotate, in percent output
     * @param resetGyro true to reset gyro, false if no
     */
    public void rotatePIDInit(double heading, double speed, boolean resetGyro) {
        if (resetGyro) resetGyro();

        m_rotatePID.setSetpoint(heading);
        m_rotatePIDMaxSpeed = speed;
    }

    /**
     * Executes the driving PID controller
     */
    public void drivePIDExec() {
        double position = getEncoderPosition(EncoderType.Average);
        double power = m_drivePID.calculate(position) * m_drivePIDMaxSpeed;

        driveRaw(power, power);
    }

    /**
     * Executes the rotating PID controller
     */
    public void rotatePIDExec() {
        double angle = getGyroPosition();
        double power = m_rotatePID.calculate(angle) * m_rotatePIDMaxSpeed;

        driveRaw(power, -power);
    }

    /**
     * Checks if the driving PID is at its setpoint
     * @return true if at setpoint, false otherwise
     */
    public boolean atDrivePIDSetpoint() {
       return m_drivePID.atSetpoint();

    }

    /**
     * Checks if the rotating PID is at its setpoint
     * @return true if at setpoint, false otherwise
     */
    public boolean atRotatePIDSetpoint() {
        return m_rotatePID.atSetpoint();
    }

    /**
     * Applies a split arcade drive to the drive base
     * @param throttle the speed to go forward or back, in percent output
     * @param turn the speed to rotate, in percent output
     */
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

    /**
     * Gets the current average position of the left and right encoders
     * @return 
     * Gets the current average position of the left and right encoders
     */
    public double getEncoderPosition(){
        return getEncoderPosition(EncoderType.Average);
    }

    /**
     * Gets the current position of the encoders, depending on what type of measuring is inputted
     * @param measureType the type of measuring to be done. Left will return the left encoder only, 
     * Right will return the right encoder only, and Average will return the average of both
     * @return the current position of the encoders, depending on what type of measuring is inputted
     */
    public double getEncoderPosition(EncoderType measureType) {
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

        double encoderRevs = encoderPos / 2048;
        return encoderRevs / 5.6 * Math.PI*4;
    }

    /**
     * Gets the current angle of the gyro, in degrees
     * @return the current angle of the gyro, in degrees
     */
    public double getGyroPosition() {
        return m_gyro.getAngle();
    }

    /**
     * Sets the speed of the drive motors
     * @param left the speed for the left side
     * @param right the speed for the right side
     */
    public void driveRaw(double left, double right) {
        m_leftPrimary.set(left);
        m_rightPrimary.set(right);
	}

    /**
     * Gets the current state of the gearbox shifter
     * @return the current state of the gearbox shifter
     */
    public ShifterState getShifterState() {
        return m_shifterState;
    }

    /**
     * Sets the state of the gearbox shifter
     * @param state the desired state of the gearbox shifter
     */
    public void setShifterState(ShifterState state) {
        m_shifterState = state;

        m_leftShifter.set(m_shifterState == ShifterState.Shifted);
        m_rightShifter.set(m_shifterState == ShifterState.Shifted);
    }
}
