package ca.fourthreethreefour.subsystems;

import java.io.File;

public interface Settings {
	SettingsFile settingsFile = new SettingsFile(new File("/settings.txt"));

	//ports
	int DRIVE_RIGHT_1 = settingsFile.getIntProperty("DRIVE_RIGHT_1", 0);
	int DRIVE_RIGHT_2 = settingsFile.getIntProperty("DRIVE_RIGHT_2", 1);
	int DRIVE_RIGHT_3 = settingsFile.getIntProperty("DRIVE_RIGHT_3", 2);
	int DRIVE_LEFT_1 = settingsFile.getIntProperty("DRIVE_LEFT_1", 3);
	int DRIVE_LEFT_2 = settingsFile.getIntProperty("DRIVE_LEFT_2", 4);
	int DRIVE_LEFT_3 = settingsFile.getIntProperty("DRIVE_LEFT_3", 5);
	int CLIMBER_1 = settingsFile.getIntProperty("CLIMBER_1", 8); //TODO this should be 6
	int CLIMBER_2 = settingsFile.getIntProperty("CLIMBER_2", 9); //TODO this should be 7
	int WIPER_1 = settingsFile.getIntProperty("WIPER_1", 6); //TODO this should be 8
	int WIPER_2 = settingsFile.getIntProperty("WIPER_2", 7); //TODO this should be 9
	int WIPER_1_POTENTIOMETER = settingsFile.getIntProperty("WIPER_1_POTENTIOMETER", 0);
	int WIPER_2_POTENTIOMETER = settingsFile.getIntProperty("WIPER_1_POTENTIOMETER", 1);
	int UNLOAD_SOLENOID_PORT_1 = settingsFile.getIntProperty("UNLOAD_SOLENOID_PORT_1", 0);
	int UNLOAD_SOLENOID_PORT_2 = settingsFile.getIntProperty("UNLOAD_SOLENOID_PORT_2", 1);
	int UNLOAD_DIGITAL_INPUT = settingsFile.getIntProperty("UNLOAD_DIGITAL_INPUT", 0);
	int CONTROLLER_1 = settingsFile.getIntProperty("CONTROLLER_1", 0);
	int CONTROLLER_2 = settingsFile.getIntProperty("CONTROLLER_1", 1);
	
	//not ports
	
}