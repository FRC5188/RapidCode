package frc.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private RobotContainer m_robotContainer;
    ArrayList<String> m_jsonList = new ArrayList<String>();
    HashMap<String, Trajectory> m_trajectories = new HashMap<String, Trajectory>();
    HashMap<String, ArrayList<Trajectory>> m_autoPaths = new HashMap<String, ArrayList<Trajectory>>();

    private void loadTrajectory(String jsonPath) {
        String trajName = jsonPath.replace("Paths/", "");
	    trajName = trajName.replace(".wpilib.json", "");
        try {
            Trajectory trajectory = new Trajectory();
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(jsonPath);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            m_trajectories.put(trajName, trajectory);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + jsonPath, ex.getStackTrace());
        }
    }

    private void loadAutonomousPaths() {
        for (String auto : new File("Autos").list()) {
            ArrayList<Trajectory> trajectories = new ArrayList<Trajectory>();
            File f = new File(auto);
            try {
                Scanner s = new Scanner(f);
                while (s.hasNextLine()) {
                    String name = s.nextLine();
                    trajectories.add(m_trajectories.get(name));
                }
            m_autoPaths.put(auto, trajectories);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void robotInit() {
        m_robotContainer = new RobotContainer();

        m_jsonList.add("Paths/TestCurve.wpilib.json");
        
        for (String json : m_jsonList) loadTrajectory(json);
        loadAutonomousPaths();
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
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }
}
