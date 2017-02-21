package ca.fourthreethreefour; //ca.4334 isn't an acceptable Java package identifier so typing out the numbers will work I guess

import ca.fourthreethreefour.commands.DisableModule;
import ca.fourthreethreefour.commands.EnableModule;
import ca.fourthreethreefour.commands.ReverseDualActionSolenoid;
import ca.fourthreethreefour.commands.RunPID;
import ca.fourthreethreefour.commands.RunServo;
import edu.first.identifiers.Function;
import edu.first.module.Module;
import edu.first.module.actuators.DualActionSolenoid;
import edu.first.module.joysticks.XboxController;
import edu.first.module.subsystems.Subsystem;
import edu.first.robot.IterativeRobotAdapter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobotAdapter {

	private final Subsystem AUTO_MODULES = new Subsystem(new Module[] {
		drive, climber, unload, wipers 
	});
	
	private final Subsystem TELEOP_MODULES = new Subsystem(new Module[] {
		drive, climber, unload, unloadLimitSwitch, wipers, controllers
	});
	
	private final Subsystem ALL_MODULES = new Subsystem(new Module[] {
		AUTO_MODULES, TELEOP_MODULES, wipersPID
	});

	public Robot(String name) {
		super(name);
	}

	@Override
	public void init() {
		ALL_MODULES.init();
		
		controller1.addDeadband(XboxController.LEFT_FROM_MIDDLE, 0.20);
		controller1.addDeadband(XboxController.RIGHT_X, 0.20);
		controller1.invertAxis(XboxController.RIGHT_X);
		controller1.changeAxis(XboxController.LEFT_FROM_MIDDLE, new Function() {
			@Override
			public double F(double in) {
				return in > 0 ? in * in : -(in * in);
			}
		});
		
		controller1.addAxisBind(
				drivetrain.getArcade(
						controller1.getLeftDistanceFromMiddle(), 
						controller1.getRightX()));

		controller1.addWhenPressed(XboxController.A, new ReverseDualActionSolenoid(unloadSolenoid));
		//controller2.addAxisBind(XboxController.RIGHT_TRIGGER, climberMotors);
		
		//TODO get setpoint for PID and put it in Settings
		//controller2.addWhenPressed(XboxController.B, new RunPID(wiper1PID));
		//controller2.addWhenPressed(XboxController.B, new RunPID(wiper2PID));
		//controller2.addWhenReleased(XboxController.B, new DisableModule(wiper1PID));
		//controller2.addWhenReleased(XboxController.B, new DisableModule(wiper2PID));

	}
	
	@Override
	public void initAutonomous() {
		AUTO_MODULES.enable();
		unloadSolenoid.set(DualActionSolenoid.Direction.LEFT);
	}
	
	@Override
	public void initTeleoperated() {
		ALL_MODULES.enable();
	}
	
	@Override
	public void endTeleoperated() {
		ALL_MODULES.disable();
	}
	
	@Override
	public void periodicTeleoperated() {
		controller1.doBinds();
		//controller2.doBinds();
		
		//SmartDashboard.putBoolean("Has Gear", unloadLimitSwitch.getPosition());
	}
}
