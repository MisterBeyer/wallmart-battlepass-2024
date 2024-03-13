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
        SmartDashboard.putNumber("Intake/IntakeNote/Intake Note Amp Limit", OperatorConstants.IntakeNoteAmps);
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        OperatorConstants.IntakeNoteAmps = SmartDashboard.getNumber("Intake/IntakeNote/Intake Note Amp Limit", OperatorConstants.IntakeNoteAmps);

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
        //if(Math.abs(intake.getRearRPM()) > OperatorConstants.IntakeNoteBackRPM) return true;
        return (intake.getFrontCurrent() > OperatorConstants.IntakeNoteAmps || Math.abs(intake.getRearRPM()) > 10); //TODO: add constant
    }
}
