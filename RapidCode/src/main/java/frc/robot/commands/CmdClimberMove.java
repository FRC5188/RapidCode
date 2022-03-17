package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class CmdClimberMove extends CommandBase {
    /** Creates a new climberMoveCommand. */
    private final Climber m_climber;
    private DoubleSupplier m_speed;

    /** Creates a new lowerClimber. */
    public CmdClimberMove(Climber climberSubsystem, DoubleSupplier speed) {
        m_climber = climberSubsystem;
        m_speed = speed;

        addRequirements(climberSubsystem);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (m_climber.getCanMove()) {
            m_climber.setClimberSpeed(m_speed.getAsDouble());
            System.out.println("hi");
        } else {
            m_climber.setClimberSpeed(0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_climber.setClimberSpeed(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}