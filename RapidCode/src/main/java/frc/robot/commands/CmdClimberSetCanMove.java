package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class CmdClimberSetCanMove extends CommandBase {
    private Climber m_climberSubsystem;
    private boolean m_canMove;

    public CmdClimberSetCanMove(Climber climberSubsystem, boolean canMove) {
        m_climberSubsystem = climberSubsystem;
        m_canMove = canMove;
    }

    @Override
    public void initialize() {
        m_climberSubsystem.setCanMove(m_canMove);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
