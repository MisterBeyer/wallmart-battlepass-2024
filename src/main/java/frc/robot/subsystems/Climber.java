package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;


public class Climber extends SubsystemBase{
    private final CANSparkMax Motor0 = new CANSparkMax(45, MotorType.kBrushless);
    private final CANSparkMax Motor1 = new CANSparkMax(46, MotorType.kBrushless);

    private RelativeEncoder Motor0_encoder = Motor0.getEncoder();  // Encoder used to find max retraction distance
    private RelativeEncoder Motor1_encoder = Motor1.getEncoder();  // Encoder used to find max retraction distance

    public Climber() {
      // Reset Settings on Motor Controller, in case we need to swap one
      Motor0.restoreFactoryDefaults();
      Motor1.restoreFactoryDefaults();

      // Set Brake Mode
      Motor0.setIdleMode(CANSparkMax.IdleMode.kBrake);
      Motor1.setIdleMode(CANSparkMax.IdleMode.kBrake);

      // Set Amp Limit
      Motor0.setSmartCurrentLimit(ClimberConstants.AmpLimit);
      Motor1.setSmartCurrentLimit(ClimberConstants.AmpLimit);

      // Save settings in case of brownout
      Motor0.burnFlash();
      Motor1.burnFlash();

      // Reset Encoder values
      Motor0_encoder.setPosition(0.0);
      Motor1_encoder.setPosition(0.0);
    }


    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
      // Nothing here yet
    }


    /** @return Left Climber motor's Output Amperage */
    public double getLeftCurrent() {
      double current = Motor0.getOutputCurrent();
      SmartDashboard.putNumber("Climber/Left Amps", current);
      return current;
    }

    /** @return Right Climber motor's Output Amperage */
    public double getRightCurrent() {
      double current = Motor1.getOutputCurrent();
      SmartDashboard.putNumber("Climber/Right Amps", current);
      return current;
    }

    /** @return Left Climber motor's current Encoder Position */
    public double getLeftEncoder() {
      double position = Motor0_encoder.getPosition();
      SmartDashboard.putNumber("Climber/Left Encoder", position);
      return position;
    }

    /** @return Right Climber motor's current Encoder Position */
    public double getRightEncoder() {
      double position = Motor1_encoder.getPosition();
      SmartDashboard.putNumber("Climber/Right Encoder", position);
      return position;
    }


    
    /** Sets speed of both Climber Motors */
    public void setBoth(double speed) {
      Motor0.set(speed);
      Motor1.set(speed);
    }

    /** Sets speed of Left Climber Motor */
    public void setLeft(double speed) {
      Motor0.set(speed);
    }

    /** Sets speed of Right Climber Motor */
    public void setRight(double speed) {
      Motor1.set(speed);
    }

  
    /**
    * Stops Both Motors
    */
    public void stop(){
      Motor0.set(0.0);
      Motor1.set(0.0);
    }


    // This method will be called once per scheduler run
    @Override
    public void periodic() { 
      // Put values into shuffleboard
      getLeftCurrent();
      getRightCurrent();

      getLeftEncoder();
      getRightEncoder();
    }
}