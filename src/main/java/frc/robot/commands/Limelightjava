package frc.robot.commands;

import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class Limelight extends Command {
    private double tx;
    private double ty;
    private double area;


    public Limelight() {
    }

    @Override
    public void initialize() {
        System.out.println("[LimeLight] Bofa Mode Loaded");
    }

    @Override
    public void execute() {
        //read values periodically
        tx = LimelightHelpers.getTX("");
        ty = LimelightHelpers.getTY("");
        area = LimelightHelpers.getTA("");
        
        //post to smart dashboard periodically
        SmartDashboard.putNumber("Limelight/TX", tx);
        SmartDashboard.putNumber("Limelight/TY", ty);
        SmartDashboard.putNumber("Limelight/Area", area);
    }


    @Override
    public void end(boolean interrupted) {
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}


// TODO: to start lets get the limelight to see (ex: ID 1) and do a simple task (ex: raise arm to speaker or spin 180)