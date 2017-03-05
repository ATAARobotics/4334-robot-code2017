package ca.fourthreethreefour.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.fourthreethreefour.subsystems.Bucket;
import ca.fourthreethreefour.subsystems.Drive;
import ca.fourthreethreefour.subsystems.GearGuard;
import edu.first.command.Command;
import edu.first.commands.CommandGroup;
import edu.first.commands.common.LoopingCommand;
import edu.first.commands.common.SetOutput;
import edu.first.commands.common.WaitCommand;
import edu.first.module.actuators.DualActionSolenoid.Direction;

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
public class AutoFile extends SettingsFile implements Drive {
    private static final long serialVersionUID = 5658050910302255585L;
    public static final HashMap<String, RuntimeCommand> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put("print", new PrintCommand());
        COMMANDS.put("drive", new DriveCommand());
        COMMANDS.put("stop", new StopCommand());
        COMMANDS.put("wait", new Wait());
        COMMANDS.put("deploy bucket", new DeployBucket());
        COMMANDS.put("retract bucket", new RetractBucket());
        COMMANDS.put("close guard", new CloseGuard());
        COMMANDS.put("open guard", new OpenGuard());
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
            double timeout = Double.parseDouble(args.get(2));

            if (args.size() != 3) {
                throw new IllegalArgumentException("Error in Drive: Invalid arguments");
            }

            return new LoopingCommand() {
                long start = 0;

                @Override
                public boolean continueLoop() {
                    if (start == 0) {
                        start = System.currentTimeMillis();
                        return start != 0;
                    } else {
                        return System.currentTimeMillis() - start > timeout;
                    }
                }

                @Override
                public void runLoop() {
                    drivetrain.set(left, right);
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
            }else {
                return new WaitCommand(Double.parseDouble(args.get(0)));
            }
        }
    }

    private static class DeployBucket implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            return new Command() {
                @Override
                public void run() {
                    Bucket.bucketSolenoid.set(Direction.RIGHT);
                }
            };
        }
    }

    private static class RetractBucket implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            return new Command() {
                @Override
                public void run() {
                    Bucket.bucketSolenoid.set(Direction.LEFT);
                }
            };
        }
    }

    private static class CloseGuard implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            return new Command() {
                @Override
                public void run() {
                    GearGuard.gearGuard.set(Direction.RIGHT);
                }
            };
        }
    }

    private static class OpenGuard implements RuntimeCommand {
        @Override
        public Command getCommand(List<String> args) {
            return new Command() {
                @Override
                public void run() {
                    GearGuard.gearGuard.set(Direction.LEFT);
                }
            };
        }
    }

    public AutoFile(File file) {
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
                System.err.println(command.name + " not found");
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
            this.arguments = Arrays.asList(inner.split(","));
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