package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.actuators.DualActionSolenoid;
import edu.first.module.actuators.SolenoidModule;

public final class ReverseSolenoid implements Command {

    private final DualActionSolenoid dualSolenoid;
    private final SolenoidModule singleSolenoid;

    public ReverseSolenoid(DualActionSolenoid solenoid) {
        this.dualSolenoid = solenoid;
        this.singleSolenoid = null;
    }
    
    public ReverseSolenoid(SolenoidModule solenoid) {
        this.dualSolenoid = null;
        this.singleSolenoid = solenoid;
    }

    @Override
    public void run() {
        if (dualSolenoid != null) {
            dualSolenoid.reverse();
        }
        if (singleSolenoid != null) {
            singleSolenoid.setPosition(!singleSolenoid.getPosition());
        }
    }
}
