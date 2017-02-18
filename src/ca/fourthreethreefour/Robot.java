package ca.fourthreethreefour; //ca.4334 isn't an acceptable Java package identifier so typing out the numbers will work I guess

import ca.fourthreethreefour.commands.ReverseDualActionSolenoidGroup;
import edu.first.module.Module;
import edu.first.module.actuators.Drivetrain;
import edu.first.module.actuators.DualActionSolenoid;
import edu.first.module.actuators.DualActionSolenoidModule;
import edu.first.module.actuators.DualActionSolenoidModuleGroup;
import edu.first.module.actuators.VictorModule;
import edu.first.module.actuators.VictorModuleGroup;
import edu.first.module.controllers.PIDController;
import edu.first.module.joysticks.XboxController;
import edu.first.module.sensors.AnalogInput;
import edu.first.module.sensors.DigitalInput;
import edu.first.module.subsystems.Subsystem;
import edu.first.robot.IterativeRobotAdapter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		unloader1 = new DualActionSolenoidModule(0, 1),
		unloader2 = new DualActionSolenoidModule(2, 3);
	
	DualActionSolenoidModuleGroup unloader = new DualActionSolenoidModuleGroup
			(new DualActionSolenoidModule[] { unloader1, unloader2 });
	
	DigitalInput unloadLimitSwitch = new DigitalInput(0); //TODO get actual channel
	
	
	VictorModule 
		wiper1 = new VictorModule(8),
		wiper2 = new VictorModule(9);
	
	AnalogInput //potentiometers
		wiper1Pot = new AnalogInput(0),
		wiper2Pot = new AnalogInput(1);
	
	PIDController 
		wiper1PID = new PIDController(wiper1Pot, wiper1),
		wiper2PID = new PIDController(wiper2Pot, wiper2);
	
	Subsystem drive = new Subsystem(new Module[] { drivetrain, left, left1, left2, left3, right, right1, right2, right3 });
	Subsystem climb = new Subsystem(new Module[] { climber, climber1, climber2 });
	Subsystem unload = new Subsystem(new Module[] { unloader, unloader1, unloader2 });
	Subsystem wipers = new Subsystem(new Module[] { wiper1, wiper2, wiper1Pot, wiper2Pot, wiper1PID, wiper2PID });
	Subsystem controllers = new Subsystem(new Module[] { controller1, controller2 });
	
	private final Subsystem AUTO_MODULES = new Subsystem(new Module[] {
		drive, climber, unload, wipers 
	});
	
	private final Subsystem ALL_MODULES = new Subsystem(new Module[] {
		AUTO_MODULES, controllers 
	});

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
		
		controller2.addWhenPressed(XboxController.A, new ReverseDualActionSolenoidGroup(unloader)); //TODO see if this actually does anything
		controller2.addAxisBind(XboxController.RIGHT_TRIGGER, climber);
		//TODO add PID (get setpoint and P, I and D coefficients)

	}
	
	@Override
	public void initAutonomous() {
		AUTO_MODULES.enable();
		unloader.set(DualActionSolenoid.Direction.LEFT);
	}
	
	@Override
	public void initTeleoperated() {
		ALL_MODULES.enable();
		unload.set(DualActionSolenoid.Direction.LEFT);
	}
	
	@Override
	public void endTeleoperated() {
		ALL_MODULES.disable();
	}
	
	@Override
	public void periodicTeleoperated() {
		controller1.doBinds();
		controller2.doBinds();
		
		SmartDashboard.putBoolean("Has Gear", unloadLimitSwitch.getPosition());
	}
}
