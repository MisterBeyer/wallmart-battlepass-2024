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
        SmartDashboard.putNumber("Intake/ShootRampUp/Note Reached TOF Sensor Limit", OperatorConstants.reachedTOFLimit);
        SmartDashboard.putNumber("Intake/ShootRampUp/Note Left TOF Sensor Limit", OperatorConstants.leftTOFLimit);
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        OperatorConstants.reachedTOFLimit = SmartDashboard.getNumber("Intake/ShootRampUp/Note Reached TOF Sensor Limit", OperatorConstants.reachedTOFLimit);
        OperatorConstants.leftTOFLimit = SmartDashboard.getNumber("Intake/ShootRampUp/Note Reached TOF Sensor Limit", OperatorConstants.leftTOFLimit);

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

        if (state == 0) { // Push Note until it reaches the Time Of Flight sensor
            System.out.println(intake.getTOFReading());
            if(intake.getTOFReading() <= OperatorConstants.leftTOFLimit) {
                intake.setSpeed(OperatorConstants.FrontSlow, -OperatorConstants.BackSlow);
            }
            else {
                System.out.println(intake.getTOFReading() + "Stopped");
                intake.stop();
                state = 2;
            }

        }

        /*else if (state == 1) { // Push Note until it leaves the Time Of Flight sensor
        
            if(intake.getTOFReading() <= OperatorConstants.leftTOFLimit) {
                intake.setSpeed(0, -OperatorConstants.BackSlow);
            }
            else state = 2;
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
