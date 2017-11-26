package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.actuators.DualActionSolenoidModule;

public final class ReverseDualActionSolenoid implements Command {
	
	private final DualActionSolenoidModule solenoid;
	public ReverseDualActionSolenoid(DualActionSolenoidModule solenoid) {
		this.solenoid = solenoid;
	}

	@Override
	public void run() {
		solenoid.reverse();
	}
}
