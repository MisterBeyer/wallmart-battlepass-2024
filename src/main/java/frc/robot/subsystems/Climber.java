package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase{
    private final CANSparkMax Motor0 = new CANSparkMax(45, MotorType.kBrushless);
    private final CANSparkMax Motor1 = new CANSparkMax(46, MotorType.kBrushless);

    private RelativeEncoder Motor0_encoder = Motor0.getEncoder();  // Encoder used to find max retraction distance


    public Climber() {
      Motor0.setIdleMode(CANSparkMax.IdleMode.kBrake);
      Motor1.setIdleMode(CANSparkMax.IdleMode.kBrake);

      // Reset Encoder values
      Motor0_encoder.setPosition(0.0);

      // Have Motors follow each other
      Motor1.follow(Motor0);
    }


    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
      // Speeds
      ClimberConstants.MaxSpeed = SmartDashboard.getNumber("Climber/Climber Motor Speed", ClimberConstants.MaxSpeed);

      // Amp and Encoder limits
      ClimberConstants.ChainReachedAmps = SmartDashboard.getNumber("Climber/Chain Reached Amp limit", ClimberConstants.ChainReachedAmps);
      ClimberConstants.RobotReachedAmps = SmartDashboard.getNumber("Climber/Robot Reached Amp limit", ClimberConstants.RobotReachedAmps);
      ClimberConstants.FullExtensionEncoder = SmartDashboard.getNumber("Climber/Full extension Encoder Position", ClimberConstants.FullExtensionEncoder);
    }


    /** @return Average of Intake motor's Output Amperage */
    public double getCurrent() {
      double current = (Motor0.getOutputCurrent() + Motor1.getOutputCurrent())/2;
      SmartDashboard.putNumber("Climber Amps", current);
      return current;
    }

    /**
     * Depolys the CLimber till encoder values match provided double
     * Using a set speed set in constants
     * @param goal Goal position represented as encoder value
     */
    public void deploy(double goal) { // TODO: add pid
        while(Motor0_encoder.getPosition() > ClimberConstants.FullExtensionEncoder) Motor0.set(ClimberConstants.MaxSpeed);
        stop();
    }

   /**
     * Retracts the CLimber till amp limit is reached
     * Using a amp limit set in constants
     */
    public void retractToChain() {//TODO: run for both motors inidivdually
      while (getCurrent() > ClimberConstants.ChainReachedAmps) Motor0.set(ClimberConstants.MaxSpeed);
      stop();
    }


   /**
     * Retracts the CLimber till amp limit is reached
     * Using a amp limit set in constants
     */
    public void retractFully() { //TODO use encoder position here but still run amp checks
      while (getCurrent() > ClimberConstants.RobotReachedAmps) Motor0.set(ClimberConstants.MaxSpeed);
      stop();
    }


     /**
      * Stops Both Motors
      */
     public void stop(){
      Motor0.set(0.0);
      Motor1.set(0.0);
     }
     
    // My code is perfect 
    // I wish
    //          - Honda Civic [2009]


    // This method will be called once per scheduler run
    @Override
    public void periodic() { 
      getCurrent();
      updateConstants();
    }
}
