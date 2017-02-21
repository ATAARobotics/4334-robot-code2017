package ca.fourthreethreefour;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousChooser {
	
	public static enum AutoMode {
		LEFT_PEG, CENTER_PEG, RIGHT_PEG
	}

	AutoMode mode;
	private final SendableChooser<AutoMode> autoChooser;
	
	public AutonomousChooser() {
		autoChooser = new SendableChooser<AutoMode>();
		
		autoChooser.addObject("Left", AutoMode.LEFT_PEG);
		autoChooser.addDefault("Center", AutoMode.CENTER_PEG);
		autoChooser.addObject("Right", AutoMode.RIGHT_PEG);
	}
	
	public void putChooser() {
		SmartDashboard.putData("Autonomous Mode", autoChooser);
	}
	
	public AutoMode getAutoMode() {
		return autoChooser.getSelected();
	}
}
