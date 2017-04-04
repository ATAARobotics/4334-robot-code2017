package ca.fourthreethreefour.commands.debug;

import ca.fourthreethreefour.settings.Settings;
import edu.first.command.Command;

public class PrintFConsole implements Command, Settings {
    
    private String key;
    private double value;
    private boolean newline;

    public PrintFConsole(String key, double value) {
        this.key = key;
        this.value = value;
        this.newline = true;
    }
    
    public PrintFConsole(String key, double value, boolean newline) {
        this.key = key;
        this.value = value;
        this.newline = newline;
    }
    
    @Override
    public void run() {
        if (LOGGING_ENABLED) {
            System.out.printf(key + ": %f" + (newline ? "\n" : ", "), value);
        }
    }

}
