package frc.robot.commands;

import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {

    //read values periodically
    double x = LimelightHelpers.getTX("");
    double y = LimelightHelpers.getTY("");
    double area = LimelightHelpers.getTX("");
    
    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
}

// TODO: to start lets get the limelight to see (ex: ID 1) and do a simple task (ex: raise arm to speaker or spin 180)