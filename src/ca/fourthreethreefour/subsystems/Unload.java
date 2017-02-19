package ca.fourthreethreefour.subsystems;

import edu.first.module.Module;
import edu.first.module.actuators.DualActionSolenoidModule;
import edu.first.module.actuators.DualActionSolenoidModuleGroup;
import edu.first.module.sensors.DigitalInput;
import edu.first.module.subsystems.Subsystem;

public interface Unload extends Settings {

	DualActionSolenoidModule //TODO get ports from external file
		unload1 = new DualActionSolenoidModule(0, 1),
		unload2 = new DualActionSolenoidModule(2, 3);
	
	DualActionSolenoidModuleGroup unloadSolenoids = new DualActionSolenoidModuleGroup(new DualActionSolenoidModule[] { 
			unload1, unload2
	});
	
	/**
	 * Gear manipulator.
	 */
	Subsystem unload = new Subsystem(new Module[] { 
			unloadSolenoids, unload1, unload2
	});
	
	/**
	 * Limit switch for the gear manipulator. Determines if the robot is holding a gear or not.
	 */
	DigitalInput unloadLimitSwitch = new DigitalInput(0); //TODO get actual channel
}
