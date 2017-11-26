package ca.fourthreethreefour.subsystems;

import edu.first.identifiers.InversedSpeedController;
import edu.first.module.Module;
import edu.first.module.actuators.Drivetrain;
import edu.first.module.actuators.VictorModule;
import edu.first.module.actuators.VictorModuleGroup;
import edu.first.module.subsystems.Subsystem;

public interface Drive extends Settings {
	
	VictorModuleGroup left = new VictorModuleGroup(new VictorModule[] { 
			new VictorModule(DRIVE_LEFT_1),
			new VictorModule(DRIVE_LEFT_2),
			new VictorModule(DRIVE_LEFT_3)
	});
	
	VictorModuleGroup right = new VictorModuleGroup(new VictorModule[] { 
			new VictorModule(DRIVE_RIGHT_1),
			new VictorModule(DRIVE_RIGHT_2),
			new VictorModule(DRIVE_RIGHT_3)
	});

	Drivetrain drivetrain = new Drivetrain(new InversedSpeedController(left), right);

	Subsystem drive = new Subsystem(new Module[] {
			drivetrain, left, right
	});
}
