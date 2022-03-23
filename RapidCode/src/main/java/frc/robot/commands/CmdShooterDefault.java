package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterLookupTable;

public class CmdShooterDefault extends CommandBase {
    private Shooter m_shooterSubsystem;
    private ShooterLookupTable m_lookupTable;

    public CmdShooterDefault(Shooter shooterSubsystem, ShooterLookupTable lookupTable) {
        m_shooterSubsystem = shooterSubsystem;
        m_lookupTable = lookupTable;

        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double speed = m_lookupTable.getVelocityAtDistance(m_shooterSubsystem.getCurrentShootingDistance());
        m_shooterSubsystem.setTopFlywheelSpeed(speed);
        m_shooterSubsystem.setBottomFlywheelSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
