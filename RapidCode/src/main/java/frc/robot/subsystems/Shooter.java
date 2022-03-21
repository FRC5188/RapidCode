package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {
    public enum HoodPosition {
        Fender,
        Far
    }

    private Dashboard m_dashboard;

    private WPI_TalonFX m_flywheelTop;
    private WPI_TalonFX m_flywheelBottom;
    private CANSparkMax m_hoodMotor;
    private CANSparkMax m_turretMotor;
    private CANSparkMax m_acceleratorMotor;

    // private AnalogInput m_hoodPotentiometer;
    private AnalogInput m_turretPotentiometer;

    private PIDController m_hoodPID;
    private PIDController m_turretPID;
    private double m_hoodSetpoint;
    private double m_turretSetpoint;

    private double m_shooterSpeed;

    private double m_bottomFlywheelSpeedSetpoint;
    private double m_topFlywheelSpeedSetpoint;

    private boolean m_readyToShoot;

    private double m_count;

    private Vision m_v;

    /**
     * Creates a new Shooter subsystem
     * @param dashboard the dashboard instance for the robot
     */
    public Shooter(Dashboard dashboard, Vision v) {


        m_dashboard = dashboard;

        m_flywheelTop = new WPI_TalonFX(Constants.CAN.LEFT_FLYWHEEL_ID);
        m_flywheelBottom = new WPI_TalonFX(Constants.CAN.RIGHT_FLYWHEEL_ID);
        m_flywheelTop.setNeutralMode(NeutralMode.Coast);
        m_flywheelBottom.setNeutralMode(NeutralMode.Coast);
        // m_flywheelBottom.setInverted(true);
        // m_flywheelTop.setInverted(true);

        m_hoodMotor = new CANSparkMax(Constants.CAN.HOOD_MOTOR_ID, MotorType.kBrushless);
        m_hoodMotor.setIdleMode(IdleMode.kBrake);

        m_turretMotor = new CANSparkMax(Constants.CAN.TURRET_MOTOR_ID, MotorType.kBrushless);
        m_turretMotor.setIdleMode(IdleMode.kBrake);
        m_turretMotor.set(0);

        m_acceleratorMotor = new CANSparkMax(Constants.CAN.ACCEL_MOTOR_ID, MotorType.kBrushless);
        m_acceleratorMotor.setIdleMode(IdleMode.kBrake); 
        m_acceleratorMotor.setInverted(true);

        // m_hoodPotentiometer = new AnalogInput(Constants.AIO.HOOD_POTENTIOMETER_PORT);
        // m_hoodPotentiometer.setAverageBits(2);
        // m_hoodPotentiometer.setOversampleBits(0);

        m_turretPotentiometer = new AnalogInput(Constants.AIO.TURRET_POTENTIOMETER_PORT);
        m_turretPotentiometer.setAverageBits(2);
        m_turretPotentiometer.setOversampleBits(0);

        m_hoodPID = new PIDController(Constants.PID.HOOD_PROPORTIONAL, Constants.PID.HOOD_INTEGRAL, Constants.PID.HOOD_DERIVATIVE); 
        m_hoodPID.setTolerance(Constants.PID.HOOD_TOLERANCE);

        m_turretPID = new PIDController(Constants.PID.TURRET_PROPORTIONAL, Constants.PID.TURRET_INTEGRAL, Constants.PID.TURRET_DERIVATIVE);
        m_turretPID.setTolerance(Constants.PID.TURRET_TOLERANCE);

        m_hoodSetpoint = 0;
        m_turretSetpoint = 0;

        m_shooterSpeed = 0;

        m_readyToShoot = false;

        m_count = 0;

        m_v = v;
    }

    @Override
    public void periodic() {
        m_dashboard.setReadyToShoot(m_readyToShoot);
        //System.out.println("Real: " + getBottomFlywheelRPM() + " Setpoint: " + (m_bottomFlywheelSpeedSetpoint - Constants.FLYWHEEL_SPEED_TOLERANCE));
        if (m_count % 25 == 0) {
            //System.out.println("Distance: " + m_v.getDistanceToTarget() + " Velocity: " + m_flywheelBottom.get() + " Hood Angle: " + getHoodPotentiometerAngle());

        }
    }
    
    /**
     * Sets the speed, in percent output, of the top flywheel motor
     * @param speed the desired speed, in percent output, of the motor
     */
    public void setTopFlywheelSpeed(double speed){
        m_flywheelTop.set(speed);
        m_topFlywheelSpeedSetpoint = speed * 6000;
    }

     /**
     * Sets the speed, in percent output, of the bottom flywheel motor
     * @param speed the desired speed, in percent output, of the motor
     */
    public void setBottomFlywheelSpeed(double speed){
        m_flywheelBottom.set(speed);
        m_bottomFlywheelSpeedSetpoint = speed * 6000;
    }

    /**
     * Gets the current speed, in percent output, of the bottom flywheel motor
     * @return the current speed, in percent output, of the bottom flywheel motor
     */
    public double getBottomFlywheelSpeed() {
        return m_flywheelBottom.get();
    }

    /**
     * Gets the current speed, in RPM, of the top flywheel speed
     * @return the current RPM of the top flywheel motor
     */
    public double getTopFlywheelRPM(){
        //units per 100ms, 2048 units per rotation
        return m_flywheelTop.getSelectedSensorVelocity() * 600 * (1/2048.0);
    } 

    /**
     * Gets the current speed, in RPM, of the bottom flywheel speed
     * @return the current RPM of the bottom flywheel motor
     */
    public double getBottomFlywheelRPM(){
        //units per 100ms, 2048 units per rotation
        return m_flywheelBottom.getSelectedSensorVelocity() * 600 * (1/2048.0);
    }   

    /**
     * Gets the current setpoint, in RPM, of the top flywheel
     * @return the current setpoint, in RPM, of the top flywheel
     */
    public double getTopFlywheelSpeedSetpoint() {
        return m_topFlywheelSpeedSetpoint;
    }

    /**
     * Gets the current setpoint, in RPM, of the bottom flywheel
     * @return the current setpoint, in RPM, of the bottom flywheel
     */
    public double getBottomFlywheelSpeedSetpoint() {
        return m_bottomFlywheelSpeedSetpoint;
    }

    /**
     * Checks if both flywheels are at the speed they were set to
     * @return true if the flywheels are at speed. False if not
     */
    public boolean flywheelsAtSpeed() {
        return (getBottomFlywheelRPM() >= m_bottomFlywheelSpeedSetpoint - Constants.FLYWHEEL_SPEED_TOLERANCE) && (getTopFlywheelRPM() >= m_topFlywheelSpeedSetpoint - Constants.FLYWHEEL_SPEED_TOLERANCE);
    }

    /**
     * Executes the PID controller for the hood motor
     */
    // public void hoodPIDExecute() {
    //     setHoodSpeed(m_hoodPID.calculate(m_hoodPotentiometer.getAverageValue()) * Constants.PID.HOOD_MAX_SPEED);
    // }

    /**
     * Sets the setpoint for the hood motor's PID controller
     * @param setpoint the desired setpoint, in raw potentiometer values, for the hood
     */
    public void setHoodSetPoint(double setpoint) {
        if (setpoint > Constants.HIGH_POT_STOP) {
            setpoint = Constants.HIGH_POT_STOP;
        } else if (setpoint < Constants.LOW_POT_STOP) {
            setpoint = Constants.LOW_POT_STOP;
        } 
        
        m_hoodPID.setSetpoint(setpoint);
        m_hoodSetpoint = setpoint;
    }

    /**
     * Gets the setpoint of the hood motor's PID controller
     * @return the setpoint of the hood motor's PID controller
     */
    public double getHoodSetPoint() {
        return m_hoodSetpoint;
    }

    /**
     * Checks if the hood motor has reached its setpoint
     * @return true if the hood motor is at its setpoint. False otherwise
     */
    public boolean atHoodSetpoint() {
        // Sets target angle for the hood
        return m_hoodPID.atSetpoint();
    }

    /**
     * Gets the current angle of the hood, in raw potentiometer values
     * @return the current angle of the hood, in raw potentiometer values
     */
    // public double getHoodPotentiometerAngle() {
    //     return m_hoodPotentiometer.getAverageValue(); 
    // }

    /**
     * Sets the speed of the hood motor
     * @param speed the desired speed of the motor
     */
    // public void setHoodSpeed(double speed) {
    //     // if ((getHoodPotentiometerAngle() >= Constants.HIGH_POT_STOP && speed > 0) || (getHoodPotentiometerAngle() <= Constants.LOW_POT_STOP && speed < 0)) {
    //     //     m_hoodMotor.set(0);
    //     // } else {
    //     //     m_hoodMotor.set(speed);
    //     // }
    // }

    /**
     * Gets the current position of the turret, in raw potentiometer values
     * @return the current position of the turret, in raw potentiometer values
     */
    public double getTurretPotentiometerAngle() {
        return m_turretPotentiometer.getAverageValue(); 
    }    

    /**
     * Sets the speed of the turret motor
     * @param speed the desired speed of the turret motor
     */
    public void setTurretSpeed(double speed) {
        m_turretMotor.set(speed);
    }

    /**
     * Sets the setpoint for the turret motor's PID controller
     * @param setpoint the desired setpoint for the turret motor's PID controller
     */
    public void setTurretSetpoint(double setpoint){
        m_turretSetpoint = setpoint;
        m_turretPID.setSetpoint(setpoint);
    }

    /**
     * Gets the setpoint for the turret motor's PID controller
     * @return the setpoint for the turret motor's PID controller
     */
    public double getTurretSetpoint() {
        return m_turretSetpoint;
    }

    /**
     * Executes the PID for the turret
     */
    public void turretPIDExecute() {
        m_turretMotor.set(m_turretPID.calculate(m_turretPotentiometer.getAverageValue()));
    }

    /**
     * Checks if the turret motor's PID is at its setpoint
     * @return true if the turret motor is at its setpoint. False otherwise
     */
    public boolean atTurretSetpoint() {
        return m_turretPID.atSetpoint();
    }
    
    /**
     * Sets the speed of the accelerator motor
     * @param speed the desired speed of the accelerator motor
     */
    public void setAcceleratorSpeed(double speed) { 
        m_acceleratorMotor.set(speed);
    }

    //TO BE REMOVED AFTER TESTING
    public void addToSpeed(double speed) {
        m_shooterSpeed += speed;
    }

    //TO BE REMOVED AFTER TESTING
    public double getShooterSpeed() {
        return m_shooterSpeed;
    }

    /**
     * Updates the ready to shoot value for the dashboard
     * @param ready true if the shooter subsystem is aligned and ready to shoot. False otherwise
     */
    public void setReadyToShoot(boolean ready) {
        m_readyToShoot = ready;
    }
}
