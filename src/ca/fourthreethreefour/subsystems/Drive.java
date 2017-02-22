package ca.fourthreethreefour.subsystems;

import edu.first.identifiers.Function;
import edu.first.identifiers.InversedSpeedController;
import edu.first.module.Module;
import edu.first.module.actuators.Drivetrain;
import edu.first.module.actuators.VictorModule;
import edu.first.module.actuators.VictorModuleGroup;
import edu.first.module.subsystems.Subsystem;

public interface Drive extends Settings {

    VictorModuleGroup left = new VictorModuleGroup(new VictorModule[] { new VictorModule(DRIVE_LEFT_1),
            new VictorModule(DRIVE_LEFT_2), new VictorModule(DRIVE_LEFT_3) });

    VictorModuleGroup right = new VictorModuleGroup(new VictorModule[] { new VictorModule(DRIVE_RIGHT_1),
            new VictorModule(DRIVE_RIGHT_2), new VictorModule(DRIVE_RIGHT_3) });

    Drivetrain drivetrain = new Drivetrain(new InversedSpeedController(left), right);

    Function speedFunction = new Function() {
        @Override
        public double F(double in) {
            return in > 0 ? in * in : -(in * in);
        }
    };
    
    Function turnFunction = new Function() {
        @Override
        public double F(double in) {
            double turn = in > 0 ? Math.pow(in, TURN_CURVE) : -Math.abs(Math.pow(in, TURN_CURVE));    
            return turn * TURN_CONSTANT;
        }
    };

    Subsystem drive = new Subsystem(new Module[] { drivetrain, left, right });
}