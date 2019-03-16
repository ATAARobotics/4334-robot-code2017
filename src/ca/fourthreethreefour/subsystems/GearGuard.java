package ca.fourthreethreefour.subsystems;

import ca.fourthreethreefour.settings.Settings;
import edu.first.module.actuators.DualActionSolenoid;
import edu.first.module.actuators.DualActionSolenoid.Direction;
import edu.first.module.actuators.DualActionSolenoidModule;
import edu.first.module.actuators.SpikeRelayModule;

public interface GearGuard extends Settings {

    /**
     * Gear securement system/replacement for wipers. We are totally trademarking Gear Guard.
     */
    //DualActionSolenoidModule gearGuard = new DualActionSolenoidModule(GEARGUARD_1, GEARGUARD_2);
    
    /**
     * Indicator light for the bucket. Should turn on if the GearGuard is open and the bucket is back.
     * In other words, the light turns on if the robot is ready to accept a gear.
     */
    //SpikeRelayModule indicator = new SpikeRelayModule(INDICATOR);
    
    DualActionSolenoid.Direction GEAR_GUARD_IN = Direction.LEFT;
    DualActionSolenoid.Direction GEAR_GUARD_OUT = Direction.RIGHT;
}