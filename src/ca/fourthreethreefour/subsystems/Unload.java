package ca.fourthreethreefour.subsystems;

import edu.first.module.Module;
import edu.first.module.actuators.DualActionSolenoidModule;
import edu.first.module.sensors.DigitalInput;
import edu.first.module.subsystems.Subsystem;

public interface Unload extends Settings {

	DualActionSolenoidModule //TODO get ports from external file
		unloadSolenoid = new DualActionSolenoidModule(0, 1);
	/**
	 * Gear manipulator.
	 */
	Subsystem unload = new Subsystem(new Module[] { 
			unloadSolenoid
	});
	
	/**
	 * Limit switch for the gear manipulator. Determines if the robot is holding a gear or not.
	 */
	DigitalInput unloadLimitSwitch = new DigitalInput(0); //TODO get actual channel
}
