
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterLookupTable;

public class CmdShooterAdjustSpeed extends CommandBase {
    private ShooterLookupTable m_lookupTable;
    private double m_speedAdjustment;
    public CmdShooterAdjustSpeed(ShooterLookupTable lookupTable, double speedAdjustment) {

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
