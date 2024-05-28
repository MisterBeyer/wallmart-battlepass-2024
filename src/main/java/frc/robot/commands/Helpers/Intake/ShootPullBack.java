package frc.robot.commands.Helpers.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;

public class ShootPullBack extends Command {
    private Intake intake;
    private int state;
    private Timer timer = new Timer();
   
    public ShootPullBack(Intake module) {
        this.intake = module;
        addRequirements(intake);

        // ShuffleBoard!
        SmartDashboard.putNumber("Intake/ShootRampUp/Note Left Front Roller Amps", OperatorConstants.NoteLeftFrontAmps);

    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        OperatorConstants.NoteLeftFrontAmps = SmartDashboard.getNumber("Intake/ShootRampUp/Note Left Front Roller Amps", OperatorConstants.NoteLeftFrontAmps);

        System.out.println("[IntakeCommands/ShootPullBack] Shuffleboard Updated");
    } 


    @Override
    public void initialize() {
        System.out.println("[IntakeCommands/ShootPullBack] Pulling Note Back");
        state = 0;
        timer.restart();
    }

    @Override
    public void execute() {
        /* if (state == 0) { // Spin-up Front Roller
            if(intake.getFrontCurrent() < OperatorConstants.NoteLeftFrontAmps) {
                intake.setSpeed(OperatorConstants.FrontSlow, -0.05);
            }
            else state = 1;
        } */
        if (state == 0) { // Run until the Note leaves front rollers
        
            if(intake.getTOFReading()) {
                intake.setSpeed(OperatorConstants.FrontSlow, OperatorConstants.BackSlow);
            }
            else state = 2;
        }
            // Old Implementation for Current sensors
            /*if (timer.get() >= 1) {
            state = 6;
        }
            if(intake.getFrontCurrent() > OperatorConstants.NoteLeftFrontAmps) {
                intake.setSpeed(OperatorConstants.FrontSlow, 0);
            }
            else state = 2;
        }
        else if  (state == 6) { // corrects note if state 1 runs for too long 
            intake.setSpeed(0, OperatorConstants.BackSlow); // back runs at .6
                if(intake.getFrontRPM() <= 0.01);
                    state = 2;
        } */


        System.out.println("state: "+state+" - " + intake.getFrontCurrent());
    }


    @Override
    public void end(boolean interrupted) {
        intake.stop();

        System.out.println("[IntakeCommands/ShootPullBack] Note Clear of Front Roller. Interrupted: " + interrupted);
    }

    @Override 
    public boolean isFinished() {
        return (state == 2);
    }
}
