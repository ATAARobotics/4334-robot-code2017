package ca.fourthreethreefour;

import java.io.File;
import java.io.IOException;

import ca.fourthreethreefour.commands.ReverseDualActionSolenoid;
import ca.fourthreethreefour.settings.AutoFile;
import edu.first.command.Command;
import edu.first.command.Commands;
import edu.first.module.Module;
import edu.first.module.actuators.DualActionSolenoid;
import edu.first.module.actuators.DualActionSolenoid.Direction;
import edu.first.module.joysticks.BindingJoystick.DualAxisBind;
import edu.first.module.joysticks.BindingJoystick;
import edu.first.module.joysticks.XboxController;
import edu.first.module.subsystems.Subsystem;
import edu.first.robot.IterativeRobotAdapter;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobotAdapter {
    private final Subsystem AUTO_MODULES = new Subsystem(
            new Module[] { drive, bucket, gearGuard, tunedDrive });

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
        drivetrain.setExpiration(0.1);
        turningPID.setTolerance(TURN_TOLERANCE);
        distancePID.setTolerance(DISTANCE_TOLERANCE);

        controller1.addDeadband(XboxController.LEFT_FROM_MIDDLE, 0.20);
        controller1.changeAxis(XboxController.LEFT_FROM_MIDDLE, speedFunction);

        controller1.addDeadband(XboxController.RIGHT_X, 0.20);
        controller1.invertAxis(XboxController.RIGHT_X);
        controller1.changeAxis(XboxController.RIGHT_X, turnFunction);
        
        if (MANUAL_CONTROL) {
            controller1.addAxisBind(new DualAxisBind(controller1.getLeftDistanceFromMiddle(), controller1.getRightX()) {
                @Override
                public void doBind(double speed, double turn) {
                    if (turn == 0 && speed == 0) {
                        drivetrain.stopMotor();
                    } else {
                        turn += (speed > 0) ? DRIVE_COMPENSATION : 0;
                        drivetrain.arcadeDrive(speed, turn);
                    }
                }
            });
        } else {
            controller1.addAxisBind(new DualAxisBind(controller1.getLeftDistanceFromMiddle(), controller1.getRightX()) {
                boolean isTurning = true;
                
                @Override
                public void doBind(double speed, double turn) {
                    if (turn == 0) {
                        if (isTurning) {
                            isTurning = false;
                            turningPID.setSetpoint(navx.getAngle());
                        }

                        drivetrain.arcadeDrive(speed, turnOutput.get());
                    } else {
                        isTurning = true;
                        drivetrain.arcadeDrive(speed, turn);
                    }
                }
            });
        }
        

        controller1.addWhenPressed(XboxController.LEFT_BUMPER, new ReverseDualActionSolenoid(gearGuard));
        controller1.addWhenPressed(XboxController.RIGHT_BUMPER, new ReverseDualActionSolenoid(bucketSolenoid));
        controller1.addAxisBind(XboxController.RIGHT_TRIGGER, climberMotors);
    }
    
    private Command autoCommand;
    
    @Override
    public void initDisabled() {
//        allianceSwitch.enable();
    }
    
    @Override
    public void periodicDisabled() {
        if (AUTO_TYPE == "") { return; }
        String alliance = ""; /* AUTO_ALLIANCE_INDEPENDENT ? "" : (allianceSwitch.getPosition() ? "red-" : "blue-"); */
        try {
            autoCommand = new AutoFile(new File(alliance + AUTO_TYPE + ".txt")).toCommand();
        } catch (IOException e) {
            // try alliance independent as backup
            try {
                autoCommand = new AutoFile(new File(AUTO_TYPE + ".txt")).toCommand();
            } catch (IOException i) {
                throw new Error(e.getMessage());
            }
        }
        Timer.delay(1);
    }

    @Override
    public void initAutonomous() {
        AUTO_MODULES.enable();
        drivetrain.setSafetyEnabled(false);
        Commands.run(autoCommand);
        drivetrain.stopMotor();
    }

    @Override
    public void endAutonomous() {
        drivetrain.setSafetyEnabled(true);
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
        
        turningPID.enable();
    }

    @Override
    public void periodicTeleoperated() {
        controller1.doBinds();
        controller2.doBinds();
//        System.out.println(allianceSwitch.getPosition());

        if (gearGuard.get() == Direction.LEFT && bucketSolenoid.get() == Direction.LEFT) {
            indicator.set(edu.first.module.actuators.SpikeRelay.Direction.FORWARDS);
        } else {
            indicator.set(edu.first.module.actuators.SpikeRelay.Direction.OFF);
        }
        //SmartDashboard.putNumber("Turning PID", turningPID.get());
        //SmartDashboard.putNumber("Turning Error", turningPID.getError());
        //SmartDashboard.putNumber("Turning Setpoint", turningPID.getSetpoint());
        //SmartDashboard.putNumber("Encoder Rate", driveEncoder.getRate());
        //SmartDashboard.putNumber("Encoder Position", driveEncoder.getPosition());
        // SmartDashboard.putBoolean("Has Gear", bucketSwitch.getPosition());
    }

    @Override
    public void endTeleoperated() {
        TELEOP_MODULES.disable();
    }
}
