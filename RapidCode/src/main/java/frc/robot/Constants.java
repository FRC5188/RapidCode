package frc.robot;

public final class Constants {
    /*
    The class that holds constant values
    All names should be all caps with _ between to denote a static (unchangeable) value
    If it is a general constant (not a port/id), put between this comment and the next class
    If it is a port/id, put it into its class based on type (CAN, DIO, AIO, etc)
    */
    public static final double QUICK_TURN_DEADBAND = 0.05;
    public static final double QUICK_TURN_MULTIPLIER = 0.75;
    public static final double ARCADE_DRIVE_DEADBAND = 0.025;

    public static final double FALCON_MAX_RPM = 6000;

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
        public static final int LEFT_PRIMARY_DRIVE_ID = 1;
        public static final int LEFT_SECONDARY_DRIVE_ID = 2;
        public static final int RIGHT_PRIMARY_DRIVE_ID = 3;
        public static final int RIGHT_SECONDARY_DRIVE_ID = 4;
        public static final int LEFT_SHOOTER_ID = 5;
        public static final int RIGHT_SHOOTER_ID = 6;

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
