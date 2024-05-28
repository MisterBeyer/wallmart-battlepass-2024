package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Helpers.LimeLight.ArmDistanceAdjust;
import frc.robot.commands.Helpers.LimeLight.DriveBaseRotationAdjust;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.SwerveSubsystem;

public class CaprisonCommands {
    
    /**  Limelight Control Commands */
    public CaprisonCommands(){
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        System.out.println("[CaprisonCommands] Shuffleboard Updated");

        // Update Constants of Subsystems
        ArmDistanceAdjust.updateConstants();
        DriveBaseRotationAdjust.updateConstants();
    }



    /**
     * Moves Arm up a certain amount depending on distance from an apriltag 
     * @param drivebase Swerve Drivebase provided by YAGSL
     * @return
     */
    public Command AdjustArm(Arm module) {
        return new ArmDistanceAdjust(module);
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
