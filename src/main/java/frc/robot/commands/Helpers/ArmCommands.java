package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Arm;

import frc.robot.Constants.OperatorConstants;

/*
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣤⣤⣴⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣤⣤⣤⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣶⣿⡿⠿⠛⠛⠉⠉⠉⢉⣭⣿⣿⣭⡉⠉⠉⠉⠛⠛⠻⢿⣿⣶⣦⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⢀⣤⣶⣿⣿⡿⠋⠁⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⣿⣿⣿⣄⠀⠀⠀⠀⠀⠀⠈⠙⢻⣿⣿⣷⣤⡀⠀⠀⠀⠀⠀
⠀⠀⠀⢀⣴⣿⣿⣿⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⠋⠀⠀⠙⢿⣿⡄⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⣿⣿⣦⡀⠀⠀⠀
⠀⠀⣰⣿⣿⠋⠸⣿⣿⣿⣷⣦⣀⠀⠀⠀⠀⠀⣾⣿⠃⠀⠀⠀⠀⠘⣿⣿⠀⠀⠀⠀⠀⣀⣤⣾⣿⣿⣿⠇⠙⣿⣿⣦⠀⠀
⠀⣰⣿⣿⠇⠀⠀⠈⠻⣿⣿⣿⣿⣿⣷⣶⣦⣤⣿⣿⣄⣀⣀⣀⣀⣠⣿⣿⣦⣤⣶⣾⣿⣿⣿⣿⣿⠟⠋   ⠘⣿⣿⣧⠀
⢠⣿⣿⣿⠀⠀⠀⠀⠀⠀⠉⠛⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠛⠉⠀⠀⠀⠀   ⣿⣿⣿⡆
⢸⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⢹⣿⡿⠿⠿⠿⠿⠿⠿⢿⣿⡏⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⣿⣿⣿⡇      Toytota Arm Subsystem™
⠘⣿⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣷⠀⠀⠀⠀⠀⠀⣸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⠇
⠀⠹⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⠀⠀⠀⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⡏⠀
⠀⠀⠹⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣧⠀⠀⠀⠀⣸⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⣿⣿⠟⠀⠀
⠀⠀⠀⠈⠻⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣿⣿⣧⣄⣠⣼⣿⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣴⣿⣿⠟⠁⠀⠀⠀
⠀⠀⠀⠀⠀⠈⠙⠿⣿⣷⣦⣄⡀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⢀⣠⣴⣾⣿⡿⠛⠁⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠛⠿⣿⣷⣶⣤⣤⣀⣀⡀⠻⣿⣿⣿⣿⠟⠀⣀⣀⣠⣤⣴⣾⣿⠿⠛⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠙⠛⠛⠻⠿⠿⠿⠿⠿⠿⠿⠿⠟⠛⠛⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 */



public class ArmCommands extends Command{
    private Arm arm;

    /** Helper Commands For Arm
     * @param module module to use as arm
     */
    public ArmCommands(Arm module) {
        // Assign Control
        this.arm = module;

        // ShuffleBoard!
        SmartDashboard.putNumber("Operator/Arm [Amp] Enocder Positon", OperatorConstants.ArmAmpPosition);
        SmartDashboard.putNumber("Operator/Arm [Speaker] Enocder Positon", OperatorConstants.ArmSpeakerPosition);
    } 


    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        // Encoder Posistions
        OperatorConstants.ArmAmpPosition = SmartDashboard.getNumber("Operator/Arm [Amp] Enocder Positon", OperatorConstants.ArmAmpPosition);
        OperatorConstants.ArmSpeakerPosition = SmartDashboard.getNumber("Operator/Arm [Speaker] Enocder Positon", OperatorConstants.ArmSpeakerPosition);

        System.out.println("[ArmCommands] Shuffleboard Updated");
        // Update Constants of Subsystems
        arm.updateConstants();
    }



    // The Real Helper Commands

    /** Brings the arm All the way to the [Bottom] */
    public Command goToStow() {
        return Commands.runOnce(() -> {
                    System.out.println("[ArmCommands] Stow");
                    arm.goToSoftStop(0);
                },
                arm);
       //return arm.goToSoftStop(0);
    }

    /** Brings the arm all the way Up to the [Amp] Shooting Position */
    public Command goToAmp() {
        return Commands.runOnce(() -> {
                    System.out.println("[ArmCommands] Amp");
                    arm.goToSoftStop(OperatorConstants.ArmAmpPosition);
                }, 
                arm);
        //return arm.goToSoftStop(OperatorConstants.ArmAmpPosition);
    }

    /** Brings the arm to the [Speaker] Shooting Position */
    public Command goToSpeaker() {
        return Commands.runOnce(() -> {
                    System.out.println("[ArmCommands] Speaker");
                    arm.goToSoftStop(OperatorConstants.ArmSpeakerPosition);
                },
                arm);
        //return arm.goToSoftStop(OperatorConstants.ArmSpeakerPosition);
    }


    // Direct Control for Arm

    /* Move Arm Forward by ReletiveSoftStopDelta Constant */
    public Command MoveForward() {
        System.out.println("[ArmCommands] Adjust Forward");
        return Commands.startEnd(() -> {
                    System.out.println("[ArmCommands] Adjust Forward");
                    arm.goToRelativeSoftStop(true);
                },
                () -> arm.stop(),
                arm);
    }

    /* Move Arm Background by ReletiveSoftStopDelta Constant */
    public Command MoveBackward() {
        return Commands.startEnd(() -> {
                    System.out.println("[ArmCommands] Adjust Backward");
                    arm.goToRelativeSoftStop(false);
                },
                () -> arm.stop(),
                arm);
    }
}
