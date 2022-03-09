package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private final TalonFX climberRight = new TalonFX(Constants.CAN.CLIMBER_MOTOR_ID_RIGHT); 
  private final TalonFX climberLeft = new TalonFX(Constants.CAN.CLIMBER_MOTOR_ID_LEFT); 
  /** Creates a new climberSubsystem. */
  public Climber() {}

  @Override
  public void periodic() {

  }

  public void climberUp () {
    this.climberRight.set(ControlMode.PercentOutput, -Constants.CLIMBER_SPEED);
    this.climberLeft.set(ControlMode.PercentOutput, -Constants.CLIMBER_SPEED);
  }

  public void climberDown () {
    this.climberRight.set(ControlMode.PercentOutput, Constants.CLIMBER_SPEED);
    this.climberLeft.set(ControlMode.PercentOutput, Constants.CLIMBER_SPEED);
  }
  //checks how much the trigger is pressed down, .2 being 20%, that way we dont accidentally start the climber
  public void climberMove(Double upSpeed, Double downSpeed) {
    if(upSpeed < -.2) {
      this.climberRight.set(ControlMode.PercentOutput, -upSpeed);
      this.climberLeft.set(ControlMode.PercentOutput, -upSpeed);
    } else if (downSpeed < -.2) {
      this.climberRight.set(ControlMode.PercentOutput, downSpeed);
      this.climberLeft.set(ControlMode.PercentOutput, downSpeed);
    }
  }

  public void stop() {
    this.climberRight.set(ControlMode.PercentOutput, 0);
    this.climberLeft.set(ControlMode.PercentOutput, 0);
  }
}