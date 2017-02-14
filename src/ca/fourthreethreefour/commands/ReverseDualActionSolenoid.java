package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.actuators.DualActionSolenoid;

public final class ReverseDualActionSolenoid implements Command {
	
	private final DualActionSolenoid solenoid;
	public ReverseDualActionSolenoid(DualActionSolenoid solenoid) {
		this.solenoid = solenoid;
	}

	@Override
	public void run() {
		solenoid.reverse();
	}
}
