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
public class WristCommands extends Command{
    private Wrist wrist;

    public WristCommands(Wrist wristprovider) {
        // Assign Control
        this.wrist = wristprovider;

        // Add requirements
        addRequirements(this.wrist);

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
       return wrist.goToSoftStop(0);
    }

    /** Brings the wrist to the [Intake] Posistion */
    public Command goToIntake() {
        return wrist.goToSoftStop(OperatorConstants.WristIntakePosition);
    }

    /** Brings the wrist to the [Amp] Shooting Position */
    public Command goToAmp() {
        return wrist.goToSoftStop(OperatorConstants.WristAmpPosition);
    }

    /** Brings the wrist to the [Speaker] Shooting Position */
    public Command goToSpeaker() {
        return wrist.goToSoftStop(OperatorConstants.WristSpeakerPosition);
    }


    // Direct Control for Wrist

    /* Move Wrist Forward by ReletiveSoftStopDelta Constant */
    public Command MoveForward() {
        return Commands.runOnce(() -> wrist.goToRelativeSoftStop(true), wrist);
    }

    /* Move Wrist Background by ReletiveSoftStopDelta Constant */
    public Command MoveBackward() {
        return Commands.runOnce(() -> wrist.goToRelativeSoftStop(false), wrist);
    }

    // Called when the command is initially scheduled.
    @Override
      public void initialize() {
        // Put Constants into Shuffleboard
        SmartDashboard.putNumber("Operator/Wrist [Amp] Enocder Positon", OperatorConstants.WristAmpPosition);
        SmartDashboard.putNumber("Operator/Wrist [Speaker] Enocder Positon", OperatorConstants.WristSpeakerPosition);
        SmartDashboard.putNumber("Operator/Wrist [Intake] Enocder Positon", OperatorConstants.WristIntakePosition);
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
