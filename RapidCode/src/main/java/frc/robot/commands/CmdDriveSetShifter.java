// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.subsystems.Drive;
// import frc.robot.subsystems.Drive.ShifterState;

// public class CmdDriveSetShifter extends CommandBase {
//     Drive m_driveSubsystem;
//     ShifterState m_state;

//     public CmdDriveSetShifter(Drive driveSubsystem, ShifterState state) {
//         m_driveSubsystem = driveSubsystem;
//         m_state = state;
//     }

//     @Override
//     public void initialize() {
//         m_driveSubsystem.setShifterState(m_state);
//     }

//     @Override
//     public boolean isFinished() {
//         return true;
//     }
// }
