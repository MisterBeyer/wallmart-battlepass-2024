package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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
    private RelativeEncoder Motor1_encoder = Motor1.getEncoder();  // Encoder used to find max retraction distance

    public Climber() {
      Motor0.setIdleMode(CANSparkMax.IdleMode.kBrake);
      Motor1.setIdleMode(CANSparkMax.IdleMode.kBrake);

      // Save settings in case of brownout
      Motor0.burnFlash();
      Motor1.burnFlash();

      // Reset Encoder values
      Motor0_encoder.setPosition(0.0);
      Motor1_encoder.setPosition(0.0);


      // Shuffleboard 
      SmartDashboard.putNumber("Climber/Extention Motor Speed", ClimberConstants.ExtendSpeed);
      SmartDashboard.putNumber("Climber/Retraction Motor Speed", ClimberConstants.RetractSpeed);
      }


    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
      // Speeds
      ClimberConstants.RetractSpeed = SmartDashboard.getNumber("Climber/Extention Motor Speed", ClimberConstants.ExtendSpeed);
      ClimberConstants.RetractSpeed = SmartDashboard.getNumber("Climber/Retraction Motor Speed", ClimberConstants.RetractSpeed);

      // Amp and Encoder limits
      //ClimberConstants.ChainReachedAmps = SmartDashboard.getNumber("Climber/Chain Reached Amp limit", ClimberConstants.ChainReachedAmps);
      //ClimberConstants.RobotReachedAmps = SmartDashboard.getNumber("Climber/Robot Reached Amp limit", ClimberConstants.RobotReachedAmps);
      //ClimberConstants.FullExtensionEncoder = SmartDashboard.getNumber("Climber/Full extension Encoder Position", ClimberConstants.FullExtensionEncoder);
    }



    /** @return Left Intake motor's Output Amperage */
    public double getLeftCurrent() {
      double current = Motor0.getOutputCurrent();
      SmartDashboard.putNumber("Climber/Left Amps", current);
      return current;
    }

    /** @return Right Intake motor's Output Amperage */
    public double getRightCurrent() {
      double current = Motor1.getOutputCurrent();
      SmartDashboard.putNumber("Climber/Right Amps", current);
      return current;
    }


    /** Retracts left Climber at a constant speed */
    public Command retractLeft() {
      return Commands.startEnd(() -> {
        Motor0.set(-ClimberConstants.RetractSpeed);},
      () -> stop());
    }

    
    /** Retracts right Climber at a constant speed */
    public Command retractRight() {
      return Commands.startEnd(() -> {
        Motor1.set(-ClimberConstants.RetractSpeed);},
      () -> stop());
    }

    /** Extends boths Climbers at a constant speed */
    public Command Extend() {
      return Commands.startEnd(() -> {
        Motor0.set(ClimberConstants.ExtendSpeed);
        Motor1.set(ClimberConstants.ExtendSpeed);},
      () -> stop(),
      this);
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
      getLeftCurrent();
      getRightCurrent();
    }
}