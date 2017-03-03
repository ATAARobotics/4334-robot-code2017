package ca.fourthreethreefour.subsystems;

import ca.fourthreethreefour.settings.Settings;
import edu.first.module.Module;
import edu.first.module.actuators.SpeedController;
import edu.first.module.actuators.SpeedControllerGroup;
import edu.first.module.actuators.VictorModule;
import edu.first.module.subsystems.Subsystem;

public interface Climber extends Settings {

    VictorModule climber1 = new VictorModule(CLIMBER_1), climber2 = new VictorModule(CLIMBER_2);

    SpeedControllerGroup climberMotors = new SpeedControllerGroup(new SpeedController[] { climber1, climber2 });

    Subsystem climber = new Subsystem(new Module[] { climber1, climber2 });
}
