package ca.fourthreethreefour;

import ca.fourthreethreefour.subsystems.Drive;
import ca.fourthreethreefour.subsystems.Unload;
import edu.first.module.actuators.DualActionSolenoid.Direction;

public class Autonomous implements Runnable {
	
	int autoLoopCounter = 0; //TODO use encoders instead of this counter

	@Override
	public void run() {
		Unload.unloadSolenoid.set(Direction.LEFT);
		boolean hasDeployed = false;
		
		while(autoLoopCounter <= 150) {
		Drive.drivetrain.drive(0.5, 0);
		autoLoopCounter++;
		}
		
		if((autoLoopCounter >= 150) && (hasDeployed = false)) {
			Unload.unloadSolenoid.reverse();
			hasDeployed = true;
		} else {
			
		}
		
		while((autoLoopCounter <= 200) && (autoLoopCounter >= 150)) {
			Drive.drivetrain.drive(-0.5, 0);
			autoLoopCounter++;
		}
		
	}

}
