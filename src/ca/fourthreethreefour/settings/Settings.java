package ca.fourthreethreefour.settings;

import java.io.File;

public interface Settings {
    SettingsFile settingsFile = SettingsFile.findFile(new File("/settings.txt"));

    String ROBOT_TYPE = settingsFile.getProperty("ROBOT_TYPE", "");
    boolean AUTO_ALLIANCE_INDEPENDENT = settingsFile.getBooleanProperty("AUTO_ALLIANCE_INDEPENDENT", false);
    String AUTO_TYPE = settingsFile.getProperty("AUTO_TYPE", "");
    
    double DRIVE_COMPENSATION = settingsFile.getDoubleProperty("DRIVE_COMPENSATION", 0.0);
    double TURN_CURVE = settingsFile.getDoubleProperty("TURN_CURVE", 1.5);
    double TURN_CONSTANT = settingsFile.getDoubleProperty("TURN_CONSTANT", 1);

    int DRIVE_RIGHT_1 = settingsFile.getIntProperty("DRIVE_RIGHT_1", 0);
    int DRIVE_RIGHT_2 = settingsFile.getIntProperty("DRIVE_RIGHT_2", 1);
    int DRIVE_RIGHT_3 = settingsFile.getIntProperty("DRIVE_RIGHT_3", 2);
    int DRIVE_LEFT_1 = settingsFile.getIntProperty("DRIVE_LEFT_1", 3);
    int DRIVE_LEFT_2 = settingsFile.getIntProperty("DRIVE_LEFT_2", 4);
    int DRIVE_LEFT_3 = settingsFile.getIntProperty("DRIVE_LEFT_3", 5);
    int DRIVE_ENCODER_1 = settingsFile.getIntProperty("DRIVE_ENCODER_1", 0); //TODO get defaults
    int DRIVE_ENCODER_2 = settingsFile.getIntProperty("DRIVE_ENCODER_2", 1);
    int DRIVE_GYRO = settingsFile.getIntProperty("DRIVE_GYRO", 0);
    int CLIMBER_1 = settingsFile.getIntProperty("CLIMBER_1", 8);
    int CLIMBER_2 = settingsFile.getIntProperty("CLIMBER_2", 9);
    int GEARGUARD_1 = settingsFile.getIntProperty("GEARGUARD_1", 2);
    int GEARGUARD_2 = settingsFile.getIntProperty("GEARGUARD_2", 3);
    int INDICATOR = settingsFile.getIntProperty("INDICATOR", 0);
    int BUCKET_SOLENOID_PORT_1 = settingsFile.getIntProperty("BUCKET_SOLENOID_PORT_1", 0);
    int BUCKET_SOLENOID_PORT_2 = settingsFile.getIntProperty("BUCKET_SOLENOID_PORT_2", 1);
    //int BUCKET_SWITCH = settingsFile.getIntProperty("BUCKET_SWITCH", 0);
    int CONTROLLER_1 = settingsFile.getIntProperty("CONTROLLER_1", 0);
    int CONTROLLER_2 = settingsFile.getIntProperty("CONTROLLER_2", 1);
    int ALLIANCE_SWITCH = settingsFile.getIntProperty("ALLIANCE_SWITCH", 0);
}