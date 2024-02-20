package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.OperatorConstants;

public class Arm extends SubsystemBase{
    // Define Motor can Ids
    private final CANSparkMax Arm0 = new CANSparkMax(35, MotorType.kBrushless);
    private final CANSparkMax Arm1 = new CANSparkMax(36, MotorType.kBrushless);


     // Create PID Controller and Encoder Objects
    private SparkPIDController Arm0_pidController = Arm0.getPIDController();
    private RelativeEncoder Arm0_encoder = Arm0.getEncoder();


    // Trapzoidal Profile setup
    private static double kDt = 0.02;
    
    private final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(1, 1.5);

    // Create a motion profile with the given maximum velocity and maximum
    // acceleration constraints for the next setpoint.
    private final TrapezoidProfile m_profile =
        new TrapezoidProfile(new TrapezoidProfile.Constraints(1.75, 0.75));
    private TrapezoidProfile.State m_goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State m_setpoint = new TrapezoidProfile.State();


    private int position;

     public Arm() {
        // kBrake Mode
        Arm0.setIdleMode(CANSparkMax.IdleMode.kBrake);
        Arm1.setIdleMode(CANSparkMax.IdleMode.kBrake);

        // Follower Config
        Arm1.setInverted(true);
        Arm1.follow(Arm0);

        // set PID coefficients
        Arm0_pidController.setP(0.1);    // kP
        Arm0_pidController.setI(0);      // kI
        Arm0_pidController.setD(0);      // kD
        Arm0_pidController.setIZone(0); //kIz
        Arm0_pidController.setFF(0);     //kFF
        Arm0_pidController.setOutputRange(0.1, 0.5); // kMINOutput, kMAXOutput
    }


    /** @return Average of Arm motor's Output Amperage  */
    public double getCurrent() {
        double current = (Arm0.getOutputCurrent() + Arm1.getOutputCurrent())/2;
  
        SmartDashboard.putNumber("Arm/Arm Amps", current);
        return current;
      }

    /** @return The arms current Posistion as a integer
     *      1: All the way DOWN
     *      2: In MID
     *      3: All the way UP 
    */
    public int getPosition() {
        SmartDashboard.putNumber("Arm/Arm Position", position);
        return position;
      }

    /** returns encoder position of right motor */
    public double getEncoder() {
        return Arm0_encoder.getPosition();
    }


    /** Updates Motor Speeds from shuffleboard */
    public void updateSpeed() {
        OperatorConstants.ArmMotorSpeed = SmartDashboard.getNumber("Arm/Wrist Motor Speed", OperatorConstants.ArmMotorSpeed);
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
        goToSoftStop(10); 
        position = 2;
    }

     /** Brings Arm All the way UP */
    public void Up() {
        goToHardStop(OperatorConstants.ArmMotorSpeed, OperatorConstants.ArmAmpLimit);
        position = 3;
    }

    private void goToSoftStop(double rotations) {
        // Retrieve the profiled setpoint for the next timestep. This setpoint moves
        // toward the goal while obeying the constraints.
        m_setpoint = m_profile.calculate(kDt, m_setpoint, m_goal);

        Arm0_pidController.setReference(m_setpoint.position, CANSparkMax.ControlType.kPosition, 0,
                                        m_feedforward.calculate(m_setpoint.velocity));
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
            Arm1.set(-MotorSpeed);
        }
        Arm0.set(0.0);
        Arm1.set(0.0);
    }


    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        getCurrent();
        getPosition();
        getEncoder();
        updateSpeed();
    }
}
