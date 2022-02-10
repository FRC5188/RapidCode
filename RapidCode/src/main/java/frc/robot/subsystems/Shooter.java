// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// Current Goal: full send motors when button pressed

public class Shooter extends SubsystemBase {
    //adding motor controllers for falcon
    private WPI_TalonFX m_shooterLeft;
    private WPI_TalonFX m_shooterRight;

    public Shooter() {
        m_shooterLeft = new WPI_TalonFX(Constants.CAN.LEFT_SHOOTER_ID);
        m_shooterRight = new WPI_TalonFX(Constants.CAN.RIGHT_SHOOTER_ID);

        m_shooterRight.setInverted(InvertType.InvertMotorOutput);
    }

    @Override
    public void periodic() {
    }

    public void shoot() {
        m_shooterLeft.set(1);
        m_shooterRight.set(1);
    }
}
