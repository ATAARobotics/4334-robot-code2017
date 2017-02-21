package ca.fourthreethreefour.subsystems;

import edu.first.module.Module;
import edu.first.module.actuators.VictorModule;
import edu.first.module.subsystems.Subsystem;

public interface Wipers extends Settings {

    VictorModule wiper1 = new VictorModule(WIPER_1),
                 wiper2 = new VictorModule(WIPER_2);

    Subsystem wipers = new Subsystem(new Module[] { wiper1, wiper2 });
}