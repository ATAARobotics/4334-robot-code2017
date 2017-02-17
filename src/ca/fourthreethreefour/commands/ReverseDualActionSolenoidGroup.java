package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.actuators.DualActionSolenoidModuleGroup;

public final class ReverseDualActionSolenoidGroup implements Command {
	
	private final DualActionSolenoidModuleGroup solenoid;
	public ReverseDualActionSolenoidGroup(DualActionSolenoidModuleGroup solenoid) {
		this.solenoid = solenoid;
	}

	@Override
	public void run() {
		solenoid.reverse();
	}
}
