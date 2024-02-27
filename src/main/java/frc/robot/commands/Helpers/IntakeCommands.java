package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.OperatorConstants;

import frc.robot.subsystems.Intake;

//dont unplug the ethernet
public class IntakeCommands extends Command{
    private Intake intake;

    /** Helper Commands For Intake
     *  @param module module to use as intake
     */
    public IntakeCommands(Intake module) {
        // Assign Control
        this.intake = module;

        // Add requirements
        addRequirements(this.intake);
    }



     /**
     * Shoots note out of intake by ramping up front till it reaches
     * the speed in OperatorConstants.FrontRPM 
     * 
     * Then Shoots the Note out of the Front of the intake
     * @return Command
     */ 
    public Command ShootForward() {
        return Commands.startEnd(() -> RampUp(),
                                 () -> Stop(), 
                                 intake);
    }    

    /** 
     * Runs intake Backward
     * Sets intake Speed to OperatorConstants FrontOut and BackOut
     * 
     * @return Command
    */
    public Command MoveBackward(){
        return Commands.startEnd(() -> intake.setSpeed(OperatorConstants.FrontOut, 0),
                                 () -> Stop(),
                                 intake);
    }

    /** 
     * Runs intake Forward
     * Sets intake Speed to OperatorConstants FrontOut and BackOut
     * 
     * @return Command
    */
    public Command MoveForward(){
    double setSpeed = OperatorConstants.IntakeSpeed;
        return Commands.startEnd(() -> intake.setSpeed(setSpeed, 0), 
                                () -> Stop(), 
                                intake);
    }

    /**
     * Intakes note
     * Sets intake Speed to OperatorConstants.FrontOut
     * 
     * @return Command
     */ 
    public Command IntakeNote() {
        return Commands.startEnd(() -> intake.setSpeed(-OperatorConstants.FrontOut, 0), 
                                 () -> Stop(),
                                 intake);
    }

    /** Stops the Intake when called 
     *  @return Command
    */
    public Command Stop() {
        return Commands.runOnce(() -> intake.stop(), intake);
    }



    /** Ramps up Front motor of intake, until it reaches the speed defined
    *   in operatorconstants.FrontRPM
    */
    private void RampUp() {
      //mhm yup boom
      while(intake.getFrontRPM() < OperatorConstants.FrontRPM) intake.setSpeed(OperatorConstants.FrontOut, 0);
      intake.setSpeed(OperatorConstants.FrontOut, -OperatorConstants.BackOut);
    }



    // Called when the command is initially scheduled.
    @Override
      public void initialize() {
        // Put Constants into Shuffleboard
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
