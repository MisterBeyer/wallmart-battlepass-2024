package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;


import frc.robot.Constants.OperatorConstants;;


// Bofa Intake System

public class Intake extends SubsystemBase{
    private final CANSparkMax Intake0 = new CANSparkMax(30, MotorType.kBrushless); // Rear
    private final CANSparkMax Intake1 = new CANSparkMax(31, MotorType.kBrushless); // Front
    private final RelativeEncoder Intake0_enc = Intake0.getEncoder();
    private final RelativeEncoder Intake1_enc = Intake1.getEncoder();


    private boolean isLocked;


    public Intake() {
      Intake0.setIdleMode(CANSparkMax.IdleMode.kCoast);
      Intake1.setIdleMode(CANSparkMax.IdleMode.kCoast);

      isLocked = false;
    }


    /** @return Intake Lock status */
    public boolean getLock() {
      return isLocked;
    }

    /** @return RPM of the Front Intake Motor as double */
    public double getFrontRPM() {
      return Intake1_enc.getVelocity();
    }

    /** @return RPM of the Rear Intake Motor as double*/
    public double getRearRPM(){
      return Intake0_enc.getVelocity();
    }

    /** @return Output Current of the Front Intake Motor as double */
    public double getFrontCurrent() {
      return Intake1.getOutputCurrent();
    }
    
    /** @return Output Current of the Rear Intake Motor as double*/
    public double getRearCurrent(){
        return Intake0.getOutputCurrent();
    }


    /** @return Average of Intake motor's Output Amperage */
    public double getAverageCurrent() {
      double current = (Intake0.getOutputCurrent() + Intake1.getOutputCurrent())/2;
      SmartDashboard.putNumber("Intake/Intake Amps", current);
      return current;
    }


    /** Pulls the IntakeSpeed variables from shuffleboard  */
    public void updateConstants() {
      OperatorConstants.IntakeSpeed = SmartDashboard.getNumber("Intake/Intake Speed", OperatorConstants.IntakeSpeed);
      OperatorConstants.BackOut = SmartDashboard.getNumber("Intake/BackOut", OperatorConstants.BackOut);
      OperatorConstants.FrontOut = SmartDashboard.getNumber("Intake/FrontOut", OperatorConstants.FrontOut);
     }



    /**
     * Sets speed of both intake motors
     * Will not work if intake lock is true
     * @param speedFront Speed of Top Motor
     * @param backSpeed 
     */
    public void setSpeed(double speedFront, double backSpeed){
      if(!isLocked) {
        Intake0.set(backSpeed);
        Intake1.set(speedFront);
      }
      else stop();
        //I might be done guys
        //He might not be either
     }

     /** Sets the speed of each Motor indidually
      * Will not work if intake lock is true
      * @param speed Speed of motor as double between 0.0-1.0
      * @param isRear Rear(true) or Front(false) motor
      */
     public void setIndividualspeed (double speed, boolean isRear) {
        if(!isLocked) {
          if(isRear) Intake0.set(speed);
          else Intake1.set(speed);
        }
        else stop();
     }
     
    /** Stops Both Motors */
     public void stop(){
      Intake0.set(0.0);
      Intake1.set(0.0);
     }

    /** Sets the Status of Intake lock 
    * @param enable enable(true) or disable(false) the intake lock
    */
    public void setLock(boolean enable) {
      isLocked = enable;
     }
     


    // My code is perfect 
    // I wish
    //          - Honda Civic [2009]


    // This method will be called once per scheduler run
    @Override
    public void periodic() { 
      getAverageCurrent();
    }
}