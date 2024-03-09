package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.TrapezoidProfileSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.ArmConstants;

public class Arm extends TrapezoidProfileSubsystem{
    // Define Motor can Ids
    private final CANSparkMax Arm0 = new CANSparkMax(35, MotorType.kBrushless);
    private final CANSparkMax Arm1 = new CANSparkMax(36, MotorType.kBrushless);

     // Create PID Controller and Encoder Objects
    private SparkPIDController Arm0_pidController = Arm0.getPIDController();
    private RelativeEncoder Arm0_encoder = Arm0.getEncoder();

    
    // Configure Feedfoward
    private final ArmFeedforward m_feedforward =
        new ArmFeedforward(
            ArmConstants.kSVolts, ArmConstants.kGVolts,
            ArmConstants.kVVoltSecondPerRad, ArmConstants.kAVoltSecondSquaredPerRad);



     public Arm() {
        // Configure Trapezoid Profile Subsystem
        super(new TrapezoidProfile.Constraints(
                ArmConstants.kMaxVelocityRadPerSecond, 
                ArmConstants.kMaxAccelerationRadPerSecSquared),
                ArmConstants.kArmOffsetRads);

                
        // Reset Settings on Motor Controller, in case we need to swap one
        Arm0.restoreFactoryDefaults();
        Arm1.restoreFactoryDefaults();

        // kBrake Mode
        Arm0.setIdleMode(CANSparkMax.IdleMode.kBrake);
        Arm1.setIdleMode(CANSparkMax.IdleMode.kCoast);

        //Set Amp Limits
        Arm0.setSmartCurrentLimit(ArmConstants.AmpLimit);
        Arm1.setSmartCurrentLimit(ArmConstants.AmpLimit);

        // Follower Config
        Arm1.follow(Arm0, true);

        // Reset encoder
        Arm0_encoder.setPosition(0.0);

        // set PID coefficients
        Arm0_pidController.setP(ArmConstants.P);
        Arm0_pidController.setI(ArmConstants.I);
        Arm0_pidController.setD(ArmConstants.D);
        Arm0_pidController.setIZone(ArmConstants.Iz);
        Arm0_pidController.setFF(ArmConstants.FF);

    
        Arm0_pidController.setOutputRange(-0.95, 0.95); // kMINOutput, kMAXOutput // -0.8, 0.8

        // Save settings in case of brownout
        Arm0.burnFlash();
        Arm1.burnFlash();
        

        // Shuffleboard!
        SmartDashboard.putNumber("Arm/Reletive SoftStop Delta", ArmConstants.ReletiveSoftStopDelta);

        SmartDashboard.putNumber("Arm/Arm P", ArmConstants.P); //PID
        SmartDashboard.putNumber("Arm/Arm I", ArmConstants.I);
        SmartDashboard.putNumber("Arm/Arm D", ArmConstants.D);
        SmartDashboard.putNumber("Arm/Arm IZone", ArmConstants.Iz);
        SmartDashboard.putNumber("Arm/Arm FF", ArmConstants.FF);
    }


    /** Updates Constants from shuffleboard */
    public void updateConstants() {
            // Function Constants
            ArmConstants.ReletiveSoftStopDelta = SmartDashboard.getNumber("Arm/Reletive SoftStop Delta", ArmConstants.ReletiveSoftStopDelta);

            // PID Constants
            ArmConstants.P = SmartDashboard.getNumber("Arm/Arm P", ArmConstants.P);
            ArmConstants.I = SmartDashboard.getNumber("Arm/Arm I", ArmConstants.I);
            ArmConstants.D = SmartDashboard.getNumber("Arm/Arm D", ArmConstants.D);
            ArmConstants.Iz = SmartDashboard.getNumber("Arm/Arm IZone", ArmConstants.Iz);
            ArmConstants.FF = SmartDashboard.getNumber("Arm/Arm FF", ArmConstants.FF);

            // ^Update PID Controller
            Arm0_pidController.setP(ArmConstants.P);
            Arm0_pidController.setI(ArmConstants.I);
            Arm0_pidController.setD(ArmConstants.D);
            Arm0_pidController.setIZone(ArmConstants.Iz);
            Arm0_pidController.setFF(ArmConstants.FF);

    }
    

    /** @return Average of Arm motor's Output Amperage  */
    public double getAverageCurrent() {
        double current = (Arm0.getOutputCurrent() + Arm1.getOutputCurrent())/2;
  
        SmartDashboard.putNumber("Arm/L Arm Amps", Arm0.getOutputCurrent());
        SmartDashboard.putNumber("Arm/R Arm Amps", Arm1.getOutputCurrent());
        return current;
      }

    /** @return The current encoder posistion of the right motor */
    public double getPosition() {
        double position = Arm0_encoder.getPosition();
        SmartDashboard.putNumber("Arm/Arm Encoder", position);
        return position;
    }

    /** Sets all arm motors to coast, allowing it to be manupilated by hand */
    public void coast() {
        Arm0.setIdleMode(CANSparkMax.IdleMode.kCoast);
        Arm1.setIdleMode(CANSparkMax.IdleMode.kCoast); 
    }

    /** Stops Both Motors */
     public void stop(){
      Arm0.set(0.0);
     }
     

 
    /**
     *  Moves arm to soft-stop using Trapazoidal Profiling
     * @param kArmOffsetRads Postion to move Arm to in Radians
    */
    public void goToSoftStop(double kArmOffsetRads) {
       setGoal(kArmOffsetRads);
    }

    /**
     * Move Arm to softstop to a point relitive to were it is now
     * @param isPositvie moves arm forward(True) or backwards(False)
     * by a set amount of Radians in Constants.ArmConstants.ReletiveSoftStopDelta
      */
    public void goToRelativeSoftStop(boolean isPositive) {
       double position = getPosition();
        if (isPositive) {
            position = position+ArmConstants.ReletiveSoftStopDelta;
        }
        else {
            position = position-ArmConstants.ReletiveSoftStopDelta;
        }
        System.out.print("calling set goal" + position);
        setGoal(position);
    }

    /**
     * Drive motor until it hits a hardstop, provided by the frame
     * @param MotorSpeed Maximuim allowed speed of motor
     * @param AmpLimit Current Limit to know when hardstop has been reached
     * 
     * Dont Use this for comp
     */
    public void goToHardStop(double MotorSpeed, double CurrentLimit) {
        while (getAverageCurrent() < CurrentLimit) {
            Arm0.set(MotorSpeed);
        }
        Arm0.set(0.0);
    }



    @Override
    /**
     * Sets the next movement per time slice
     * Called by the superclass TrapezoidProfile
     */
    public void useState(TrapezoidProfile.State setpoint) {
        // calculate feedforawrd from the set point
        double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);
        // Add the feedforward to the PID output to get the motor output
        Arm0_pidController.setReference(setpoint.position, CANSparkMax.ControlType.kPosition, 0, feedforward / 12.0);
    }
 
    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        // Update values on Shufflboard
        // Moved to Helper Commands to be called on buttonpress
        getPosition();
        getAverageCurrent();


        // Run the Trapzoidal Subsystem Periodic
        super.periodic();
    } 
}
