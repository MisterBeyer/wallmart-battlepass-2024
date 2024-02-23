package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.TrapezoidProfileSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.OperatorConstants;

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


    private double encoder_goal = 1 ; //Test goal posisiton

     public Arm() {
        // Configure Trapezoid Profile Subsystem
        super(new TrapezoidProfile.Constraints(
                ArmConstants.kMaxVelocityRadPerSecond, 
                ArmConstants.kMaxAccelerationRadPerSecSquared),
                ArmConstants.kArmOffsetRads);

        // kBrake Mode
        Arm0.setIdleMode(CANSparkMax.IdleMode.kBrake);
        Arm1.setIdleMode(CANSparkMax.IdleMode.kBrake);

        // Follower Config
        Arm1.follow(Arm0, true);

        // Reset encoder
        Arm0_encoder.setPosition(0.0);

        // set PID coefficients
        //TODO: Put I, D and FF into shuffleboard 
        /* 
        Arm0_pidController.setP(0.15);    // kP
        Arm0_pidController.setI(0);      // kI
        Arm0_pidController.setD(0);      // kD
        Arm0_pidController.setIZone(0); //kIz
        Arm0_pidController.setFF(0);     //kFF
        */
        //TODO: See if we can go from -.5 to .5 vvv
        Arm0_pidController.setOutputRange(0, 0.5); // kMINOutput, kMAXOutput
        

        // Shuffleboard!
        Arm0_pidController.setP(SmartDashboard.getNumber("Arm P", 0.15));
        Arm0_pidController.setI(SmartDashboard.getNumber("Arm I", 0));
        Arm0_pidController.setD(SmartDashboard.getNumber("Arm D", 0));
        Arm0_pidController.setIZone(SmartDashboard.getNumber("Arm IZone", 0));
        Arm0_pidController.setFF(SmartDashboard.getNumber("Arm FF", 0));
        SmartDashboard.putNumber("Arm/Arm Encoder goal", encoder_goal);
    }


    /** Updates Constants from shuffleboard */
    public void updateSpeed() {
            OperatorConstants.WristMotorSpeed = SmartDashboard.getNumber("Arm/Wrist Motor Speed", OperatorConstants.WristMotorSpeed);
            encoder_goal = SmartDashboard.getNumber("Arm/Wrist Encoder goal", encoder_goal);

            Arm0_pidController.setP(SmartDashboard.getNumber("Arm/Arm PID", 0.15));
    }
    

    /** @return Average of Arm motor's Output Amperage  */
    public double getAverageCurrent() {
        double current = (Arm0.getOutputCurrent() + Arm1.getOutputCurrent())/2;
  
        SmartDashboard.putNumber("Arm/Arm Amps", current);
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

    /**
      * Stops Both Motors
      */
     public void stop(){
      Arm0.set(0.0);
     }
     

 
    /**
     *  Moves arm to soft-stop using Trapazoidal Profiling
     * @param kArmOffsetRads Postion to move Arm to in Radians
     * @return Command for this function
     */
    public Command goToSoftStop(double kArmOffsetRads) {
        return Commands.runOnce(() -> setGoal(kArmOffsetRads), this);
    }

    public Command goToRelativeSoftStop(boolean isPositive) {
       double position = getPosition();
        if (isPositive){
            position = position+2;
        }
        else {
            position = position-2;
        }
        return goToSoftStop(position);
}


    /**
     * Drive motor until it hits a hardstop, provided by the frame
     * @param MotorSpeed Maximuim allowed speed of motor
     * @param AmpLimit Current Limit to know when hardstop has been reached
     * 
     * Dont Use this for comp
     */
    public void goToHardStop(double MotorSpeed) {
        while (getAverageCurrent() < OperatorConstants.ArmAmpLimit) {
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
        double posisiton = getPosition();
        if (getAverageCurrent() < OperatorConstants.ArmAmpLimit) {
            goToSoftStop(posisiton);
        }
        // Run the Trapzoidal Subsystem Periodic
        super.periodic();
    } 
}
