package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Wrist;
import frc.robot.Constants.OperatorConstants;

/*
 *⠀⠀⠀⠀⠀⠀⢀⣀⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⡔⠉⠀⠀⠀⠙⢄⠀⠀⠀⠀⠀⠀⡠⠊⠁⠀⠀⠉⢢⡀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⢀⠎⠀⠀⡴⠒⢢⠀⠀⢣⡀⠀⠀⠀⡜⠁⠀⡠⠒⢦⠀⠀⠱⡀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⡞⠀⠀⡜⠀⠀⠀⢣⠀⠀⢱⠀⠀⡼⠀⠀⣰⠁⠀⠀⢣⠀⠀⢱⠀⠀⠀⠀⠀⠀
⠀⠀⣀⠔⠁⠀⠀⣇⠀⠀⠀⢈⡄⠀⠀⠑⠒⠃⠀⠀⣇⠀⠀⠀⢨⣆⠀⠀⠣⢄⠀⠀⠀⠀
⠀⡏⠉⠛⣍⠙⠙⢚⣿⣿⣿⣟⠏⠉⢉⡙⠛⡉⠉⠙⣾⠿⠿⠿⢻⠟⠉⠉⠉⠉⠙⡆⠀⠀
⢸⠀⠀⠀⠈⠂⠀⠀⠀⠀⠀⠈⠀⢠⠞⠀⠀⠀⠀⠀⠘⠆⠀⣠⠏⠀⠀⠀⠀⠀⠀⢻⠀⠀
⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡆⠀
⣸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣇⠀
⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⠀
⣇⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢘⡆
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇
⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃              Happy Wrist Subsystem™
⢸⣿⣿⣿⣿⠿⠟⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠛⠿⢿⣿⣿⣿⣿⠀
⢸⣿⣿⣿⣦⣼⠀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡷⠀⣦⣨⣿⣿⡿⠀
⠈⣿⣿⣿⣿⣿⣧⡀⠙⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠀⣼⣿⣿⣿⣿⡇⠀
⠀⣿⣿⣿⣿⣿⣿⣷⣄⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠋⣠⣾⣿⣿⣿⣿⣿⡇⠀
⠀⣿⡻⠿⠟⠏⠽⢿⢿⣷⣄⡀⠙⠛⠿⠿⣿⠿⠿⠿⠟⠋⢁⣠⣾⣿⣿⣿⣿⣿⣿⣿⡇⠀
⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠩⠙⠓⠶⠦⠤⠤⠤⠤⠴⠶⠚⡛⠿⠉⠙⠙⠉⠙⠛⠻⣿⠀⠀
⠀⢹⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠃⠀
⠀⣼⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀
⠀⢻⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀
⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣀⣀⣀⣀⣀⡀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡟⠀⠀
⠀⠈⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠃⠀⠀⠀
 */


// Helper Commands File For Arm
public class WristCommands{
    private Wrist wrist;


    /** Helper Commands For Wrist
     *  @param module module to use as wrist
     */
    public WristCommands(Wrist module) {
        // Assign Control
        this.wrist = module;

        // ShuffleBoard!
        SmartDashboard.putNumber("Operator/Wrist [Amp] Enocder Positon", OperatorConstants.WristAmpPosition);
        SmartDashboard.putNumber("Operator/Wrist [Speaker] Enocder Positon", OperatorConstants.WristSpeakerPosition);
        SmartDashboard.putNumber("Operator/Wrist [Intake] Enocder Positon", OperatorConstants.WristIntakePosition);
    } 


    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        // Encoder Posistions
        OperatorConstants.WristAmpPosition = SmartDashboard.getNumber("Operator/Wrist [Amp] Enocder Positon", OperatorConstants.WristAmpPosition);
        OperatorConstants.WristSpeakerPosition = SmartDashboard.getNumber("Operator/Wrist [Speaker] Enocder Positon", OperatorConstants.WristSpeakerPosition);
        OperatorConstants.WristIntakePosition = SmartDashboard.getNumber("Operator/Wrist [Intake] Enocder Positon", OperatorConstants.WristIntakePosition);

        // Update Constants of Subsystems
        wrist.updateConstants();
    }



    // The Real Helper Commands

    /** Brings the Wrist into the [Stow] posistion */
    public Command goToStow() {
      return Commands.runOnce(() -> wrist.goToSoftStop2(0), wrist);
      //return wrist.goToSoftStop(0);
    }

    /** Brings the wrist to the [Intake] Posistion */
    public Command goToIntake() {
        return Commands.runOnce(() -> wrist.goToSoftStop2(OperatorConstants.WristIntakePosition), wrist);
        //return wrist.goToSoftStop(OperatorConstants.WristIntakePosition);
    }

    /** Brings the wrist to the [Amp] Shooting Position */
    public Command goToAmp() {
        return Commands.runOnce(() -> wrist.goToSoftStop2(OperatorConstants.WristAmpPosition), wrist);
        //return wrist.goToSoftStop(OperatorConstants.WristAmpPosition);
    }

    /** Brings the wrist to the [Speaker] Shooting Position */
    public Command goToSpeaker() {
        return Commands.runOnce(() -> wrist.goToSoftStop2(OperatorConstants.WristSpeakerPosition), wrist);
        //return wrist.goToSoftStop(OperatorConstants.WristSpeakerPosition);
    }


    // Direct Control for Wrist

    /* Move Wrist Forward by ReletiveSoftStopDelta Constant */
    public Command MoveForward() {
        return Commands.startEnd(() -> wrist.goToRelativeSoftStop(true), 
                                 () -> wrist.stop(),
                                wrist);
    }

    /* Move Wrist Background by ReletiveSoftStopDelta Constant */
    public Command MoveBackward() {
        return Commands.startEnd(() -> wrist.goToRelativeSoftStop(false),
                                 () -> wrist.stop(),
                                 wrist);
    }
}
