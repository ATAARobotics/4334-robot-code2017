package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.Module;

public class EnableModule implements Command {
	
	private final Module module;
	public EnableModule(Module module) {
		this.module = module;
	}
	
	@Override
	public void run() {
		module.enable();
	}

}
