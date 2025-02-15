package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.Helpers.Vision.DriveBaseRotationAdjust;
import frc.robot.subsystems.SwerveSubsystem;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.Constants.OperatorConstants;

public class DoritoCommands {
    
    /**  Limelight Control Commands */
    public DoritoCommands(){
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        System.out.println("[DoritoCommands] Shuffleboard Updated");

        // Update Constants of Subsystems
        DriveBaseRotationAdjust.updateConstants();
    }

    /**
     * Turns the Robot to face an April Tag
     * @param drivebase Swerve Drivebase provided by YAGSL
     * @return
     */
    public Command AdjustDriveBase(SwerveSubsystem drivebase) {
        return new DriveBaseRotationAdjust(drivebase);
    }
}
