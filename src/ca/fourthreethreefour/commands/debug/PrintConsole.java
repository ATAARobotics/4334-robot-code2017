package ca.fourthreethreefour.commands.debug;

import ca.fourthreethreefour.settings.Settings;
import edu.first.command.Command;

public class PrintConsole implements Command, Settings {

    private String text;
    
    public PrintConsole(String text) {
        this.text = text;
    }
    
    @Override
    public void run() {
        if (LOGGING_ENABLED) {
            System.out.println(text);
        }
    }

}
