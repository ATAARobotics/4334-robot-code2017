package ca.fourthreethreefour.subsystems;

import edu.first.module.Module;
import edu.first.module.actuators.VictorModule;
import edu.first.module.controllers.PIDController;
import edu.first.module.sensors.AnalogInput;
import edu.first.module.subsystems.Subsystem;

public interface Wipers extends Settings {

	VictorModule //TODO get ports from external file
		wiper1 = new VictorModule(8),
		wiper2 = new VictorModule(9);

	Subsystem wipers = new Subsystem(new Module[] { 
			wiper1, wiper2
	});
	
	/**
	 * Potentiometers for wipers.
	 */
	AnalogInput 
		wiper1Pot = new AnalogInput(0),
		wiper2Pot = new AnalogInput(1);
	
	/**
	 * PID controllers for wipers.
	 */
	PIDController 
		wiper1PID = new PIDController(wiper1Pot, wiper1),
		wiper2PID = new PIDController(wiper2Pot, wiper2);
	
	/**
	 * PID system for the wipers. Should be activated while a button is held down, and deactivated when it is released.
	 */
	Subsystem wipersPID = new Subsystem(new Module[] { 
			wiper1Pot, wiper2Pot, wiper1PID, wiper2PID
	});
}
