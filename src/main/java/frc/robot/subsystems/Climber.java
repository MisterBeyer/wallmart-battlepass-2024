package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Climber extends SubsystemBase{
    private final CANSparkMax climberMotor = new CANSparkMax(38, MotorType.kBrushless);



    public Climber() {
      climberMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

    }


    /** Pulls the IntakeSpeed variables from shuffleboard  */


    /** @return Average of Intake motor's Output Amperage */
    public double getCurrent() {
      double current = (climberMotor.getOutputCurrent() + climberMotor.getOutputCurrent())/2;
      SmartDashboard.putNumber("Climber Amps", current);
      return current;
    }


    /**
     * Sets speed of both intake motors
     * @param speedTop Speed of Top Motor
     * @param speedBottom Speed of Bottom Motor
     */
    public void setSpeed(double speedTop, double speedBottom){
        climberMotor.set(0.2);
        //I might be done guys
        //He might not be either
     }

     /**
      * Stops Both Motors
      */
     public void stop(){
      climberMotor.set(0.0);
     }
     
    // My code is perfect 
    // I wish
    //          - Honda Civic [2009]


    // This method will be called once per scheduler run
    @Override
    public void periodic() { 
      getCurrent();
    }
}
