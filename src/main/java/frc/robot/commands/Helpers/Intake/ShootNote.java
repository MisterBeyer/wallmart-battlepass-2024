package frc.robot.commands.Helpers.Intake;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;

public class ShootNote extends Command {
    private Intake intake;
    private int counter;
    private int state;
    private Timer timer = new Timer();
    private LinearFilter movingFilter; 
    
    public ShootNote(Intake module) {
        this.intake = module;
        addRequirements(intake);

        // ShuffleBoard!
        SmartDashboard.putNumber("Intake/ShootRampUp/Note Shot from Front Roller Amps", OperatorConstants.NoteShotFrontAmps);

    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        OperatorConstants.NoteShotFrontAmps = SmartDashboard.getNumber("Intake/ShootRampUp/Note Shot from Front Roller Amps", OperatorConstants.NoteShotFrontAmps);

        System.out.println("[IntakeCommands/ShootNote] Shuffleboard Updated");
    } 


    @Override
    public void initialize() {
        movingFilter = LinearFilter.movingAverage(5);
        System.out.println("[IntakeCommands/ShootNote] Shooting Note");
        state = 0;
        counter = 0;
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
        /* else if (state == 1) { // Shoot Note - Wait till Note reaches front rollerss
            if(intake.getFrontCurrent() < OperatorConstants.NoteShotFrontAmps) { //TODO: Trailing average
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 2;
        }  */
        else if (state == 1) { // Shoots Note Until It leaves front rollers
            counter += 1;
            /* Old Current based implementation
            /if(intake.getFrontCurrent() > OperatorConstants.NoteShotFrontAmps || counter < 5) {
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }*/
            if (!intake.getTOFReading()) { // Wait till reaches TOF
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 3; 
        }
        else if (state == 3) {
            if (intake.getTOFReading()) { // Wait till leaves TOF
                intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.BackOut);
            }
            else state = 4;
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
        return (state == 4);
    }
}
