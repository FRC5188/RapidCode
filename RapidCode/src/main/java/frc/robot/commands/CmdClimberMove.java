package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class CmdClimberMove extends CommandBase {
    /** Creates a new climberMoveCommand. */
    private final Climber m_climber;
    private DoubleSupplier m_upSpeed, m_downSpeed;

    /** Creates a new lowerClimber. */
    public CmdClimberMove(Climber climberSubsystem, DoubleSupplier upSpeed, DoubleSupplier downSpeed) {
        m_climber = climberSubsystem;
        m_upSpeed = upSpeed;
        m_downSpeed = downSpeed;
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
        m_climber.climberMove(m_upSpeed.getAsDouble(), m_downSpeed.getAsDouble());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}