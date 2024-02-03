package frc.robot.commands.Limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpiutil.net.PortForwarder;

public class Limelight {

    
    double tx = LimelightHelpers.getTX("7627_SpyCamera");



    NetworkTable table = NetworkTable.getTable("limelight");
double targetOffsetAngle_Horizontal = table.getNumber("tx", 0);
double targetOffsetAngle_Vertical = table.getNumber("ty", 0);
double targetArea = table.getNumber("ta", 0);
double targetSkew = table.getNumber("ts", 0);



NetworkTable Table = NetworkTableInstance.getDefault().getTable("limelight");
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


@Override
public void robotInit() 
{
    // Make sure you only configure port forwarding once in your robot code.
    // Do not place these function calls in any periodic functions
    for (int port = 5800; port <= 5807; port++) {
        PortForwarder.add(port, "limelight.local", port);
    }
}


    
}
