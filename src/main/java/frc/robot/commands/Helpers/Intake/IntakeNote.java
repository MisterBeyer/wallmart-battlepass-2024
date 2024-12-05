package frc.robot.commands.Helpers.Intake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;

public class IntakeNote extends Command {
    private Intake intake;

    public IntakeNote(Intake module) {
        this.intake = module;
        addRequirements(intake);

        // Shuffleboard!
        SmartDashboard.putNumber("Intake/IntakeNote/Note In Intake TOF Reading", OperatorConstants.intakeTOFLimit);
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        OperatorConstants.intakeTOFLimit = SmartDashboard.getNumber("Intake/IntakeNote/Note In Intake TOF Reading", OperatorConstants.intakeTOFLimit);

        System.out.println("[IntakeCommands/IntakeNote] Shuffleboard Updated");
    } 
    

    @Override
    public void initialize() {
        System.out.println("[IntakeCommands/IntakeNote] Intaking Note");
        intake.setSpeed(OperatorConstants.FrontIn, 0);
    }

    @Override
    public void execute() {}


    @Override
    public void end(boolean interrupted) {
        intake.stop();
        intake.setNoteStatus(true);

        System.out.println("[IntakeCommands/IntakeNote] Note Retrieved");
    }

    @Override 
    public boolean isFinished() {
        return (intake.getTOFReading() <= OperatorConstants.intakeTOFLimit); 
    }
}
