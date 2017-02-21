package ca.fourthreethreefour.subsystems;

import java.io.File;

public interface Settings {
    SettingsFile settingsFile = new SettingsFile(new File("/settings.txt"));

    String ROBOT_TYPE = settingsFile.getProperty("ROBOT_TYPE", "Competition");
    String AUTO_TYPE = settingsFile.getProperty("AUTO_TYPE", "CenterMobile");
    
    double DRIVE_COMPENSATION = settingsFile.getDoubleProperty("DRIVE_COMPENSATION", 0.0);
    double TURN_CURVE = settingsFile.getDoubleProperty("TURN_CURVE", 1.5);

    int DRIVE_RIGHT_1 = settingsFile.getIntProperty("DRIVE_RIGHT_1", 0);
    int DRIVE_RIGHT_2 = settingsFile.getIntProperty("DRIVE_RIGHT_2", 1);
    int DRIVE_RIGHT_3 = settingsFile.getIntProperty("DRIVE_RIGHT_3", 2);
    int DRIVE_LEFT_1 = settingsFile.getIntProperty("DRIVE_LEFT_1", 3);
    int DRIVE_LEFT_2 = settingsFile.getIntProperty("DRIVE_LEFT_2", 4);
    int DRIVE_LEFT_3 = settingsFile.getIntProperty("DRIVE_LEFT_3", 5);
    int CLIMBER_1 = settingsFile.getIntProperty("CLIMBER_1", 8);
    int CLIMBER_2 = settingsFile.getIntProperty("CLIMBER_2", 9);
    int WIPER_1 = settingsFile.getIntProperty("WIPER_1", 6);
    int WIPER_2 = settingsFile.getIntProperty("WIPER_2", 7);
    int WIPER_1_POTENTIOMETER = settingsFile.getIntProperty("WIPER_1_POTENTIOMETER", 0);
    int WIPER_2_POTENTIOMETER = settingsFile.getIntProperty("WIPER_2_POTENTIOMETER", 1);
    int BUCKET_SOLENOID_PORT_1 = settingsFile.getIntProperty("BUCKET_SOLENOID_PORT_1", 0);
    int BUCKET_SOLENOID_PORT_2 = settingsFile.getIntProperty("BUCKET_SOLENOID_PORT_2", 1);
    int BUCKET_SWITCH = settingsFile.getIntProperty("BUCKET_SWITCH", 0);
    int CONTROLLER_1 = settingsFile.getIntProperty("CONTROLLER_1", 0);
    int CONTROLLER_2 = settingsFile.getIntProperty("CONTROLLER_2", 1);
}