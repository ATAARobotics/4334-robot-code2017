package ca.fourthreethreefour; //ca.4334 isn't an acceptable Java package identifier so typing out the numbers will work I guess

import edu.first.module.actuators.Drivetrain;
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
	
	XboxController controller = new XboxController(0);
	
	Subsystem ALL_MODULES = new SubsystemBuilder()
			.add(controller)
			.add(drivetrain)
				.add(left)
					.add(left1)
					.add(left2)
					.add(left3)
				.add(right)
					.add(right1)
					.add(right2)
					.add(right3)
			
			.toSubsystem();

	public Robot(String name) {
		super(name);
	}

	@Override
	public void init() {
		ALL_MODULES.init();
		
		controller.addDeadband(XboxController.LEFT_FROM_MIDDLE, 0.15);
		controller.addDeadband(XboxController.RIGHT_FROM_MIDDLE, 0.15);
		
		controller.addAxisBind(
				drivetrain.getTank(
						controller.getLeftDistanceFromMiddle(), 
						controller.getRightDistanceFromMiddle()));
	}
	
	@Override
	public void initAutonomous() {
		ALL_MODULES.enable();
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
		controller.doBinds();
		drivetrain.tankDrive(controller.getLeftYValue(), controller.getRightYValue());
	}
}
