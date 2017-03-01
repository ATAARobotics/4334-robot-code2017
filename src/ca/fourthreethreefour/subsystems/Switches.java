package ca.fourthreethreefour.subsystems;

import edu.first.module.sensors.DigitalInput;

public interface Switches extends Settings {
//TODO find out what hardware these switches are
    /**
     * Hardware switch on the robot. Determines if the robot uses left, center or right auto.
     */
    DigitalInput autoSwitch = new DigitalInput(AUTO_SWITCH);
    
    /**
     * Hardware switch on the robot. Determines which alliance the robot is on, used for turning in auto.
     */
//    DigitalInput allianceSwitch = new DigitalInput(ALLIANCE_SWITCH);
    
}
