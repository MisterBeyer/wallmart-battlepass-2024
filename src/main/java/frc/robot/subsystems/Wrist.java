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
                WristConstants.kWristOffsetRads);

        // kBrake Mode
        Wrist0.setIdleMode(CANSparkMax.IdleMode.kBrake);

        //Set Amp Limits
        Wrist0.setSmartCurrentLimit(WristConstants.AmpLimit);

        // Reset encoder
        Wrist0_encoder.setPosition(0.0);

        // set PID coefficients
        Wrist0_pidController.setP(WristConstants.P);
        Wrist0_pidController.setI(WristConstants.I);
        Wrist0_pidController.setD(WristConstants.D);
        Wrist0_pidController.setIZone(WristConstants.Iz);
        Wrist0_pidController.setFF(WristConstants.FF);


        Wrist0_pidController.setOutputRange(-0.8, 0.8); // kMINOutput, kMAXOutput

        // Save settings in case of brownout
        Wrist0.burnFlash();
        

        // Shuffleboard!
        SmartDashboard.putNumber("Wrist/Reletive SoftStop Delta", WristConstants.ReletiveSoftStopDelta);

        SmartDashboard.putNumber("Wrist/Wrist P", WristConstants.P); //PID
        SmartDashboard.putNumber("Wrist/Wrist I", WristConstants.I);
        SmartDashboard.putNumber("Wrist/Wrist D", WristConstants.D);
        SmartDashboard.putNumber("Wrist/Wrist IZone", WristConstants.Iz);
        SmartDashboard.putNumber("Wrist/Wrist FF", WristConstants.FF);
    }



    /** Updates Constants from shuffleboard */
    public void updateConstants() {
        // Function Constants
        WristConstants.ReletiveSoftStopDelta = SmartDashboard.getNumber("Wrist/Reletive SoftStop Delta", WristConstants.ReletiveSoftStopDelta);

        // PID Constants
        WristConstants.P = SmartDashboard.getNumber("Wrist/Wrist P", WristConstants.P);
        WristConstants.I = SmartDashboard.getNumber("Wrist/Wrist I", WristConstants.I);
        WristConstants.D = SmartDashboard.getNumber("Wrist/Wrist D", WristConstants.D);
        WristConstants.Iz = SmartDashboard.getNumber("Wrist/Wrist IZone", WristConstants.Iz);
        WristConstants.FF = SmartDashboard.getNumber("Wrist/Wrist FF", WristConstants.FF);

        // ^Update PID Controller
        Wrist0_pidController.setP(WristConstants.P);
        Wrist0_pidController.setI(WristConstants.I);
        Wrist0_pidController.setD(WristConstants.D);
        Wrist0_pidController.setIZone(WristConstants.Iz);
        Wrist0_pidController.setFF(WristConstants.FF);

    }


    /** @return Wrist Motor Output Amperage  */
    public double getCurrent() {
        double current = Wrist0.getOutputCurrent();
  
        SmartDashboard.putNumber("Wrist/Wrist Amps", current);
        return current;
      }

    /** @return The current encoder posistion of the Wrist motor */
    public double getPosition() {
        double position = Wrist0_encoder.getPosition();
        SmartDashboard.putNumber("Wrist/Wrist Encoder", position);
        return position;
    }

    /** Sets all wrist motors to coast, allowing it to be manupilated by hand */
    public void coast() {
        Wrist0.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    /** Stops Both Motors */
     public void stop(){
      Wrist0.set(0.0);
     }
     
       

 
    /**
     *  Moves Wrist to soft-stop using Trapazoidal Profiling
     * @param kWristOffsetRads Postion to move Wrist to in Radians
     * @return Command for this function
     */
    public Command goToSoftStop(double kWristOffsetRads) {
        
        return Commands.runOnce(() -> setGoal(kWristOffsetRads), this);
    }

    /**
     * Move Arm to softstop to a point relitive to were it is now
     * @param isPositvie moves arm forward(True) or backwards(False)
     * by a set amount of Radians in Constants.WristConstants.ReletiveSoftStopDelta
      */
    public void goToRelativeSoftStop(boolean isPositive) {
        double position = getPosition();
            if (isPositive){
                position = position+WristConstants.ReletiveSoftStopDelta;
            }
            else {
                position = position-WristConstants.ReletiveSoftStopDelta;
            }
        
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
        while (getCurrent() < CurrentLimit) {
            Wrist0.set(MotorSpeed);
        }
        Wrist0.set(0.0);
    }



    /** Make sure we're not hitting the AmpLimit */
    // Obsolete
    /*private void verifyAmpLimit() {
        double posisiton = getPosition();
        if (getCurrent() < WristConstants.AmpLimit) {
            goToSoftStop(posisiton);
        }
    } */



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
      
        // Update values on Shufflboard
        // Moved to Helper Commands to be called on buttonpress
        getPosition();
        getCurrent();

        // Run the Trapzoidal Subsystem Periodic
        super.periodic();
    } 
}
