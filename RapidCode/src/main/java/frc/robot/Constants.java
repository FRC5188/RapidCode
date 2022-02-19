package frc.robot;

import edu.wpi.first.math.util.Units;

public final class Constants {
    /*
    The class that holds constant values
    All names should be all caps with _ between to denote a static (unchangeable) value
    If it is a general constant (not a port/id), put between this comment and the next class
    If it is a port/id, put it into its class based on type (CAN, DIO, AIO, etc)
    */
    public static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24);  //Check this value
    public static final double TARGET_HEIGHT_METERS = Units.inchesToMeters(102.625);

    public static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

    public final class AIO {
        /*
        The class that holds the AIO ports used (AIO = analog input/output)
        Used for analog sensors: they return a range of values
        Sensor examples include potentiometers, ultrasonic sensors, range sensors
        Name example: public static final int TURRET_POT_PORT = 0 (name what sensor is/does and end with port)
        */
    }

    public final class CAN {
        /*
        The class that holds the CAN ids for each motor
        Most of the motors/motor controllers are labelled with a number, so use that as ID
        Do not use an id of 0; this is a default for things like the PDH, PCM, and roboRIO, so start at 1
        Name example: public static final int LEFT_PRIMARY_DRIVE_ID (name with what motor does and end with id)
        */
    }

    public final class DIO {
        /*
        The class that holds the DIO ports used (DIO = digital input/output)
        Used for digital types of sensors: they only return a true or false
        Sensor examples include some encoders, photoelectric sensors
        Name example: public static final int PICKUP_SENSOR_PORT = 0 (name what sensor is/does and end with port)
        */
    }

    public final class PCM {
        /*
        The class that holds the solenoids used on the robot
        The PCM (Pneumatics Control Module) controls all the solenoids and is connected through CAN
        Will only put solenoid ports into this class
        Name example: public static final int PICKUP_SOLENOID = 0 (name what solenoid does and end with solenoid)
        */
    }
    
    public final class ButtonMappings {
        /*
        The class that holds the button mappings for an X-box controller
        Reference the all-caps name in code to use the buttons
        You should not need to add anything to this class, so don't touch please!
        */
        public static final int A_BUTTON         = 1;
        public static final int B_BUTTON         = 2;
        public static final int X_BUTTON         = 3;
        public static final int Y_BUTTON         = 4;
        public static final int LEFT_BUMPER      = 5;
        public static final int RIGHT_BUMPER     = 6;
        public static final int BACK_BUTTON      = 7;
        public static final int START_BUTTON     = 8;
        public static final int LEFT_JOY_BUTTON  = 9;
        public static final int RIGHT_JOY_BUTTON = 10;
    }
}
