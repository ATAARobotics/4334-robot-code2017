package ca.fourthreethreefour;

import ca.fourthreethreefour.commands.ReverseDualActionSolenoid;
import edu.first.command.Commands;
import edu.first.commands.common.SetOutput;
import edu.first.identifiers.InversedSpeedController;
import edu.first.module.Module;
import edu.first.module.actuators.DualActionSolenoid;
import edu.first.module.actuators.DualActionSolenoid.Direction;
import edu.first.module.joysticks.BindingJoystick.DualAxisBind;
import edu.first.module.joysticks.XboxController;
import edu.first.module.subsystems.Subsystem;
import edu.first.robot.IterativeRobotAdapter;

public class Robot extends IterativeRobotAdapter {

    private final Subsystem AUTO_MODULES = new Subsystem(
            new Module[] { drive, climber, bucket, wipers });

    private final Subsystem TELEOP_MODULES = new Subsystem(
            new Module[] { drive, climber, bucket, bucketSwitch, wipers, controllers });

    private final Subsystem ALL_MODULES = new Subsystem(
            new Module[] { AUTO_MODULES, TELEOP_MODULES });

    public Robot() {
        super("ATA 2017");
    }

    @Override
    public void init() {
        ALL_MODULES.init();

        controller1.addDeadband(XboxController.LEFT_FROM_MIDDLE, 0.20);
        controller1.changeAxis(XboxController.LEFT_FROM_MIDDLE, speedFunction);

        controller1.addDeadband(XboxController.RIGHT_X, 0.20);
        controller1.invertAxis(XboxController.RIGHT_X);
        controller1.changeAxis(XboxController.RIGHT_X, turnFunction);

        controller1.addAxisBind(new DualAxisBind(controller1.getLeftDistanceFromMiddle(),
                                                 controller1.getRightX()) {
            @Override
            public void doBind(double speed, double turn) {
                turn += (speed > 0) ? DRIVE_COMPENSATION : -DRIVE_COMPENSATION;
                drivetrain.arcadeDrive(speed, turn);
            }
        });

        controller1.addWhenPressed(XboxController.A, new ReverseDualActionSolenoid(bucketSolenoid));
        controller1.addWhilePressed(XboxController.B, new SetOutput(wiper1, 0.5));
        controller1.addWhilePressed(XboxController.B, new SetOutput(wiper2, -0.5));
        controller1.addWhilePressed(XboxController.X, new SetOutput(wiper1, 0.5));
        controller1.addWhilePressed(XboxController.X, new SetOutput(wiper2, -0.5));
        controller1.addAxisBind(XboxController.RIGHT_TRIGGER, new InversedSpeedController(climberMotors));
    }

    @Override
    public void initAutonomous() {
        AUTO_MODULES.enable();
        
        Commands.run(new Autonomous());
    }
    
    @Override
    public void endAutonomous() {
        AUTO_MODULES.disable();
    }

    @Override
    public void initTeleoperated() {
        TELEOP_MODULES.enable();
        if (bucketSolenoid.get() == Direction.OFF) {
            bucketSolenoid.set(DualActionSolenoid.Direction.LEFT);
        }
    }

    @Override
    public void periodicTeleoperated() {
        controller1.doBinds();
        controller2.doBinds();
    }

    @Override
    public void endTeleoperated() {
        TELEOP_MODULES.disable();
    }
}
