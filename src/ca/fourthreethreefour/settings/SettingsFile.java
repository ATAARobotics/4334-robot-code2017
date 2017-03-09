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
    
    public static SettingsFile findFile(File file) {
        try {
            return new SettingsFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return new SettingsFile();
        }
    }

    public SettingsFile(File file) throws IOException {
        load(new FileInputStream(file));
    }
    
    public SettingsFile() {}

    @Override
    public String getProperty(String key, String defaultValue) {
        if (containsKey(key)) {
            return super.getProperty(key);
        } else {
            Logger.getLogger(SettingsFile.class)
                    .info(key + " not found in settings file, using default (" + defaultValue + ")");
            return defaultValue;
        }
    }

    int getIntProperty(String key, int defaultValue) {
        if (stringPropertyNames().contains(key)) {
            return Integer.parseInt(getProperty(key));
        } else {
            Logger.getLogger(SettingsFile.class)
                    .info(key + " not found in settings file, using default (" + defaultValue + ")");
            return defaultValue;
        }
    }

    double getDoubleProperty(String key, double defaultValue) {
        if (stringPropertyNames().contains(key)) {
            return Double.parseDouble(getProperty(key));
        } else {
            Logger.getLogger(SettingsFile.class)
                    .info(key + " not found in settings file, using default (" + defaultValue + ")");
            return defaultValue;
        }
    }

    boolean getBooleanProperty(String key, boolean defaultValue) {
        if (stringPropertyNames().contains(key)) {
            return Boolean.parseBoolean(getProperty(key));
        } else {
            Logger.getLogger(SettingsFile.class)
                    .info(key + " not found in settings file, using default (" + defaultValue + ")");
            return defaultValue;
        }
    }
}
