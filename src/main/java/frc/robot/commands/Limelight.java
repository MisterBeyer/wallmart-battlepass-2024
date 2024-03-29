package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableValue;
import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    NetworkTable Table = NetworkTable.getTable("Limelight");
    
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    
    //read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    
    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX". x);
    SmartDashboard.putNumber("LimelightY". y);
    SmartDashboard.putNumber("LimelightArea". area);
}

// TODO: to start lets get the limelight to see (ex: ID 1) and do a simple task (ex: raise arm to speaker or spin 180)