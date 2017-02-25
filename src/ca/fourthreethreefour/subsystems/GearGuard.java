package ca.fourthreethreefour.subsystems;

import edu.first.module.actuators.DualActionSolenoidModule;

public interface GearGuard extends Settings {

    /**
     * Gear securement system/replacement for wipers. We are totally trademarking Gear Guard.
     */
    DualActionSolenoidModule gearGuard = new DualActionSolenoidModule(GEARGUARD_1, GEARGUARD_2);

}