package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.actuators.ServoModule;

/**
 * Sets the specified {@link ServoModule} to 0.5.
 * @author Satan
 * @since 1867
 */
public class RunServo implements Command {
	
	private final ServoModule servo;
	private final double value;
	
	public RunServo(ServoModule servo, double value) {
		this.servo = servo;
		this.value = value;
	}

	@Override
	public void run() {
		servo.set(value);
	}

}
