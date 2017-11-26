package ca.fourthreethreefour.subsystems;

import edu.first.module.Module;
import edu.first.module.actuators.VictorModule;
import edu.first.module.controllers.PIDController;
import edu.first.module.sensors.AnalogInput;
import edu.first.module.subsystems.Subsystem;

public interface Wipers extends Settings {

	VictorModule
		wiper1 = new VictorModule(WIPER_1),
		wiper2 = new VictorModule(WIPER_2);
	
	/**
	 * Potentiometers for wipers.
	 */
	AnalogInput 
		wiper1Pot = new AnalogInput(WIPER_1_POTENTIOMETER),
		wiper2Pot = new AnalogInput(WIPER_2_POTENTIOMETER);
	
	Subsystem wipers = new Subsystem(new Module[] { 
			wiper1, wiper2, wiper1Pot, wiper2Pot, 
	});

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
			wiper1PID, wiper2PID
	});
}
