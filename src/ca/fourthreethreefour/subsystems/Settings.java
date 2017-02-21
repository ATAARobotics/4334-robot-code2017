package ca.fourthreethreefour.subsystems;

import java.util.Properties;

public class Settings extends Properties {
	private static final long serialVersionUID = -6308390915164135156L;
	
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
