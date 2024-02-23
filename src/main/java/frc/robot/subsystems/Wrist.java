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

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.WristConstants;

public class Wrist extends TrapezoidProfileSubsystem{
    // Define Motor can Ids
    private final CANSparkMax Wrist0 = new CANSparkMax(32, MotorType.kBrushless);

     // Create PID Controller and Encoder Objects
    private SparkPIDController Wrist0_pidController = Wrist0.getPIDController();
    private RelativeEncoder Wrist0_encoder = Wrist0.getEncoder();

    
    // Configure Feedfoward
    private final ArmFeedforward m_feedforward =
        new ArmFeedforward(
            WristConstants.kSVolts, WristConstants.kGVolts,
            WristConstants.kVVoltSecondPerRad, WristConstants.kAVoltSecondSquaredPerRad);



     public Wrist() {
        // Configure Trapezoid Profile Subsystem
        super(new TrapezoidProfile.Constraints(
                WristConstants.kMaxVelocityRadPerSecond, 
                WristConstants.kMaxAccelerationRadPerSecSquared),
                WristConstants.kArmOffsetRads);

        // kBrake Mode
        Wrist0.setIdleMode(CANSparkMax.IdleMode.kBrake);

        // Reset encoder
        Wrist0_encoder.setPosition(0.0);

        // set PID coefficients
     
        
        
        //TODO: See if we can go from -.5 to .5 vvv
        Wrist0_pidController.setOutputRange(0, 0.5); // kMINOutput, kMAXOutput
        

        // Shuffleboard!
        Wrist0_pidController.setP(SmartDashboard.getNumber("Wrist P", 0.15));
        Wrist0_pidController.setI(SmartDashboard.getNumber("Wrist I", 0));
        Wrist0_pidController.setD(SmartDashboard.getNumber("Wrist D", 0));
        Wrist0_pidController.setIZone(SmartDashboard.getNumber("Wrist IZone", 0));
        Wrist0_pidController.setFF(SmartDashboard.getNumber("Wrist FF", 0));
        
    }


    /** @return Wrist motor's Output Amperage  */
    public double getCurrent() {
        double current = Wrist0.getOutputCurrent();
  
        SmartDashboard.putNumber("Arm/Wrist Amps", current);
        return current;
      }

    /** @return The current encoder posistion of the right motor */
    public double getPosition() {
        double position = Wrist0_encoder.getPosition();
        SmartDashboard.putNumber("Arm/Wrist Encoder", position);
        return position;
    }


    /** Updates Motor Speeds from shuffleboard */
    public void updateFromShuffleBoard() {

    }
    

    /** Sets all wrist motors to coast, allowing it to be manupilated by hand */
    public void coast() {
        Wrist0.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    /**
    * Stops Motor
    */
      public void stop(){
        Wrist0.set(0.0);
    }
       

 
    /**
     *  Moves arm to soft-stop using Trapazoidal Profiling
     * @param kArmOffsetRads Postion to move Arm to in Radians
     * @return Command for this function
     */
    public Command goToSoftStop(double kWristOffsetRads) {
        
        return Commands.runOnce(() -> setGoal(kWristOffsetRads), this);
    }
    //TODO: put real values here
    public Command goToStow(){
        return goToSoftStop(0);
    }
    public Command goToIntake(){
        return goToSoftStop(3/*Put real value in here*/);
    }
    public Command goToShootSpeaker(){
        return goToSoftStop(2/*Put real value here*/);
    }
    public Command goToShootAmp(){
        return goToSoftStop(4/*Put Real value here*/)
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
    public void goToHardStop(double MotorSpeed, double AmpLimit) {
        while (getCurrent() < AmpLimit) {
            Wrist0.set(MotorSpeed);
        }
        Wrist0.set(0.0);
    }


    @Override
    public void useState(TrapezoidProfile.State setpoint) {
        // calculate feedforawrd from the set point
        double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);
        // Add the feedforward to the PID output to get the motor output
        Wrist0_pidController.setReference(setpoint.position, CANSparkMax.ControlType.kPosition, 0, feedforward / 12.0);
    }
 
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        double posisiton = getPosition();
        if (getCurrent() < OperatorConstants.ArmAmpLimit) {
            goToSoftStop(posisiton);
        }
        updateFromShuffleBoard();

        // Run the Trapzoidal Subsystem Periodic
        super.periodic();
    } 
}
