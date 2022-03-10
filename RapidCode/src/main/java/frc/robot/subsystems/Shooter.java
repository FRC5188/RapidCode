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
    private WPI_TalonFX m_flywheelTop;
    private WPI_TalonFX m_flywheelBottom;
    // Describe your objects better, aka don't call it hood, I don't know if its a motor or a sensor
    private CANSparkMax m_hoodMotor;
    private CANSparkMax m_turretMotor;

    private AnalogInput m_hoodPotentiometer;
    private AnalogInput m_turretPotentiometer;

    private PIDController m_hoodPID;
    private PIDController m_turretPID;
    // Make sure to add in the scope of the thing you declare here (private or public)
    // Preferably, don't give anything a value in here until the constructor. Only constants should get a value up here
    private double m_hoodSetpoint;
    private double m_turretSetpoint;


    public Shooter() {
        m_flywheelTop = new WPI_TalonFX(Constants.CAN.LEFT_FLYWHEEL_ID);
        m_flywheelBottom = new WPI_TalonFX(Constants.CAN.RIGHT_FLYWHEEL_ID);
        m_flywheelTop.setNeutralMode(NeutralMode.Coast);
        m_flywheelBottom.setNeutralMode(NeutralMode.Coast);
        m_flywheelBottom.setInverted(InvertType.InvertMotorOutput);

        m_hoodMotor = new CANSparkMax(Constants.CAN.HOOD_MOTOR_ID, MotorType.kBrushless);
        m_hoodMotor.setIdleMode(IdleMode.kBrake);

        m_turretMotor = new CANSparkMax(Constants.CAN.TURRET_MOTOR_ID, MotorType.kBrushless);
        m_turretMotor.setIdleMode(IdleMode.kBrake);

        m_hoodPotentiometer = new AnalogInput(Constants.AIO.HOOD_POTENTIOMETER_PORT);
        m_hoodPotentiometer.setAverageBits(2);
        m_hoodPotentiometer.setOversampleBits(0);

        m_turretPotentiometer = new AnalogInput(Constants.AIO.TURRET_POTENTIOMETER_PORT);
        m_turretPotentiometer.setAverageBits(2);
        m_turretPotentiometer.setOversampleBits(0);

        m_hoodPID = new PIDController(Constants.PID.HOOD_PROPORTIONAL, Constants.PID.HOOD_INTEGRAL, Constants.PID.HOOD_DERIVATIVE); 
        m_hoodPID.setTolerance(Constants.PID.HOOD_TOLERANCE);

        m_turretPID = new PIDController(Constants.PID.TURRET_PROPORTIONAL, Constants.PID.TURRET_INTEGRAL, Constants.PID.TURRET_DERIVATIVE);
        m_turretPID.setTolerance(Constants.PID.TURRET_TOLERANCE);

        m_hoodSetpoint = 0;
        m_turretSetpoint = 0;
    }

    @Override
    public void periodic() {
    }

    public void setTopFlywheelSpeed(double speed){
        m_flywheelTop.set(speed);
    }

    public void setBottomFlywheelSpeed(double speed){
        m_flywheelBottom.set(speed);
    }

    public double getFlywheelRPM(){
        //units per 100ms, 2048 units per rotation
        return m_flywheelBottom.getSelectedSensorVelocity() * 600 * (1/2048.0);
    }   

    public double getFlywheelSpeedSetpoint() {
        return m_flywheelBottom.get() * 6000/*max RPM*/;
    }

    public void hoodPIDExecute() {
        m_hoodMotor.set(m_hoodPID.calculate(m_hoodPotentiometer.getAverageValue()));
    }

    public void setHoodSetPoint(double setpoint) {
        m_hoodPID.setSetpoint(setpoint);
        m_hoodSetpoint = setpoint;
    }

    public double getHoodSetPoint() {
        // Remember we have a variable to keep track of this rather than calling a method
        // Calling a method takes much longer than calling a variable, so always opt to use a stored value than to call it for a get
        return m_hoodSetpoint;
    }

    public boolean atHoodSetpoint() {
        // Sets target angle for the hood
        return m_hoodPID.atSetpoint();
    }

    public double getHoodPotentiometerAngle() {
        return m_hoodPotentiometer.getAverageValue(); //needs to be converted to angle
    }

    public void setHoodSpeed(double speed) {
        m_hoodMotor.set(speed);
    }

    public double getTurretPotentiometerAngle() {
        return m_turretPotentiometer.getAverageValue(); //needs to be converted to angle
    }    

    public void setTurretSpeed(double speed) {
        m_turretMotor.set(speed);
    }

    public void setTurretSetpoint(double setpoint){
        m_turretSetpoint = setpoint;
        m_turretPID.setSetpoint(setpoint);
    }

    public double getTurretSetpoint() {
        return m_turretSetpoint;
    }

    public void turretPIDExecute() {
        m_turretMotor.set(m_turretPID.calculate(m_turretPotentiometer.getAverageValue()));
    }

    public boolean atTurretSetpoint() {
        return m_turretPID.atSetpoint();
    }

}
