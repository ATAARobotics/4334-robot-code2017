package ca.fourthreethreefour.commands.debug;

import ca.fourthreethreefour.settings.Settings;
import edu.first.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SendSmartDashboard implements Command, Settings {
    
    private String key;
    private double value;
    
    public SendSmartDashboard(String key, double value) {
        if (LOGGING_ENABLED) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void run() {
        SmartDashboard.putNumber(key, value);
    }

}
