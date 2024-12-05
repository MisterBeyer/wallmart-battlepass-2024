package frc.robot.commands.Helpers.Intake;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;

public class ShootNote extends Command {
    private Intake intake;
    private int state;
    private Timer timer = new Timer();
    private LinearFilter movingFilter; 
    
    public ShootNote(Intake module) {
        this.intake = module;
        addRequirements(intake);

        // Shuffleboard values are in ShootPullBack
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        System.out.println("[IntakeCommands/ShootNote] Values are in ShootPullBack");
    } 


    @Override
    public void initialize() {
        movingFilter = LinearFilter.movingAverage(5);
        System.out.println("[IntakeCommands/ShootNote] Shooting Note");
        state = 0;
        timer.restart();
    }

    @Override
    public void execute() {
        if (state == 0) { // Ramp up front roller
            if(Math.abs(intake.getFrontRPM()) < OperatorConstants.FrontRPM) {
                //System.out.println(intake.getFrontRPM());
                intake.setSpeed(-OperatorConstants.FrontOut, 0);
            }
            else { 
                state = 1;
                movingFilter.reset();
            }
        }

        else if (state == 1) { // Push Note until it reaches the Time Of Flight sensor
        
            if(intake.getTOFReading() >= OperatorConstants.reachedTOFLimit) {
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 2;
        }


        else if (state == 2) { // Run until the Note leaves Time Of Flight sensor
        
            if(intake.getTOFReading() <= OperatorConstants.leftTOFLimit) {
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 3;
        }

        System.out.println("state: "+state+" - " + intake.getFrontCurrent());
    }


    @Override
    public void end(boolean interrupted) {
        intake.stop();
        intake.setNoteStatus(false);

        System.out.println("[IntakeCommands/ShootNote] Note Shot. Interrupted: " + interrupted);
    }

    @Override 
    public boolean isFinished() {
        return (state == 3);
    }
}
