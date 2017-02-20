package ca.fourthreethreefour.subsystems;

import edu.first.module.Module;
import edu.first.module.joysticks.XboxController;
import edu.first.module.subsystems.Subsystem;

public interface Controllers extends Settings {
	
	XboxController 
		controller1 = new XboxController(0),
		controller2 = new XboxController(1);

	Subsystem controllers = new Subsystem(new Module[] { 
			controller1, controller2
	});
}
