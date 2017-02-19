package ca.fourthreethreefour.subsystems;

import edu.first.identifiers.InversedSpeedController;
import edu.first.module.Module;
import edu.first.module.actuators.SpeedController;
import edu.first.module.actuators.SpeedControllerGroup;
import edu.first.module.actuators.VictorModule;
import edu.first.module.subsystems.Subsystem;

public interface Climber extends Settings {
	
	VictorModule //TODO get ports from external file
		climber1 = new VictorModule(6),
		climber2 = new VictorModule(7);
	
	SpeedControllerGroup climberMotors = new SpeedControllerGroup(new SpeedController[] { 
			climber1, new InversedSpeedController(climber2)
	});

	Subsystem climber = new Subsystem(new Module[] { 
			climber1, climber2
	});
}