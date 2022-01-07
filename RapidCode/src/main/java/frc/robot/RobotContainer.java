package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
    }

    public Command getAutonomousCommand() {
        return null;
    }

    private double applyDeadband(double raw, double deadband) {
        double modified = 0.0;

        deadband = Math.abs(deadband);

        if (raw < -deadband)
            modified = ((raw + 1) / (1 - deadband)) - 1;
        else if (raw > deadband)
            modified = ((raw - 1) / (1 - deadband)) + 1;

        return modified;
    }
}
