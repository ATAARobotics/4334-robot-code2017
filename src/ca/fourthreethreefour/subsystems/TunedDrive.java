package ca.fourthreethreefour.subsystems;

import com.kauailabs.navx.frc.AHRS;

import ca.fourthreethreefour.commands.debug.Logging;
import edu.first.identifiers.Input;
import edu.first.identifiers.Output;
import edu.first.module.Module;
import edu.first.module.controllers.PIDController;
import edu.first.module.sensors.Encoder;
import edu.first.module.sensors.EncoderModule;
import edu.first.module.sensors.EncoderModule.InputType;
import edu.first.module.subsystems.Subsystem;
import edu.wpi.first.wpilibj.SPI;

public interface TunedDrive extends Drive {
    EncoderModule leftEncoder = new EncoderModule(LEFT_ENCODER_1, LEFT_ENCODER_2, InputType.DISTANCE);
    EncoderModule rightEncoder = new EncoderModule(RIGHT_ENCODER_1, RIGHT_ENCODER_2, InputType.DISTANCE);
    
    AHRS navx = new AHRS(SPI.Port.kMXP);

    public class DualEncoderInput implements Input {
        Encoder left, right;

        public DualEncoderInput(Encoder left, Encoder right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public double get() {
            Logging.logf("left %f right %f\n", left.get(), right.get());
            return right.get() + left.get();
        }
    }

    public interface InOut extends Input, Output {
    }

    public class VolatileInOut implements InOut {
        private volatile double currentValue = 0;

        @Override
        public void set(double value) {
            currentValue = value;
        }

        @Override
        public double get() {
            return currentValue;
        }
    }

    Input encoderInput = new DualEncoderInput(leftEncoder, rightEncoder);
    Input turnInput = new Input() {
        @Override
        public double get() {
            return navx.getAngle();
        }
    };

    InOut speedOutput = new VolatileInOut();
    InOut turnOutput = new VolatileInOut();

    PIDController distancePID = new PIDController(encoderInput, speedOutput, DISTANCE_P, DISTANCE_I, DISTANCE_D);
    PIDController turningPID = new PIDController(turnInput, turnOutput, TURN_P, TURN_I, TURN_D);

    Subsystem tunedDrive = new Subsystem(new Module[] { leftEncoder, rightEncoder });
}