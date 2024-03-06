package frc.robot.commands.Helpers.Intake;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;

public class ShootRampUp extends Command {
    private Intake intake;

    private int state;

    public ShootRampUp(Intake module) {
        this.intake = module;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        System.out.println("[IntakeCommands/ShootRampUP] Shooting Note");
        state = 0;
    }

    @Override
    public void execute() {
        if (state == 0) { // Spin-up Front Roller
            if(intake.getFrontCurrent() < OperatorConstants.NoteLeftFrontAmps) {
                intake.setSpeed(OperatorConstants.FrontSlow, 0);
            }
            else state = 1;
        }
        else if (state == 1) { // Move Note to Back Roller
            if(intake.getFrontCurrent() > OperatorConstants.NoteLeftFrontAmps) {
                intake.setSpeed(OperatorConstants.FrontSlow, 0);
            }
            else state = 2;
        }
        else if (state == 2) { // Ramp up front roller
            if(Math.abs(intake.getFrontRPM()) < OperatorConstants.FrontRPM) {
                //System.out.println(intake.getFrontRPM());
                intake.setSpeed(-OperatorConstants.FrontOut, 0);
            }
            else state = 3;
        }
        else if (state == 3) { // Shoot Note
            if(true) { //TODO: Find when note has left the intake
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 4;
        } 

    }


    @Override
    public void end(boolean interrupted) {
        intake.stop();
        System.out.println("[IntakeCommands/ShootRampUP] Note Shot");
    }

    @Override 
    public boolean isFinished() {
        return state == 4;
    }
}
