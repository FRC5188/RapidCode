
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterLookupTable;

public class CmdShooterAdjustSpeed extends CommandBase {
    private ShooterLookupTable m_lookupTable;
    private double m_speedAdjustment;
    private int m_distanceInInches;

    public CmdShooterAdjustSpeed(ShooterLookupTable lookupTable, double speedAdjustment, int distanceInInches) {
        m_lookupTable = lookupTable;
        m_speedAdjustment = speedAdjustment;
        m_distanceInInches = distanceInInches;
    }

    @Override
    public void initialize() {
        m_lookupTable.editVelocityEntry(m_distanceInInches, m_speedAdjustment);
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
