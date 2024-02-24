package frc.robot.commands.Helpers;
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
    int rollerState = 0;
    public void Shoot() {
        Double frontSpeed = OperatorConstants.FrontOut;
        Double backSpeed = OperatorConstants.BackOut;

       //mhm yup boom

    if(intake.getFrontRPM() > OperatorConstants.FrontRPM){
        rollerState = 1;
    }
    if(rollerState == 0){
          intake.setSpeed(frontSpeed, 0);
       }
    if(rollerState == 1){
        intake.setSpeed(frontSpeed, -backSpeed);
    }
}
public void ShootBack(){
    double frontSpeed = OperatorConstants.FrontOut;
    Double backspeed = OperatorConstants.BackOut;
    intake.setSpeed(-frontSpeed, backspeed);
}
public void ShootFront(){
double frontSpeed = OperatorConstants.FrontOut;
double backspeed = OperatorConstants.BackOut;
intake.setSpeed(frontSpeed, -backspeed);
}
    /**
     * Intakes note
     * Ramps up speeed by using a natural log function
     * @speed Speed provided by controller 
     * @Constants.IntakeSpeed provides max achievable speed
     */ 
    public void Intake() {
        Double setSpeed = OperatorConstants.IntakeSpeed;
        intake.setSpeed(-setSpeed, 0);
    }


    /**
     * Stops and resets the Intake's status when called
     */
    //TODO: make into command
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
        if (right.getAsDouble() < 0) { Shoot(); }
        else if(left.getAsDouble() < 0) { Intake(); }
        else if (left.getAsDouble() > 0) { ShootBack(); }
        else if (right.getAsDouble() > 0) { ShootFront(); }
        else rollerState = 0; Stop();
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
