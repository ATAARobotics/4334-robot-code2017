package ca.fourthreethreefour.subsystems;

import edu.first.identifiers.InversedSpeedController;
import edu.first.module.Module;
import edu.first.module.actuators.Drivetrain;
import edu.first.module.actuators.VictorModule;
import edu.first.module.actuators.VictorModuleGroup;
import edu.first.module.subsystems.Subsystem;

public interface Drive extends Settings {
	
	VictorModuleGroup left = new VictorModuleGroup(new VictorModule[] { 
			new VictorModule(0), //TODO get ports from external file
			new VictorModule(1),
			new VictorModule(2)
	});
	
	VictorModuleGroup right = new VictorModuleGroup(new VictorModule[] { 
			new VictorModule(3),
			new VictorModule(4),
			new VictorModule(5)
	});

	Drivetrain drivetrain = new Drivetrain(left, new InversedSpeedController(right));

	Subsystem drive = new Subsystem(new Module[] {
			drivetrain, left, right
	});
}
