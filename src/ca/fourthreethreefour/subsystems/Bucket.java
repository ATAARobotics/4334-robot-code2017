package ca.fourthreethreefour.subsystems;

import edu.first.module.Module;
import edu.first.module.actuators.DualActionSolenoidModule;
import edu.first.module.sensors.DigitalInput;
import edu.first.module.subsystems.Subsystem;

public interface Bucket extends Settings {

    DualActionSolenoidModule bucketSolenoid = new DualActionSolenoidModule(BUCKET_SOLENOID_PORT_1,
            BUCKET_SOLENOID_PORT_2);
    DigitalInput bucketSwitch = new DigitalInput(BUCKET_SWITCH);

    Subsystem bucket = new Subsystem(new Module[] { bucketSolenoid, bucketSwitch });
}