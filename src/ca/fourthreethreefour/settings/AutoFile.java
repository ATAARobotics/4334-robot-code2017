package ca.fourthreethreefour.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.first.command.Command;
import edu.first.commands.CommandGroup;

/*
 * 1-CommandName:(0.5, -0.5, 1000L)
 * 2-OtherCommand:(0.5, -0.5, 1000L)
 * 3-Command:1
 * 
 * add any custom commands in the static {} block below, look at
 * PrintCommand for an example
 */
public class AutoFile extends SettingsFile {
    private static final long serialVersionUID = 5658050910302255585L;
    public static final HashMap<String, RuntimeCommand> COMMANDS = new HashMap<>();
    
    static {
       COMMANDS.put("print", new PrintCommand());
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

    public AutoFile(File file) {
        super(file);
    }
    
    public Command toCommand() {
       ArrayList<AutoFileCommand> commands = new ArrayList<>();
       for (Map.Entry<Object, Object> e : entrySet()) {
           commands.add(new AutoFileCommand(e.getKey().toString(), e.getValue().toString()));
       }
       Collections.sort(commands);
       
       CommandGroup group = new CommandGroupFactory();
       for (AutoFileCommand command : commands) {
           if (COMMANDS.containsKey(command.name)) {
               group.appendSequential(COMMANDS.get(command.name).getCommand(command.arguments));
           } else {
               System.err.println(command.name + " not found");
           }
       }
       
       return group;
    }
    
    private static class AutoFileCommand implements Comparable<AutoFileCommand> {
        public final int index;
        public final String name;
        public final List<String> arguments;

        public AutoFileCommand(String key, String arguments) {
            this.index = Integer.parseInt(key.substring(0, key.indexOf('-')));
            this.name = key.substring(key.indexOf('-') + 1);
            
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