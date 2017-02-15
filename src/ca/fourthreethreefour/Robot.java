package ca.fourthreethreefour; //ca.4334 isn't an acceptable Java package identifier so typing out the numbers will work I guess

import ca.fourthreethreefour.commands.ReverseDualActionSolenoidGroup;
import edu.first.module.actuators.Drivetrain;
import edu.first.module.actuators.DualActionSolenoid;
import edu.first.module.actuators.DualActionSolenoidModule;
import edu.first.module.actuators.DualActionSolenoidModuleGroup;
import edu.first.module.actuators.VictorModule;
import edu.first.module.actuators.VictorModuleGroup;
import edu.first.module.joysticks.XboxController;
import edu.first.module.subsystems.Subsystem;
import edu.first.module.subsystems.SubsystemBuilder;
import edu.first.robot.IterativeRobotAdapter;

public class Robot extends IterativeRobotAdapter {
	
	VictorModule //TODO get the actual channels
		left1 = new VictorModule(0), 
		left2 = new VictorModule(1),
		left3 = new VictorModule(2), 
		
		right1 = new VictorModule(3),
		right2 = new VictorModule(4),
		right3 = new VictorModule(5);
	
	VictorModuleGroup left = new VictorModuleGroup(new VictorModule[] { left1, left2, left3 });
	VictorModuleGroup right = new VictorModuleGroup(new VictorModule[] { right1, right2, right3 });
	
	Drivetrain drivetrain = new Drivetrain(left, right);
	
	VictorModule 
		climber1 = new VictorModule(6), 
		climber2 = new VictorModule(7);
	
	VictorModuleGroup climber = new VictorModuleGroup(new VictorModule[] { climber1, climber2 });
	
	XboxController 
		controller1 = new XboxController(0),
		controller2 = new XboxController(1);
	
	DualActionSolenoidModule 
		unload1 = new DualActionSolenoidModule(0, 1),
		unload2 = new DualActionSolenoidModule(2, 3);
	
	DualActionSolenoidModuleGroup unload = new DualActionSolenoidModuleGroup
			(new DualActionSolenoidModule[] { unload1, unload2 });
	
	Subsystem ALL_MODULES = new SubsystemBuilder()
			.add(controller1)
			.add(controller2)
			.add(drivetrain)
				.add(left)
					.add(left1)
					.add(left2)
					.add(left3)
				.add(right)
					.add(right1)
					.add(right2)
					.add(right3)
			.add(unload)
				.add(unload1)
				.add(unload2)
			
			.toSubsystem();

	public Robot(String name) {
		super(name);
	}

	@Override
	public void init() {
		ALL_MODULES.init();
		
		controller1.addDeadband(XboxController.LEFT_FROM_MIDDLE, 0.15);
		controller1.addDeadband(XboxController.RIGHT_FROM_MIDDLE, 0.15);
		
		controller1.addAxisBind(
				drivetrain.getTank(
						controller1.getLeftDistanceFromMiddle(), 
						controller1.getRightDistanceFromMiddle()));
		
		controller2.addWhenPressed(XboxController.A, new ReverseDualActionSolenoidGroup(unload)); //TODO see if this actually does anything
		controller2.addAxisBind(XboxController.RIGHT_TRIGGER, climber);
	}
	
	@Override
	public void initAutonomous() {
		ALL_MODULES.enable();
		unload.set(DualActionSolenoid.Direction.LEFT);
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
		controller2.doBinds();
	}
}
