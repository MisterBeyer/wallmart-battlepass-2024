package frc.robot.commands.Helpers.Intake;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;

public class ShootRampUp extends Command {
    private Intake intake;
    private int counter;
    private int state;
    private Timer timer;
    private LinearFilter movingFilter; 
    
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
        movingFilter = LinearFilter.movingAverage(5);
        System.out.println("[IntakeCommands/ShootRampUP] Shooting Note");
        state = 0;
        counter = 0;
        timer.restart();
    }

    @Override
    public void execute() {
        if (state == 0) { // Spin-up Front Roller
            if(intake.getFrontCurrent() < OperatorConstants.NoteLeftFrontAmps) {
                intake.setSpeed(OperatorConstants.FrontSlow, -0.05);
            }
            else state = 1;
        }
        else if (state == 1) { // Run until the Note leaves front rollers
        if (timer.get() >= 1) {
            state = 6;
        }
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
            else { 
                state = 3;
                movingFilter.reset();
            }
        }
        else if (state == 3) { // Shoot Note - Wait till Note reaches front rollerss
            if(intake.getFrontCurrent() < OperatorConstants.NoteShotFrontAmps) { //TODO: Trailing average
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 4;
        } 
        else if (state == 4) { // Shoots Note Until It leaves front rollers
            counter += 1;
            if(intake.getFrontCurrent() > OperatorConstants.NoteShotFrontAmps || counter < 5) {
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 5;
        }

        else if  (state == 6) { // corrects note if state 1 runs for too long 
            intake.setSpeed(0, OperatorConstants.BackSlow); // back runs at .6
                if(intake.getFrontRPM() <= 0.01);
                    state = 2;
        }



        System.out.println("state: "+state+" - " + intake.getFrontCurrent());
    }


    @Override
    public void end(boolean interrupted) {
        intake.stop();
        intake.setNoteStatus(false);

        System.out.println("[IntakeCommands/ShootRampUP] Note Shot");
    }

    @Override 
    public boolean isFinished() {
        return (state == 5);
    }
}
