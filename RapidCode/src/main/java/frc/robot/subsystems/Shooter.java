// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// Current Goal: full send motors when button pressed

public class Shooter extends SubsystemBase {
    //adding motor controllers for falcon shooter, sparkmax hood/turret, analog hood/turret potentiometers, and hood PID
    private WPI_TalonFX m_flywheelLeft;
    private WPI_TalonFX m_flywheelRight;

    private CANSparkMax m_hood;
    private CANSparkMax m_turret;

    private AnalogInput m_hoodPotentiometer;
    private AnalogInput m_turretPotentiometer;

    private PIDController m_hoodPID;


    public Shooter() {
        m_flywheelLeft = new WPI_TalonFX(Constants.CAN.LEFT_FLYWHEEL_ID);
        m_flywheelRight = new WPI_TalonFX(Constants.CAN.RIGHT_FLYWHEEL_ID);

        m_flywheelRight.setInverted(InvertType.InvertMotorOutput);

        m_hood = new CANSparkMax(Constants.CAN.HOOD_MOTOR_ID, MotorType.kBrushless);

        m_turret = new CANSparkMax(Constants.CAN.TURRET_MOTOR_ID, MotorType.kBrushless);

        m_hoodPotentiometer = new AnalogInput(Constants.AIO.HOOD_POTENTIOMETER_PORT);
        m_turretPotentiometer = new AnalogInput(Constants.AIO.TURRET_POTENTIOMETER_PORT);

        m_hoodPID = new PIDController(0.1, 0, 0);

    }

    @Override
    public void periodic() {
    }

    public void setFlywheelSpeed(double speed){
        m_flywheelLeft.set(speed);
        m_flywheelRight.set(speed);
    }

    public double getFlywheelSpeedSetpoint() {
        return m_flywheelRight.get() * 6000/*max RPM*/;
    }

    public double getFlywheelRPM(){
        //units per 100ms, 2048 units per rotation
        return m_flywheelRight.getSelectedSensorVelocity() * 600 * (1/2048.0);
    }   

    public double getHoodPotentiometerAngle() {
        return m_hoodPotentiometer.getVoltage(); //needs to be converted to angle
    }

    public double getTurretPotentiometerAngle() {
        return m_turretPotentiometer.getVoltage(); //needs to be converted to angle
    }
    

}
