package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.Module;

public class DisableModule implements Command {
	
	private final Module module;
	public DisableModule(Module module) {
		this.module = module;
	}
	
	@Override
	public void run() {
		module.disable();
	}

}
