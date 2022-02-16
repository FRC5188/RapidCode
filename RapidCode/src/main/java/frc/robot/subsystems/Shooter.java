package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {
    private WPI_TalonFX m_flywheelLeft;
    private WPI_TalonFX m_flywheelRight;

    private CANSparkMax m_hood;
    private CANSparkMax m_turret;

    private AnalogInput m_hoodPotentiometer;
    private AnalogInput m_turretPotentiometer;

    private PIDController m_hoodPID;
    double m_hoodSetpoint = 0;



    public Shooter() {
        m_flywheelLeft = new WPI_TalonFX(Constants.CAN.LEFT_FLYWHEEL_ID);
        m_flywheelRight = new WPI_TalonFX(Constants.CAN.RIGHT_FLYWHEEL_ID);

        m_flywheelRight.setInverted(InvertType.InvertMotorOutput);

        m_hood = new CANSparkMax(Constants.CAN.HOOD_MOTOR_ID, MotorType.kBrushless);

        m_turret = new CANSparkMax(Constants.CAN.TURRET_MOTOR_ID, MotorType.kBrushless);

        m_hoodPotentiometer = new AnalogInput(Constants.AIO.HOOD_POTENTIOMETER_PORT);
        m_hoodPotentiometer.setAverageBits(2);
        m_hoodPotentiometer.setOversampleBits(0);

        m_turretPotentiometer = new AnalogInput(Constants.AIO.TURRET_POTENTIOMETER_PORT);
        m_turretPotentiometer.setAverageBits(2);
        m_turretPotentiometer.setOversampleBits(0);

        m_hoodPID = new PIDController(0.1, 0, 0);

        m_hoodPID.setTolerance(1);
    }

    @Override
    public void periodic() {
    }

    public void setFlywheelSpeed(double speed){
        m_flywheelLeft.set(speed);
        m_flywheelRight.set(speed);
    }

    public double getFlywheelRPM(){
        //units per 100ms, 2048 units per rotation
        return m_flywheelRight.getSelectedSensorVelocity() * 600 * (1/2048.0);
    }   

    public double getFlywheelSpeedSetpoint() {
        return m_flywheelRight.get() * 6000/*max RPM*/;
    }

    public void setHoodSpeed(double speed){
        m_hood.set(speed);
    }

    public void hoodPIDExecute() {
        m_hood.set(m_hoodPID.calculate(m_hoodPotentiometer.getAverageValue()));
    }

    public void setHoodSetPoint(double setpoint) {
        m_hoodPID.setSetpoint(setpoint);
        m_hoodSetpoint = setpoint;
    }

    public double getHoodSetPoint() {
        return m_hoodPID.getSetpoint();
    }

    public boolean atHoodSetpoint() {
        return m_hoodPID.atSetpoint();
    }

    public double getHoodPotentiometerAngle() {
        return m_hoodPotentiometer.getAverageValue(); //needs to be converted to angle
    }

    public double getTurretPotentiometerAngle() {
        return m_turretPotentiometer.getAverageValue(); //needs to be converted to angle
    }    

}
