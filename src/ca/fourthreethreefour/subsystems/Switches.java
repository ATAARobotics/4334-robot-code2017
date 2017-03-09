package ca.fourthreethreefour.subsystems;

import ca.fourthreethreefour.settings.Settings;
import edu.first.module.sensors.DigitalInput;

public interface Switches extends Settings {
    /**
     * Hardware switch on the robot. Determines which alliance the robot is on, used for turning in auto.
     */
    DigitalInput allianceSwitch = new DigitalInput(ALLIANCE_SWITCH);
}