package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallPath;
import frc.robot.subsystems.Pickup;
import frc.robot.subsystems.Pickup.PickupState;

public class CmdPickupDefault extends CommandBase {
    private Pickup m_pickupSubsystem;
    private BallPath m_ballPathSubsystem;

    public CmdPickupDefault(Pickup pickupSubsystem, BallPath ballPathSubsystem) {
        m_pickupSubsystem = pickupSubsystem;
        m_ballPathSubsystem = ballPathSubsystem;

        addRequirements(pickupSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (m_ballPathSubsystem.getBallCount() >= 2) {
            m_pickupSubsystem.setPickupState(PickupState.Retracted);
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
