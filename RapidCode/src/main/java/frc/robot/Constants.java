package frc.robot;

public final class Constants {
    /*
     * The class that holds constant values
     * All names should be all caps with _ between to denote a static (unchangeable)
     * value
     * If it is a general constant (not a port/id), put between this comment and the
     * next class
     * If it is a port/id, put it into its class based on type (CAN, DIO, AIO, etc)
     */
    public static final double QUICK_TURN_DEADBAND = 0.05;
    public static final double QUICK_TURN_MULTIPLIER = 0.75;
    public static final double ARCADE_DRIVE_DEADBAND = 0.025;

    public static final double BALL_PATH_LOADING_SPEED = 0.5;
    public static final double BALL_PATH_POSITION_SPEED = 0.5;
    public static final double BALL_PATH_SHOOTING_SPEED = 0.5;

    public static final double AUTO_DRIVE_SPEED = 0.25;

    public static final double FALCON_MAX_RPM = 6000;

    public static final double CAMERA_HEIGHT_INCHES = 24;  //Check this value
    public static final double TARGET_HEIGHT_INCHES = 102.625;
    public static final double CAMERA_PITCH_DEGREES = 18;
    
    public static final double DRIVE_PID_P = .01; //still needs tuned
    public static final double DRIVE_PID_I = 0;
    public static final double DRIVE_PID_D = 0;

    public static final double ROTATE_PID_P = .01;
    public static final double ROTATE_PID_I = 0;
    public static final double ROTATE_PID_D = 0;

    public static final double HOOD_PID_P = .1;
    public static final double HOOD_PID_I = 0;
    public static final double HOOD_PID_D = 0;

    public final class PID {
        public static final double DRIVE_PROPORTIONAL_PRACTICE     = 0.017;
        public static final double DRIVE_INTEGRAL_PRACTICE         = 0.000;
        public static final double DRIVE_DERIVATIVE_PRACTICE       = 0.001;
        public static final double DRIVE_TOLERANCE_PRACTICE        =   1.0;

        public static final double ROTATE_PROPORTIONAL_PRACTICE    = 0.0090;
        public static final double ROTATE_INTEGRAL_PRACTICE        = 0.0005;
        public static final double ROTATE_DERIVATIVE_PRACTICE      = 0.0030;
        public static final double ROTATE_TOLERANCE_PRACTICE       =    1.0;

        public static final double DRIVE_PROPORTIONAL_COMPETITION  = 0.017;
        public static final double DRIVE_INTEGRAL_COMPETITION      = 0.000;
        public static final double DRIVE_DERIVATIVE_COMPETITION    = 0.001;
        public static final double DRIVE_TOLERANCE_COMPETITION     =   1.0;

        public static final double ROTATE_PROPORTIONAL_COMPETITION = 0.0090;
        public static final double ROTATE_INTEGRAL_COMPETITION     = 0.0005;
        public static final double ROTATE_DERIVATIVE_COMPETITION   = 0.0030;
        public static final double ROTATE_TOLERANCE_COMPETITION    =    1.0;
    }

    public final class AIO {
        /*
         * The class that holds the AIO ports used (AIO = analog input/output)
         * Used for analog sensors: they return a range of values
         * Sensor examples include potentiometers, ultrasonic sensors, range sensors
         * Name example: public static final int TURRET_POT_PORT = 0 (name what sensor
         * is/does and end with port)
         */
        public static final int HOOD_POTENTIOMETER_PORT = 0;
        public static final int TURRET_POTENTIOMETER_PORT = 1;

    }

    public final class CAN {
        /*
         * The class that holds the CAN ids for each motor
         * Most of the motors/motor controllers are labelled with a number, so use that
         * as ID
         * Do not use an id of 0; this is a default for things like the PDH, PCM, and
         * roboRIO, so start at 1
         * Name example: public static final int LEFT_PRIMARY_DRIVE_ID (name with what
         * motor does and end with id)
         */

        
        public static final int LEFT_PRIMARY_DRIVE_ID = 1;
        public static final int LEFT_SECONDARY_DRIVE_ID = 2;
        public static final int RIGHT_PRIMARY_DRIVE_ID = 3;
        public static final int RIGHT_SECONDARY_DRIVE_ID = 4;

        public static final int INDEX_MOTOR_TOP_ID = 5;
        public static final int INDEX_MOTOR_BOTTOM_ID = 6;

        public static final int LEFT_FLYWHEEL_ID = 7;
        public static final int RIGHT_FLYWHEEL_ID = 8;
        public static final int HOOD_MOTOR_ID = 9;
        public static final int TURRET_MOTOR_ID = 10;
        
        public static final int PICKUP_MOTOR_ID = 11;

        public static final int REV_PH_ID = 13;
    }

    public final class DIO {
        /*
         * The class that holds the DIO ports used (DIO = digital input/output)
         * Used for digital types of sensors: they only return a true or false
         * Sensor examples include some encoders, photoelectric sensors
         * Name example: public static final int PICKUP_SENSOR_PORT = 0 (name what
         * sensor is/does and end with port)
         */
        public static final int ENTRANCE_SENSOR_PORT = 0;
        public static final int MIDDLE_SENSOR_PORT = 1;
        public static final int SHOOTER_SENSOR_PORT = 2;
    }

    public final class PCM {
        /*
         * The class that holds the solenoids used on the robot
         * The PCM (Pneumatics Control Module) controls all the solenoids and is
         * connected through CAN
         * Will only put solenoid ports into this class
         * Name example: public static final int PICKUP_SOLENOID = 0 (name what solenoid
         * does and end with solenoid)
         */
        public static final int DRIVE_LEFT_SOLENOID = 9;
        public static final int DRIVE_RIGHT_SOLENOID = 8;
        public static final int PICKUP_LEFT_SOLENOID = 10;
        public static final int PICKUP_RIGHT_SOLENOID = 11;
    }

    public final class ButtonMappings {
        /*
         * The class that holds the button mappings for an X-box controller
         * Reference the all-caps name in code to use the buttons
         * You should not need to add anything to this class, so don't touch please!
         */
        public static final int A_BUTTON = 1;
        public static final int B_BUTTON = 2;
        public static final int X_BUTTON = 3;
        public static final int Y_BUTTON = 4;
        public static final int LEFT_BUMPER = 5;
        public static final int RIGHT_BUMPER = 6;
        public static final int BACK_BUTTON = 7;
        public static final int START_BUTTON = 8;
        public static final int LEFT_JOY_BUTTON = 9;
        public static final int RIGHT_JOY_BUTTON = 10;
    }
}
