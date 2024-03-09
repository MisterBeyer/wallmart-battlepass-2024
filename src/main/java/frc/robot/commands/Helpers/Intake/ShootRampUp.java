package frc.robot.commands.Helpers.Intake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;

public class ShootRampUp extends Command {
    private Intake intake;

    private int state;

    public ShootRampUp(Intake module) {
        this.intake = module;
        addRequirements(intake);

        // ShuffleBoard!
        SmartDashboard.putNumber("Intake/ShootRampUp/Note Left Front Roller Amps", OperatorConstants.NoteLeftFrontAmps);
        SmartDashboard.putNumber("Intake/ShootRampUp/Note Shot from Front Roller Amps", OperatorConstants.NoteShotFrontAmps);

    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        OperatorConstants.NoteLeftFrontAmps = SmartDashboard.getNumber("Intake/ShootRampUp/Note Left Front Roller Amps", OperatorConstants.NoteLeftFrontAmps);
        OperatorConstants.NoteShotFrontAmps = SmartDashboard.getNumber("Intake/ShootRampUp/Note Shot from Front Roller Amps", OperatorConstants.NoteShotFrontAmps);

        System.out.println("[IntakeCommands/ShootRampUP] Shuffleboard Updated");
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
        else if (state == 1) { // Run until the Note leaves front rollers
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
        else if (state == 3) { // Shoot Note - Wait till Note reaches front rollerss
            if(intake.getFrontCurrent() < OperatorConstants.NoteShotFrontAmps) {
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 4;
        } 
        else if (state == 4) { // Shoots Note Until It leaves front rollers
            if(intake.getFrontCurrent() > OperatorConstants.NoteShotFrontAmps) {
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 5;
        } 

    }


    @Override
    public void end(boolean interrupted) {
        intake.stop();
        System.out.println("[IntakeCommands/ShootRampUP] Note Shot");
    }

    @Override 
    public boolean isFinished() {
        return (state == 5);
    }
}
