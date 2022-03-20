package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
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
    private CANSparkMax m_turretMotor;
    private CANSparkMax m_acceleratorMotor;

    private Solenoid m_hoodSolenoid;
    private HoodPosition m_hoodPosition;

    private AnalogInput m_turretPotentiometer;

    private PIDController m_turretPID;
    private double m_turretSetpoint;

    private double m_bottomFlywheelSpeedSetpoint;
    private double m_topFlywheelSpeedSetpoint;

    private boolean m_readyToShoot;

    /**
     * Creates a new Shooter subsystem
     * @param dashboard the dashboard instance for the robot
     */
    public Shooter(Dashboard dashboard) {
        m_dashboard = dashboard;

        m_flywheelTop = new WPI_TalonFX(Constants.CAN.LEFT_FLYWHEEL_ID);
        m_flywheelBottom = new WPI_TalonFX(Constants.CAN.RIGHT_FLYWHEEL_ID);
        m_flywheelTop.setNeutralMode(NeutralMode.Coast);
        m_flywheelBottom.setNeutralMode(NeutralMode.Coast);

        m_hoodSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.PCM.HOOD_SOLENOID);

        m_turretMotor = new CANSparkMax(Constants.CAN.TURRET_MOTOR_ID, MotorType.kBrushless);
        m_turretMotor.setIdleMode(IdleMode.kBrake);

        m_acceleratorMotor = new CANSparkMax(Constants.CAN.ACCEL_MOTOR_ID, MotorType.kBrushless);
        m_acceleratorMotor.setIdleMode(IdleMode.kBrake); 
        m_acceleratorMotor.setInverted(true);

        m_turretPotentiometer = new AnalogInput(Constants.AIO.TURRET_POTENTIOMETER_PORT);
        m_turretPotentiometer.setAverageBits(2);
        m_turretPotentiometer.setOversampleBits(0);

        m_turretPID = new PIDController(Constants.PID.TURRET_PROPORTIONAL, Constants.PID.TURRET_INTEGRAL, Constants.PID.TURRET_DERIVATIVE);
        m_turretPID.setTolerance(Constants.PID.TURRET_TOLERANCE);

        m_turretSetpoint = 0;

        m_readyToShoot = false;
    }

    @Override
    public void periodic() {
        m_dashboard.setReadyToShoot(m_readyToShoot);
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

    public HoodPosition getHoodPosition() {
        return m_hoodPosition;
    }

    public void setHoodPosition(HoodPosition position) {
        m_hoodPosition = position;
        m_hoodSolenoid.set(m_hoodPosition == HoodPosition.Far);
    }

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

    /**
     * Updates the ready to shoot value for the dashboard
     * @param ready true if the shooter subsystem is aligned and ready to shoot. False otherwise
     */
    public void setReadyToShoot(boolean ready) {
        m_readyToShoot = ready;
    }
}
