package ca.fourthreethreefour;

import ca.fourthreethreefour.subsystems.Drive;
import edu.first.commands.CommandGroup;
import edu.first.commands.common.LoopingCommand;
import edu.first.commands.common.WaitCommand;
import edu.first.module.actuators.Drivetrain;

public class Autonomous extends CommandGroup implements Drive {
    public Autonomous() {
        appendSequential(new TimedDrive(drivetrain, 0.2, 0.2, 1000L));
        appendSequential(new WaitCommand(1));
    }

    class TimedDrive extends LoopingCommand {

        private Drivetrain drive;
        private double left, right;
        private long durationMS;
        private long start = 0;

        public TimedDrive(Drivetrain drive, double left, double right, long durationMS) {
            this.drive = drive;
            this.left = left;
            this.right = right;
            this.durationMS = durationMS;
        }

        @Override
        public boolean continueLoop() {
            return (System.currentTimeMillis() - start) < durationMS;
        }

        @Override
        public void runLoop() {
            if (start == 0) {
                start = System.currentTimeMillis();
            }

            drive.tankDrive(left, right);
        }
    }
}