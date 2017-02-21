package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.controllers.PIDController;

/**
 * Enables the specified {@link PIDController} and runs it.
 */
public class RunPID implements Command {
	
	private final PIDController PIDcontroller;
	
	public RunPID(PIDController PIDcontroller) {
		this.PIDcontroller = PIDcontroller;
	}

	@Override
	public void run() {
		PIDcontroller.enable();
		PIDcontroller.run();
	}

}
