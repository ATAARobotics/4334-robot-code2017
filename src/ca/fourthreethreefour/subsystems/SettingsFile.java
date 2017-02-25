package ca.fourthreethreefour.subsystems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import edu.wpi.first.wpilibj.DriverStation;

public class SettingsFile extends Properties {
    private static final long serialVersionUID = -6308390915164135156L;

    DriverStation driverStation = DriverStation.getInstance();

    public SettingsFile(File file) {
        try {
            load(new FileInputStream(file));
        } catch (FileNotFoundException err) {
            DriverStation.reportError("File not found", false);
            err.printStackTrace();
        } catch (IOException err) {
            DriverStation.reportError("I/O Exception", false);
            err.printStackTrace();
        }
    }

    int getIntProperty(String key, int defaultValue) {
        if (stringPropertyNames().contains(key)) {
            return Integer.parseInt(getProperty(key));
        } else {
            return defaultValue;
        }
    }

    double getDoubleProperty(String key, double defaultValue) {
        if (stringPropertyNames().contains(key)) {
            return Double.parseDouble(getProperty(key));
        } else {
            return defaultValue;
        }
    }

    boolean getBooleanProperty(String key, boolean defaultValue) {
        if (stringPropertyNames().contains(key)) {
            return Boolean.parseBoolean(getProperty(key));
        } else {
            return defaultValue;
        }
    }
}
