package ca.fourthreethreefour;

import java.io.File;

import ca.fourthreethreefour.commands.ReverseDualActionSolenoid;
import ca.fourthreethreefour.settings.AutoFile;
import ca.fourthreethreefour.subsystems.Switches;
import edu.first.command.Command;
import edu.first.command.Commands;
import edu.first.identifiers.InversedSpeedController;
import edu.first.module.Module;
import edu.first.module.actuators.DualActionSolenoid;
import edu.first.module.actuators.DualActionSolenoid.Direction;
import edu.first.module.joysticks.BindingJoystick.DualAxisBind;
import edu.first.module.joysticks.XboxController;
import edu.first.module.subsystems.Subsystem;
import edu.first.robot.IterativeRobotAdapter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobotAdapter {

    private final Subsystem AUTO_MODULES = new Subsystem(
            new Module[] { drive, bucket, gearGuard });

    private final Subsystem TELEOP_MODULES = new Subsystem(
            new Module[] { drive, climber, bucket, gearGuard, indicator, controllers });

    private final Subsystem ALL_MODULES = new Subsystem(new Module[] { AUTO_MODULES, TELEOP_MODULES });

    public Robot() {
        super("ATA 2017");
    }

    @Override
    public void init() {
        if (ROBOT_TYPE == "") {
            throw new Error("No ROBOT_TYPE set, please set a robot type in the settings file");
        }
        
        if(ROBOT_TYPE == "Practice") {
            throw new Error("Wrong code deploy, dummy");
        }

        ALL_MODULES.init();

        controller1.addDeadband(XboxController.LEFT_FROM_MIDDLE, 0.20);
        controller1.changeAxis(XboxController.LEFT_FROM_MIDDLE, speedFunction);

        controller1.addDeadband(XboxController.RIGHT_X, 0.20);
        controller1.invertAxis(XboxController.RIGHT_X);
        controller1.changeAxis(XboxController.RIGHT_X, turnFunction);

        controller1.addAxisBind(new DualAxisBind(controller1.getLeftDistanceFromMiddle(), controller1.getRightX()) {
            @Override
            public void doBind(double speed, double turn) {
                turn += (speed > 0) ? DRIVE_COMPENSATION : -DRIVE_COMPENSATION;
                drivetrain.arcadeDrive(speed, turn);
            }
        });

        controller1.addWhenPressed(XboxController.LEFT_BUMPER, new ReverseDualActionSolenoid(gearGuard));
        controller1.addWhenPressed(XboxController.RIGHT_BUMPER, new ReverseDualActionSolenoid(bucketSolenoid));
<<<<<<< HEAD
        controller1.addAxisBind(XboxController.RIGHT_TRIGGER, climberMotors);
=======
        controller1.addAxisBind(XboxController.RIGHT_TRIGGER, new InversedSpeedController(climberMotors));
        
        driveEncoder.setDistancePerPulse(1);
>>>>>>> 708e14d... Adjustments to autofile implementation
    }
    
    private Command autoCommand;
    
    @Override
    public void initDisabled() {
        autoSwitch.enable();
    }
    
    @Override
    public void periodicDisabled() {
        String alliance = AUTO_ALLIANCE_INDEPENDANT ? "" : (autoSwitch.getPosition() ? "red" : "blue");
        autoCommand = new AutoFile(new File(alliance + "-" + AUTO_TYPE + ".txt")).toCommand();
        Timer.delay(1);
    }

    @Override
    public void initAutonomous() {
        AUTO_MODULES.enable();
        Commands.run(autoCommand);
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
        if (gearGuard.get() == Direction.OFF) {
            gearGuard.set(DualActionSolenoid.Direction.LEFT);
        }
    }

    @Override
    public void periodicTeleoperated() {
        controller1.doBinds();
        controller2.doBinds();

        if (gearGuard.get() == Direction.LEFT && bucketSolenoid.get() == Direction.LEFT) {
            indicator.set(edu.first.module.actuators.SpikeRelay.Direction.FORWARDS);
        } else {
            indicator.set(edu.first.module.actuators.SpikeRelay.Direction.OFF);
        }

        SmartDashboard.putNumber("Encoder Rate", driveEncoder.getRate());
        SmartDashboard.putNumber("Encoder Position", driveEncoder.getPosition());
        // SmartDashboard.putBoolean("Has Gear", bucketSwitch.getPosition());
    }

    @Override
    public void endTeleoperated() {
        TELEOP_MODULES.disable();
    }
}
