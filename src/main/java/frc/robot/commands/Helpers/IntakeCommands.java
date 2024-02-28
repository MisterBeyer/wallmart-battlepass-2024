package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.OperatorConstants;

import frc.robot.subsystems.Intake;

//dont unplug the ethernet
public class IntakeCommands{
    
    private Intake intake;

    // Variables
    private boolean commandcancel;

    /** Helper Commands For Intake
     *  @param module module to use as intake
     */
    public IntakeCommands(Intake module) {
        // Assign Control
        this.intake = module;

        // ShuffleBoard!
        SmartDashboard.putNumber("Intake/Front Motor Speed", OperatorConstants.FrontOut);
        SmartDashboard.putNumber("Intake/Rear Motor Speed", OperatorConstants.BackOut);
        SmartDashboard.putNumber("Operator/Shoot [Front] Goal RPM", OperatorConstants.FrontRPM); 
        SmartDashboard.putNumber("Operator/Intake [Back] Goal RPM", OperatorConstants.IntakeNoteBackRPM);
    }



    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        // Motor Speed Posistions
        OperatorConstants.FrontOut = SmartDashboard.getNumber("Intake/Front Motor Speed", OperatorConstants.FrontOut);
        OperatorConstants.BackOut  = SmartDashboard.getNumber("Intake/Rear Motor Speed", OperatorConstants.BackOut);

        // RPM Goals
        OperatorConstants.FrontRPM = SmartDashboard.getNumber("Operator/Shoot [Front] Goal RPM", OperatorConstants.FrontRPM); 
        OperatorConstants.IntakeNoteBackRPM = SmartDashboard.getNumber("Operator/Intake [Back] Goal RPM", OperatorConstants.IntakeNoteBackRPM); 

        // Variables
        commandcancel = false;

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
                                 () -> commandcancel = true, 
                                 intake);
    }    

    /**
     * Intakes note
     * Sets intake Speed to OperatorConstants.FrontOut
     * 
     * @return Command
     */ 
    public Command Intake() {
        return Commands.startEnd(() -> IntakeNote(), 
                                 () -> commandcancel = true,
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
                                 () -> intake.stop(),
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
                                () -> intake.stop(), 
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
      while(intake.getFrontRPM() < OperatorConstants.FrontRPM || commandcancel) intake.setSpeed(OperatorConstants.FrontOut, 0);
      intake.setSpeed(OperatorConstants.FrontOut, -OperatorConstants.BackOut);

      commandcancel = false;
    }

    /** Runs intake Util Note Hits Rear Roller
     *  Uses amp limit set for rear roller in operatorConstants to detect note
     */
    private void IntakeNote() {
        // TODO:  class in wpilib does something like this
        while(intake.getRearRPM() < OperatorConstants.IntakeNoteBackRPM || commandcancel) intake.setSpeed(-OperatorConstants.FrontOut, 0);
        intake.stop();

        commandcancel = false;
    }

}
