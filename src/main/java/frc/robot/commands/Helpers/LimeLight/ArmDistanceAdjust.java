package frc.robot.commands.Helpers.LimeLight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Arm;

public class ArmDistanceAdjust extends Command {
    private Arm arm;

    private double targetOffsetAngle_Vertical;


    // ** Constants
    // how many degrees back is your limelight rotated from perfectly vertical?
    private double limelightMountAngleDegrees = 0.0; 

    // distance from the center of the Limelight lens to the floor
    private double limelightLensHeightInches = 9.0; 

    // distance from the target to the floor
    private double goalHeightInches = 36.0; 


    public ArmDistanceAdjust(Arm module) {
        this.arm = module;
        addRequirements(arm);

        // Shuffleboard!
        SmartDashboard.putNumber("Limelight/ArmAdjust/Multiplyer", OperatorConstants.LimelightArmAdjustmentMuliplyer);
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        OperatorConstants.LimelightArmAdjustmentMuliplyer = SmartDashboard.getNumber("Limelight/ArmAdjust/Multiplyer", OperatorConstants.LimelightArmAdjustmentMuliplyer);
        
        System.out.println("[LimeLightCommands/ArmDistanceAdjust] Shuffleboard Updated");
    } 
    

    @Override
    public void initialize() {
        System.out.println("[LimeLightCommands/ArmDistanceAdjust]] Adjusting Arm");
    }

    @Override
    public void execute() {
        if (LimelightHelpers.getTV("")) {
            System.out.println("[LimeLightCommands/ArmDistanceAdjust]] AprilTag Detected!");
   
            targetOffsetAngle_Vertical = LimelightHelpers.getTY("");

            double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
            double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

            //calculate distance
            double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

            System.out.println( distanceFromLimelightToGoalInches);
            //arm.setGoal(distanceFromLimelightToGoalInches * OperatorConstants.LimelightArmAdjustmentMuliplyer);
        }
    }


    @Override
    public void end(boolean interrupted) {
        System.out.println("[LimeLightCommands/ArmDistanceAdjust] Interupted");
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
