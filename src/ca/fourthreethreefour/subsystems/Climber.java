package ca.fourthreethreefour.subsystems;

import edu.first.identifiers.InversedSpeedController;
import edu.first.module.Module;
import edu.first.module.actuators.SpeedController;
import edu.first.module.actuators.SpeedControllerGroup;
import edu.first.module.actuators.TalonModule;
import edu.first.module.actuators.VictorModule;
import edu.first.module.subsystems.Subsystem;

public interface Climber extends Settings {
	
	/*VictorModule 
		climber1 = new VictorModule(CLIMBER_1),
		climber2 = new VictorModule(CLIMBER_2);
	*/
	/**
	 * Speed controllers for the practice bot.
	 */
	TalonModule 
		climber1 = new TalonModule(CLIMBER_1),
		climber2 = new TalonModule(CLIMBER_2);
	
	SpeedControllerGroup climberMotors = new SpeedControllerGroup(new SpeedController[] { 
			new InversedSpeedController(climber1), new InversedSpeedController(climber2)
	});

	Subsystem climber = new Subsystem(new Module[] { 
			climber1, climber2
	});
}
