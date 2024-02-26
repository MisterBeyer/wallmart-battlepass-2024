package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;


import frc.robot.Constants.OperatorConstants;;


// Intake 1 is the front rollers
public class Intake extends SubsystemBase{
    private final CANSparkMax Intake0 = new CANSparkMax(30, MotorType.kBrushless);
    private final CANSparkMax Intake1 = new CANSparkMax(31, MotorType.kBrushless);
    private final RelativeEncoder Intake0enc = Intake0.getEncoder();
    private final RelativeEncoder Intake1enc = Intake1.getEncoder();



    public Intake() {
      Intake0.setIdleMode(CANSparkMax.IdleMode.kCoast);
      Intake1.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }


    /** Pulls the IntakeSpeed variables from shuffleboard  */
    public void updateConstants() {
      OperatorConstants.IntakeSpeed = SmartDashboard.getNumber("Intake/Intake Speed", OperatorConstants.IntakeSpeed);
      OperatorConstants.BackOut = SmartDashboard.getNumber("Intake/BackOut", OperatorConstants.BackOut);
      OperatorConstants.FrontOut = SmartDashboard.getNumber("Intake/FrontOut", OperatorConstants.FrontOut);
     }

    /** @return RPM of front Motor */
    public double getFrontRPM(){
      double velocity = Intake1enc.getVelocity();
      SmartDashboard.putNumber("Intake/FrontRPM", velocity);
      return velocity;
    }

    /** @return Average of Intake motor's Output Amperage */
    public double getCurrent() {
      double current = (Intake0.getOutputCurrent() + Intake1.getOutputCurrent())/2;
      SmartDashboard.putNumber("Intake/Intake Amps", current);
      return current;
    }


    /**
     * Sets speed of both intake motors
     * @param speedFront Speed of Top Motor
     * @param backSpeed 
     */
    public void setSpeed(double speedFront, double backSpeed){
        Intake0.set(backSpeed);
        Intake1.set(speedFront);
        //I might be done guys
        //He might not be either
     }


     public void setBackSpeed (double backspeed) {
      Intake0.set(backspeed);


     }

     
    /**
      * Stops Both Motors
      */
     public void stop(){
      Intake0.set(0.0);
      Intake1.set(0.0);
     }
     
    // My code is perfect 
    // I wish
    //          - Honda Civic [2009]


    // This method will be called once per scheduler run
    @Override
    public void periodic() { 
      getCurrent();
      getFrontRPM();
    }
}