package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.actuators.DualActionSolenoidModuleGroup;

public final class ReverseDualActionSolenoidGroup implements Command {
	
	private final DualActionSolenoidModuleGroup solenoidGroup;
	
	/**
	 * Reverses a {@link DualActionSolenoidModuleGroup}.
	 * @param solenoidGroup the {@link DualActionSolenoidModuleGroup} to reverse.
	 */
	public ReverseDualActionSolenoidGroup(DualActionSolenoidModuleGroup solenoidGroup) {
		this.solenoidGroup = solenoidGroup;
	}

	@Override
	public void run() {
		solenoidGroup.reverse();
	}
}
