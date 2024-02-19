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
     * Shoots note
     * Ramps up speeed by using a natural log function
     * @speed Speed provided by controller 
     * @Constants.OutakeSpeed provides max achievable speed
     */ 
    public void Shoot(Double SpeedFront, double SpeedBack ) {
        Double frontSpeed = OperatorConstants.FrontOut*Math.log(SpeedFront);
        Double backSpeed = OperatorConstants.BackOut*Math.log(SpeedBack);
        intake.setSpeed(frontSpeed, backSpeed);
        
    }

    /**
     * Intakes note
     * Ramps up speeed by using a natural log function
     * @speed Speed provided by controller 
     * @Constants.IntakeSpeed provides max achievable speed
     */ 
    public void Intake(Double Speed) {
        Double setSpeed = -OperatorConstants.IntakeSpeed*Math.log(Speed);
        intake.setSpeed(setSpeed, setSpeed);
    }


    /**
     * Stops and resets the Intake's status when called
     */
    public void Stop() {
        intake.stop();
    }

      // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(left.getAsDouble() < 0) { Intake(left.getAsDouble()); }
        else if(right.getAsDouble() < 0) { Shoot(right.getAsDouble()); }
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