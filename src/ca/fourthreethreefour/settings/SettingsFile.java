package ca.fourthreethreefour.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import edu.first.util.log.Logger;
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
    
    @Override
    public String getProperty(String key, String defaultValue) {
        if (containsKey(key)) {
            return super.getProperty(key);
        } else {
            Logger.getLogger(SettingsFile.class).info(key + " not found in settings file, using default (" + defaultValue + ")");
            return defaultValue;
        }
    }

    int getIntProperty(String key, int defaultValue) {
        if (stringPropertyNames().contains(key)) {
            return Integer.parseInt(getProperty(key));
        } else {
            Logger.getLogger(SettingsFile.class).info(key + " not found in settings file, using default (" + defaultValue + ")");
            return defaultValue;
        }
    }

    double getDoubleProperty(String key, double defaultValue) {
        if (stringPropertyNames().contains(key)) {
            return Double.parseDouble(getProperty(key));
        } else {
            Logger.getLogger(SettingsFile.class).info(key + " not found in settings file, using default (" + defaultValue + ")");
            return defaultValue;
        }
    }

    boolean getBooleanProperty(String key, boolean defaultValue) {
        if (stringPropertyNames().contains(key)) {
            return Boolean.parseBoolean(getProperty(key));
        } else {
            Logger.getLogger(SettingsFile.class).info(key + " not found in settings file, using default (" + defaultValue + ")");
            return defaultValue;
        }
    }
}
