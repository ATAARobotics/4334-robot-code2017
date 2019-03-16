package ca.fourthreethreefour.subsystems;

import ca.fourthreethreefour.settings.Settings;
import edu.first.module.Module;
import edu.first.module.actuators.DualActionSolenoid;
import edu.first.module.actuators.DualActionSolenoid.Direction;
import edu.first.module.actuators.DualActionSolenoidModule;
import edu.first.module.actuators.Solenoid;
import edu.first.module.actuators.SolenoidModule;
import edu.first.module.actuators.VictorModule;
import edu.first.module.subsystems.Subsystem;

public interface Bucket extends Settings {

    DualActionSolenoidModule bucketSolenoid = new DualActionSolenoidModule(BUCKET_SOLENOID_PORT_1, BUCKET_SOLENOID_PORT_2);
    
    VictorModule groundIntake = new VictorModule(GROUND_INTAKE);
    
    Subsystem bucket = new Subsystem(new Module[] { bucketSolenoid, groundIntake });
    DualActionSolenoid.Direction 
        BUCKET_IN = Direction.RIGHT,
        BUCKET_OUT = Direction.LEFT;
}
