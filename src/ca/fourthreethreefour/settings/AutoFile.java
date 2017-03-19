package ca.fourthreethreefour.settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.fourthreethreefour.subsystems.Bucket;
import ca.fourthreethreefour.subsystems.Drive;
import ca.fourthreethreefour.subsystems.GearGuard;
import ca.fourthreethreefour.subsystems.TunedDrive;
import edu.first.command.Command;
import edu.first.commands.CommandGroup;
import edu.first.commands.common.LoopingCommand;
import edu.first.commands.common.SetOutput;
import edu.first.commands.common.WaitCommand;
import edu.wpi.first.wpilibj.DriverStation;

/*
 * driveSpeed = 0.43
 * foo = name
 * 1-CommandName:(0.5, -0.5, 1000L)
 * 2-OtherCommand:(0.5, driveSpeed, 1000L)
 * 3-Command:1
 * 4-!ConcurrentCommand:2
 * 5-!ConcurrentCommand:3
 * 
 * add any custom commands in the static {} block below, look at
 * PrintCommand for an example
 */
public class AutoFile extends SettingsFile implements Drive, TunedDrive {
    private static final long serialVersionUID = 5658050910302255585L;
    public static final HashMap<String, RuntimeCommand> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put("print", new PrintCommand());
        COMMANDS.put("drive", new DriveCommand());
        COMMANDS.put("driveDistance", new DriveDistanceCommand());
        COMMANDS.put("turn", new TurnCommand());
        COMMANDS.put("stop", new StopCommand());
        COMMANDS.put("wait", new Wait());
        COMMANDS.put("waitUntil", new WaitUntil());
        COMMANDS.put("deploybucket", new DeployBucket());
        COMMANDS.put("retractbucket", new RetractBucket());
        COMMANDS.put("closeguard", new CloseGuard());
        COMMANDS.put("openguard", new OpenGuard());
    }

    private static class PrintCommand implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            return new Command() {
                @Override
                public void run() {
                    System.out.println(args.toString());
                }
            };
        }
    }

    private static class DriveCommand implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            double left = Double.parseDouble(args.get(0));
            double right = Double.parseDouble(args.get(1));
            double timeout = Long.parseLong(args.get(2));

            if (args.size() != 3) {
                throw new IllegalArgumentException("Error in Drive: Invalid arguments");
            }

            return new LoopingCommand() {
                long start = 0;

                @Override
                public boolean continueLoop() {
                    if (start == 0) {
                        start = System.currentTimeMillis();
                        return timeout != 0;
                    } else {
                        return System.currentTimeMillis() - start < timeout;
                    }
                }

                @Override
                public void runLoop() {
                    drivetrain.set(left, right);
                }
            };
        }
    }

    private static class DriveDistanceCommand implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            int distance = Integer.parseInt(args.get(0));
            final int threshold = args.size() > 1 ? Integer.parseInt(args.get(1)) : 10;

            return new LoopingCommand() {
                double current = 0;
                int correctIterations = 0;

                @Override
                public boolean continueLoop() {
                    if (Math.abs(current - distance) < threshold) {
                        correctIterations++;
                    } else {
                        correctIterations = 0;
                    }

                    if (correctIterations >= threshold) {
                        distancePID.disable();
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public void firstLoop() {
                    distancePID.enable();
                    distancePID.setSetpoint(distance);
                }
                
                @Override
                public void runLoop() {
                    current = encoderInput.get();
                    double output = speedOutput.get();
                    drivetrain.set(output, output);
                }
            };
        }
    }

    private static class DriveStraightCommand implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            int distance = Integer.parseInt(args.get(0));
            final int threshold = args.size() > 1 ? Integer.parseInt(args.get(1)) : 10;

            return new LoopingCommand() {
                double current = 0;
                int correctIterations = 0;

                @Override
                public boolean continueLoop() {
                    if (Math.abs(current - distance) < threshold) {
                        correctIterations++;
                    } else {
                        correctIterations = 0;
                    }

                    if (correctIterations >= threshold) {
                        distancePID.disable();
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public void firstLoop() {
                    distancePID.enable();
                    distancePID.setSetpoint(distance);
                    turningPID.enable();
                    turningPID.setSetpoint(0);
                }
                
                @Override
                public void runLoop() {
                    current = encoderInput.get();
                    drivetrain.arcadeDrive(speedOutput.get(), turnOutput.get());
                }
            };
        }
    }

    private static class TurnCommand implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            int angle = Integer.parseInt(args.get(0));
            final int threshold = args.size() > 1 ? Integer.parseInt(args.get(1)) : 10;

            return new LoopingCommand() {
                double current = 0;
                int correctIterations = 0;

                @Override
                public boolean continueLoop() {
                    if (Math.abs(current - angle) < threshold) {
                        correctIterations++;
                    } else {
                        correctIterations = 0;
                    }

                    if (correctIterations >= threshold) {
                        turningPID.disable();
                        return false;
                    } else {
                        return true;
                    }
                }
                
                @Override
                public void firstLoop() {
                    turningPID.enable();
                    turningPID.setSetpoint(angle);
                }
                
                @Override
                public void runLoop() {
                    current = navx.getAngle();
                    try {
                        turningPID.wait(100);
                    } catch (InterruptedException e) {}
                    drivetrain.arcadeDrive(0, (turnOutput.get() * TURN_SPEED_COEFFICIENT));
                }
            };
        }
    }

    private static class StopCommand implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            return new SetOutput(drivetrain.getDriveStraight(), 0);
        }
    }

    private static class Wait implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            if (args.size() != 1) {
                throw new IllegalArgumentException("Error in Wait: Invalid arguments");
            } else {
                return new WaitCommand(Double.parseDouble(args.get(0)));
            }
        }
    }

    private static class WaitUntil implements RuntimeCommand {

        @Override
        public Command getCommand(List<String> args) {
            double time = Double.parseDouble(args.get(0));
            return new Command() {

                @Override
                public void run() {
                    while (DriverStation.getInstance().getMatchTime() < time) {
                        drivetrain.stopMotor();
                    }
                }
            };
        }
    }

    private static class DeployBucket implements RuntimeCommand, Bucket {
        @Override
        public Command getCommand(List<String> args) {
            return new Command() {
                @Override
                public void run() {
                    Bucket.bucketSolenoid.set(BUCKET_OUT);
                }
            };
        }
    }

    private static class RetractBucket implements RuntimeCommand, Bucket {
        @Override
        public Command getCommand(List<String> args) {
            return new Command() {
                @Override
                public void run() {
                    Bucket.bucketSolenoid.set(BUCKET_IN);
                }
            };
        }
    }

    private static class CloseGuard implements RuntimeCommand, GearGuard {
        @Override
        public Command getCommand(List<String> args) {
            return new Command() {
                @Override
                public void run() {
                    GearGuard.gearGuard.set(GEAR_GUARD_OUT);
                }
            };
        }
    }

    private static class OpenGuard implements RuntimeCommand, GearGuard {
        @Override
        public Command getCommand(List<String> args) {
            return new Command() {
                @Override
                public void run() {
                    GearGuard.gearGuard.set(GEAR_GUARD_IN);
                }
            };
        }
    }

    public AutoFile(File file) throws IOException {
        super(file);
    }

    public Command toCommand() {
        Map<String, String> variables = new HashMap<>();
        for (Map.Entry<Object, Object> e : entrySet()) {
            String key = e.getKey().toString(), value = e.getValue().toString();
            if (!key.contains("-")) {
                variables.put(key, value);
            }
        }

        ArrayList<AutoFileCommand> commands = new ArrayList<>();
        for (Map.Entry<Object, Object> e : entrySet()) {
            String key = e.getKey().toString(), value = e.getValue().toString();
            if (variables.containsKey(value)) {
                value = variables.get(value);
            }

            if (key.contains("-")) {
                commands.add(new AutoFileCommand(key, value));
            }
        }
        Collections.sort(commands);

        CommandGroup group = new CommandGroupFactory();
        for (AutoFileCommand command : commands) {
            if (COMMANDS.containsKey(command.name)) {
                if (command.concurrent) {
                    group.appendConcurrent(COMMANDS.get(command.name).getCommand(command.arguments));
                } else {
                    group.appendSequential(COMMANDS.get(command.name).getCommand(command.arguments));
                }
            } else {
                throw new Error(command.name + " not found");
            }
        }

        return group;
    }

    private static class AutoFileCommand implements Comparable<AutoFileCommand> {
        public final int index;
        public final boolean concurrent;
        public final String name;
        public final List<String> arguments;

        public AutoFileCommand(String key, String arguments) {
            this.index = Integer.parseInt(key.substring(0, key.indexOf('-')));
            String keyname = key.substring(key.indexOf('-') + 1).toLowerCase();
            if (keyname.startsWith("!")) {
                this.concurrent = true;
                this.name = keyname.substring(1);
            } else {
                this.concurrent = false;
                this.name = keyname;
            }

            String inner = arguments;
            if (arguments.contains("(") && arguments.contains(")")) {
                inner = arguments.substring(arguments.indexOf('(') + 1, arguments.indexOf(')'));
            }
            this.arguments = Arrays.asList(inner.split(",")).stream().map(String::trim).collect(Collectors.toList());
        }

        @Override
        public int compareTo(AutoFileCommand o) {
            return index - o.index;
        }
    }

    private interface RuntimeCommand {
        public Command getCommand(List<String> args);
    }

    private static class CommandGroupFactory extends CommandGroup {
        public CommandGroupFactory() {}
    }
}