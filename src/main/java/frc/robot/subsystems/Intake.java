package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.OperatorConstants;;

public class Intake extends SubsystemBase{
    private final CANSparkMax Intake0 = new CANSparkMax(31, MotorType.kBrushless);
    private final CANSparkMax Intake1 = new CANSparkMax(30, MotorType.kBrushless);
    private final RelativeEncoder Intake0enc = Intake0.getEncoder();
    private final RelativeEncoder Intake1enc = Intake1.getEncoder();

    public double getFrontRPM(){
      return Intake1enc.getVelocity();
    }


    public Intake() {
      Intake0.setIdleMode(CANSparkMax.IdleMode.kCoast);
      Intake1.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }


    /** Pulls the IntakeSpeed variables from shuffleboard  */
    public void updateSpeed() {
      OperatorConstants.IntakeSpeed = SmartDashboard.getNumber("Arm/Intake Speed", OperatorConstants.IntakeSpeed);
      OperatorConstants.BackOut = SmartDashboard.getNumber("BackOut", OperatorConstants.BackOut);
      OperatorConstants.FrontOut = SmartDashboard.getNumber("FrontOut", OperatorConstants.FrontOut);
      SmartDashboard.putNumber("FrontRPM", getFrontRPM());
     }

    /** @return Average of Intake motor's Output Amperage */
    public double getCurrent() {
      double current = (Intake0.getOutputCurrent() + Intake1.getOutputCurrent())/2;
      SmartDashboard.putNumber("Arm/Intake Amps", current);
      return current;
    }


    /**
     * Sets speed of both intake motors
     * @param speedFront Speed of Top Motor
     * @param speedBack Speed of Bottom Motor
     */
    public void setSpeed(double speedFront, double speedBack){
        Intake0.set(speedBack);
        Intake1.set(speedFront);
        //I might be done guys
        //He might not be either
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
      updateSpeed();
    }
}