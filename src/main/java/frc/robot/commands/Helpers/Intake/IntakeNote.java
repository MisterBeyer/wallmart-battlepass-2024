package frc.robot.commands.Helpers.Intake;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;

public class IntakeNote extends Command {
    private Intake intake;

    public IntakeNote(Intake module) {
        this.intake = module;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setSpeed(OperatorConstants.FrontIn, 0);
    }

    @Override
    public void execute() {}


    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }

    @Override 
    public boolean isFinished() {
        //if(Math.abs(intake.getRearRPM()) > OperatorConstants.IntakeNoteBackRPM) return true;
        if(intake.getFrontCurrent() > OperatorConstants.IntakeNoteAmps) return true;
        else return false;
    }
}
