package frc.robot.commands.intake;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;

import frc.robot.subsystems.Intake;

//dont unplug the ethernet
public class IntakeShoot extends Command {
    private Intake intake;

    private DoubleSupplier left;
    private DoubleSupplier right;

    private boolean inuse = false;


    /**
     *  Command providing Basic Intake Control by Operator Controller
     * @param module module to use as note intake
     * @param operator_left Supplier for operator Joystick used for Intaking
     * @param operator_right Supplier for operator Joystick used for Shooting
     */
    public IntakeShoot(Intake module, DoubleSupplier operator_left, DoubleSupplier operator_right) {
        this.intake = module;
        this.left = operator_left;
        this.right = operator_right;

        addRequirements(this.intake);
    }

   /**
     * Shoots note from intake when called
     */
    public void Shoot() {
        if(!inuse) {
            inuse = true;
            intake.setSpeed(OperatorConstants.IntakeSpeedTop, OperatorConstants.IntakeSpeedBottom);
        }
    }

    /**
     * Intakes note when called
     */
    public void Intake() {
        if(!inuse) {
            inuse = true;
            intake.setSpeed(-OperatorConstants.IntakeSpeedTop, -OperatorConstants.IntakeSpeedBottom);
        }
    }


    /**
     * Stops and resets the Intake's status when called
     */
    public void Stop() {
        inuse = false;
        intake.stop();
    }

      // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(left.getAsDouble() > 0) { Intake(); }
        else if(right.getAsDouble() > 0) { Shoot(); }
        else { Stop(); }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}