package ca.fourthreethreefour.commands;

import edu.first.command.Command;
import edu.first.module.actuators.SpeedController;

/**
 * Sets the specified {@link SpeedController} to the value provided. 
 * Why you would want to run a speed controller on a set value is 
 * anyone's guess.
 * @author Satan
 * @since 1867
 */
public class RunSpeedController implements Command {
	
	private final SpeedController speedController;
	private final double value;
	
	/**
	 * Sets the speed of the specified {@link SpeedController} to the 
	 * given value. Why would you want this?
	 * @param speedController the {@link SpeedController} to use
	 * @param value the speed to set the {@link SpeedController} to
	 */
	public RunSpeedController(SpeedController speedController, double value) {
		this.speedController = speedController;
		this.value = value;
	}

	@Override
	public void run() {
		speedController.setSpeed(value);
	}

}
