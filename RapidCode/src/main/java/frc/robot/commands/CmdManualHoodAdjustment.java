package frc.robot.commands;

import frc.robot.subsystems.Shooter.HoodPosition;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class CmdManualHoodAdjustment extends CommandBase {
    Shooter m_shooterSubsystem; // Declare member level variable for the ShooterSubsys. passed in through constructor. 
    HoodPosition m_hoodPosition;  // Declare member level variable for the Hood Angle passed in constructor. 

    public CmdManualHoodAdjustment(Shooter shooterSubsystem, HoodPosition hoodPosition){
        /**
         * Constructor for the CmdManualHoodAngle, takes in the values of shooter subsystem, and
         * the hood position as values that are their respective type. 
         */
        m_shooterSubsystem = shooterSubsystem;  // Sets values that is passed in the the member-level var. 
        m_hoodPosition = hoodPosition;
    }
    
    @Override
    public void initialize(){
        m_shooterSubsystem.setHoodPosition(m_hoodPosition);  // Sets hood position based off value passed in through constructor. 
    }

    // These values are just pretty much a copy of every other command. 

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }   
}
