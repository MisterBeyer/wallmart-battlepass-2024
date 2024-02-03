package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.OperatorConstants;

public class Arm extends SubsystemBase{
    // Define Motor can Ids
    private final CANSparkMax Arm0 = new CANSparkMax(36, MotorType.kBrushless);
    private final CANSparkMax Arm1 = new CANSparkMax(37, MotorType.kBrushless);

     // Create PID Controller and Encoder Objects
    private SparkPIDController Arm0_pidController = Arm0.getPIDController();
    private SparkPIDController Arm1_pidController = Arm1.getPIDController();
    private RelativeEncoder Arm0_encoder = Arm0.getEncoder();
    private RelativeEncoder Arm1_encoder = Arm1.getEncoder();

    private int position;

     public Arm() {
      Arm0.setIdleMode(CANSparkMax.IdleMode.kBrake);
      Arm1.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }


    /** @return Average of Arm motor's Output Amperage  */
    public double getCurrent() {
        double current = (Arm0.getOutputCurrent() + Arm1.getOutputCurrent())/2;
  
        SmartDashboard.putNumber("Arm Amps", current);
        return current;
      }

    /** @return The arms current Posistion as a integer
     *      1: All the way DOWN
     *      2: In MID
     *      3: All the way UP 
    */
    public int getPosition() {
        SmartDashboard.putNumber("Arm Position", position);
        return position;
      }


    /** Updates Motor Speeds from shuffleboard */
    public void updateSpeed() {
        OperatorConstants.ArmMotorSpeed = SmartDashboard.getNumber("Wrist Motor Speed", OperatorConstants.ArmMotorSpeed);
      }

    /** Sets all arm motors to coast, allowing arm to be manupilated by hand */
    public void coast() {
        Arm0.setIdleMode(CANSparkMax.IdleMode.kCoast);
        Arm1.setIdleMode(CANSparkMax.IdleMode.kCoast); 
    }

    /** Brings Arm All the way DOWN */
    public void Down() {
        goToHardStop(-OperatorConstants.ArmMotorSpeed, OperatorConstants.ArmAmpLimit);
        position = 1;
    }

     /** Brings Arm to some point in between */
    public void Mid() {
        goToSoftStop(); 
        position = 2;
    }

     /** Brings Arm All the way UP */
    public void Up() {
        goToHardStop(OperatorConstants.ArmMotorSpeed, OperatorConstants.ArmAmpLimit);
        position = 3;
    }

    private void goToSoftStop() {
        //m_pidController.setReference(rotations, CANSparkMax.ControlType.kPosition);
    }

    /**
     * Drive motor until it hits a hardstop, provided by the frame
     * @param Motor to drive into hardstop
     * @param MotorSpeed Maximuim allowed speed of motor
     * @param AmpLimit Current Limit to know when hardstop has been reached
     */
    private void goToHardStop(double MotorSpeed, double AmpLimit) {
        while (getCurrent() < AmpLimit) {
            Arm0.set(MotorSpeed);
            Arm1.set(MotorSpeed);
        }
        Arm0.set(0.0);
        Arm1.set(0.0);
    }


    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        getCurrent();
        getPosition();
        updateSpeed();
    }
}
