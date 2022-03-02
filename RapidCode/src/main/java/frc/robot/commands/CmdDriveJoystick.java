package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class CmdDriveJoystick extends CommandBase {
    Drive m_driveSubsystem;
    DoubleSupplier m_drive;
    DoubleSupplier m_rotate;

    public CmdDriveJoystick(Drive driveSubsystem, DoubleSupplier drive, DoubleSupplier rotate) {
        m_driveSubsystem = driveSubsystem;
        m_drive = drive;
        m_rotate = rotate;

        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        m_driveSubsystem.arcadeDrive(m_drive.getAsDouble(), -m_rotate.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
