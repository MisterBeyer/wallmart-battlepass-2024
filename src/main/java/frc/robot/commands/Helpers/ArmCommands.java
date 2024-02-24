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
⠀⣰⣿⣿⠇⠀⠀⠈⠻⣿⣿⣿⣿⣿⣷⣶⣦⣤⣿⣿⣄⣀⣀⣀⣀⣠⣿⣿⣦⣤⣶⣾⣿⣿⣿⣿⣿⠟⠋⠘⣿⣿⣧⠀
⢠⣿⣿⣿⠀⠀⠀⠀⠀⠀⠉⠛⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠛⠉⠀⠀⠀⠀⠀⣿⣿⣿⡆
⢸⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⢹⣿⡿⠿⠿⠿⠿⠿⠿⢿⣿⡏⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⡇      Toytota Arm Subsystem™
⠘⣿⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣷⠀⠀⠀⠀⠀⠀⣸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⠇
⠀⠹⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⠀⠀⠀⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⡏⠀
⠀⠀⠹⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣧⠀⠀⠀⠀⣸⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⣿⣿⠟⠀⠀
⠀⠀⠀⠈⠻⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣿⣿⣧⣄⣠⣼⣿⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣴⣿⣿⠟⠁⠀⠀⠀
⠀⠀⠀⠀⠀⠈⠙⠿⣿⣷⣦⣄⡀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⢀⣠⣴⣾⣿⡿⠛⠁⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠛⠿⣿⣷⣶⣤⣤⣀⣀⡀⠻⣿⣿⣿⣿⠟⠀⣀⣀⣠⣤⣴⣾⣿⠿⠛⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠙⠛⠛⠻⠿⠿⠿⠿⠿⠿⠿⠿⠟⠛⠛⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 */


// Helper Commands File For Arm
public class ArmCommands extends Command{
    private Arm arm;

    public ArmCommands(Arm armprovider) {
        // Assign Control
        this.arm = armprovider;

        // Add requirements
        addRequirements(this.arm);

        // ShuffleBoard!
        SmartDashboard.putNumber("Operator/Arm [Amp] Enocder Positon", OperatorConstants.ArmAmpPosition);
        SmartDashboard.putNumber("Operator/Arm [Speaker] Enocder Positon", OperatorConstants.ArmSpeakerPosition);
    } 


    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        // Encoder Posistions
        OperatorConstants.ArmAmpPosition = SmartDashboard.getNumber("Operator/Arm [Amp] Enocder Positon", OperatorConstants.ArmAmpPosition);
        OperatorConstants.ArmSpeakerPosition = SmartDashboard.getNumber("Operator/Arm [Speaker] Enocder Positon", OperatorConstants.ArmSpeakerPosition);

        // Update Constants of Subsystems
        arm.updateConstants();
    }



    // The Real Helper Commands

    /** Brings the arm All the way to the [Bottom] */
    public Command goToStow() {
       return arm.goToSoftStop(0);
    }

    /** Brings the arm all the way Up to the [Amp] Shooting Position */
    public Command goToAmp() {
        return arm.goToSoftStop(OperatorConstants.ArmAmpPosition);
    }

    /** Brings the arm to the [Speaker] Shooting Position */
    public Command goToSpeaker() {
        return arm.goToSoftStop(OperatorConstants.ArmSpeakerPosition);
    }


    // Direct Control for Arm

    /* Move Arm Forward by ReletiveSoftStopDelta Constant */
    public Command MoveForward() {
          return Commands.runOnce(() -> arm.goToRelativeSoftStop(true), arm);
    }

    /* Move Arm Background by ReletiveSoftStopDelta Constant */
    public Command MoveBackward() {
        return Commands.runOnce(() -> arm.goToRelativeSoftStop(false), arm);
    }



    // Called when the command is initially scheduled.
    @Override
      public void initialize() {
        // Put Constants into Shuffleboard
        SmartDashboard.putNumber("Operator/Arm [Amp] Enocder Positon", OperatorConstants.ArmAmpPosition);
        SmartDashboard.putNumber("Operator/Arm [Speaker] Enocder Positon", OperatorConstants.ArmSpeakerPosition);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
