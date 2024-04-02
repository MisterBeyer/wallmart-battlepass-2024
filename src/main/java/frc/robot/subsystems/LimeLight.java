package frc.robot.subsystems;

import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimeLight extends SubsystemBase {
    private double tx;
    private double ty;
    private boolean tv;
    private double area;
    private int num = 0;


    public LimeLight() {
        System.out.println("[LimeLight] Bofa Mode Loaded");
    }



  
        public void doLimelightThing(){
            num++;
        //read values periodically
         tx = LimelightHelpers.getTX("limelight");
        ty = LimelightHelpers.getTY("limelight");
        area = LimelightHelpers.getTA("limelight");
        if(num % 50 == 0){
            System.out.print(tx + ", " + ty + ", " + area );
        }
       
        /* 
        //reads tv (is target dectected)
         tv = LimelightHelpers.getTV("limelight");
        if(num % 50 == 0){
            System.out.println(tv + ",");
        }
        */ //TODO: un comment when ready to test TV

        //post to smart dashboard periodically
        SmartDashboard.putNumber("Limelight/TX", tx);
        SmartDashboard.putNumber("Limelight/TY", ty);
        SmartDashboard.putNumber("Limelight/Area", area);
        }
        @Override
    public void periodic() {
        doLimelightThing();
    }



    
}


// TODO: to start lets get the limelight to see (ex: ID 1) and do a simple task (ex: raise arm to speaker or spin 180)