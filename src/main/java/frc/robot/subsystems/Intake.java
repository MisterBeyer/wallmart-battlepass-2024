package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.playingwithfusion.TimeOfFlight;
import com.revrobotics.CANSparkLowLevel.MotorType;


// Bofa Intake System

public class Intake extends SubsystemBase{
    private final CANSparkMax IntakeR = new CANSparkMax(30, MotorType.kBrushless);
    private final CANSparkMax IntakeF = new CANSparkMax(31, MotorType.kBrushless);
    private final RelativeEncoder IntakeR_enc = IntakeR.getEncoder();
    private final RelativeEncoder IntakeF_enc = IntakeF.getEncoder();

    private final TimeOfFlight rangeSensorF = new TimeOfFlight(40);


    private boolean isLocked;
    private boolean hasNote;


    public Intake() {

      // Reset Settings on Motor Controller, in case we need to swap one
      IntakeR.restoreFactoryDefaults();
      IntakeF.restoreFactoryDefaults();

      IntakeR.setIdleMode(CANSparkMax.IdleMode.kCoast);
      IntakeF.setIdleMode(CANSparkMax.IdleMode.kCoast);

      // Save settings in case of brownout
      IntakeR.burnFlash();
      IntakeF.burnFlash();

      isLocked = false;
      hasNote  = false;
    }


    /** @return Intake Lock status */
    public boolean getLock() {
      return isLocked;
    }

    /** @return Wether Note is marked as inside Intake T/F */
    public boolean getNoteStatus() {
      return this.hasNote;
    }

    /** @return RPM of the Front Intake Motor as double */
    public double getFrontRPM() {
      return IntakeF_enc.getVelocity();
    }

    /** @return RPM of the Rear Intake Motor as double*/
    public double getRearRPM(){
      return IntakeR_enc.getVelocity();
    }

    /** @return Output Current of the Front Intake Motor as double */
    public double getFrontCurrent() {
      return IntakeF.getOutputCurrent();
    }

    /** @return value reported by Time of Flight sensor as double*/
    public double getTOFReading() {
          return rangeSensorF.getRange();
        }
    
    /** @return Output Current of the Rear Intake Motor as double*/
    public double getRearCurrent(){
        return IntakeR.getOutputCurrent();
    }

    /** @return Average of Intake motor's Output Amperage */
    public double getAverageCurrent() {
      double current = (IntakeR.getOutputCurrent() + IntakeF.getOutputCurrent())/2;
      return current;
    }



    /** Puts data onto Shuffleboard */
    public void putData() {
      SmartDashboard.putNumber("Intake/Front Intake Amps", getFrontCurrent());
      SmartDashboard.putNumber("Intake/Rear Intake Amps", getRearCurrent());
      SmartDashboard.putNumber("Intake/Front Intake RPM", getFrontRPM());
      SmartDashboard.putNumber("Intake/Rear Intake RPM", getRearRPM());

      SmartDashboard.putBoolean("Intake/Lock Status", getLock());
      SmartDashboard.putBoolean("Intake/Note Status", getNoteStatus());
      SmartDashboard.putNumber("Intake/TOF Reading", rangeSensorF.getRange());
    }

    /** Pulls the IntakeSpeed variables from shuffleboard  */
    public void updateConstants() {
    }



    /**
     * Sets speed of both intake motors
     * Will not work if intake lock is true
     * @param speedFront Speed of Top Motor
     * @param backSpeed 
     */
    public void setSpeed(double speedFront, double backSpeed){
      if(!isLocked) {
        IntakeR.set(backSpeed);
        IntakeF.set(speedFront);
      }
        //I might be done guys
        //He might not be either
     }

     /** Sets the speed of each Motor indidually
      * Will not work if intake lock is true
      * @param speed Speed of motor as double between 0.0-1.0
      * @param isRear Rear(true) or Front(false) motor
      */
     public void setIndividualspeed(double speed, boolean isRear) {
        if(!isLocked) {
          if(isRear) IntakeR.set(speed);
          else IntakeF.set(speed);
        }
     }
     
    /** Stops Both Motors */
     public void stop(){
      IntakeR.set(0.0);
      IntakeF.set(0.0);
     }

    /** Sets the Status of Intake lock 
    * @param enable enable(true) or disable(false) the intake lock
    */
    public void setLock(boolean enable) {
      System.out.println("(Intake) Lock Set");
      isLocked = enable;
      stop();
     }

    /** Sets the Note Status of the Intake
     * @param status Wether the intake has a note or not T/F
     */
    public void setNoteStatus(boolean status) {
      hasNote = status;
    }


    // My code is perfect 
    // I wish
    //          - Honda Civic [2009]


    // This method will be called once per scheduler run
    @Override
    public void periodic() { 
      putData();
    }
}