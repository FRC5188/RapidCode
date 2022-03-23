package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;

public class CmdShooterAdjustSpeed extends CommandBase {
    private Shooter m_shooterSubsystem;
    private ShooterLookupTable m_lookupTable;
    private double m_addToSpeed;

    public CmdShooterAdjustSpeed(Shooter shooterSubsystem, ShooterLookupTable lookupTable, double addToSpeed) {
        m_lookupTable = lookupTable;
        m_addToSpeed = addToSpeed;
    }

    @Override
    public void initialize() {
        m_lookupTable.editVelocityEntry(m_shooterSubsystem.getCurrentShootingDistance(), m_addToSpeed);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
