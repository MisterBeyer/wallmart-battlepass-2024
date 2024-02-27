package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.OperatorConstants;

import frc.robot.subsystems.Intake;

//dont unplug the ethernet
public class IntakeCommands{
    private Intake intake;

    /** Helper Commands For Intake
     *  @param module module to use as intake
     */
    public IntakeCommands(Intake module) {
        // Assign Control
        this.intake = module;

        // ShuffleBoard!
        SmartDashboard.putNumber("Intake/Front Motor Speed", OperatorConstants.FrontOut);
        SmartDashboard.putNumber("Intake/Rear Motor Speed", OperatorConstants.BackOut);
        SmartDashboard.putNumber("Operator/Intake [Front] Goal RPM", OperatorConstants.FrontRPM); 
        //SmartDashboard.putNumber("Operator/Intake [Back] Goal RPM", OperatorConstants.BackRPM);
    }



    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        // Motor Speed Posistions
        OperatorConstants.FrontOut = SmartDashboard.getNumber("Intake/Front Motor Speed", OperatorConstants.FrontOut);
        OperatorConstants.BackOut  = SmartDashboard.getNumber("Intake/Rear Motor Speed", OperatorConstants.BackOut);

        // RPM Goals
        OperatorConstants.FrontRPM = SmartDashboard.getNumber("Operator/Intake [Front] Goal RPM", OperatorConstants.FrontRPM); 
        //OperatorConstants.BackRPM  = SmartDashboard.getNumber("Operator/Intake [Back] Goal RPM", OperatorConstants.BackRPM); 

        // Update Constants of Subsystems
        intake.updateConstants();
    }


     /**
     * Shoots note out of intake by ramping up front till it reaches
     * the speed in OperatorConstants.FrontRPM 
     * 
     * Then Shoots the Note out of the Front of the intake
     * @return Command
     */ 
    public Command ShootForward() { // TODO: Roll note slightly back before rampup
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
    public Command EjectBackward(){
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
    public Command EjectForward(){
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

    // TODO: Implement stop intake on note, class in wpilib does something like this
}
