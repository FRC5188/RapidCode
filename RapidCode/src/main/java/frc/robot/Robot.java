package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;
    private Compressor m_compressor;

    private RobotContainer m_robotContainer;

    @Override
    public void robotInit() {
        m_robotContainer = new RobotContainer();
        m_compressor = new Compressor(Constants.CAN.REV_PH_ID, PneumaticsModuleType.REVPH);
        m_compressor.enableDigital();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        m_robotContainer.resetEncoders();
        System.out.println("ENOCDERS RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }

        m_robotContainer.setAutoRampRate(1);
    }

    @Override
    public void autonomousPeriodic() {
        System.out.println("enocoder pos: " + m_robotContainer.getEncoderPosition());
    }

    @Override
    public void teleopInit() {
        // im sorry this got messy with us testing autos :( GH
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
        m_robotContainer.resetEncoders();
        System.out.println("ENOCDERS RESET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        // this is the workaround we have for the default command not working
        Command teleopDrive = m_robotContainer.getTeleopCommand();
        teleopDrive.schedule();

        m_robotContainer.setAutoRampRate(0);
    }

    @Override
    public void teleopPeriodic() {
        System.out.println("enocoder pos: " + m_robotContainer.getEncoderPosition());

    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }
}
