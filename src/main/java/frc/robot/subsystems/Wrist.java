package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import frc.robot.Constants.OperatorConstants;

public class Wrist extends SubsystemBase{
    private final CANSparkMax Wrist0 = new CANSparkMax(30, MotorType.kBrushless);

    boolean position;

    public Wrist() {
        Wrist0.setIdleMode(CANSparkMax.IdleMode.kBrake);
      }


    /** @return Wrist motor's Output Amperage */
    public double getCurrent() {
        double current = Wrist0.getOutputCurrent();
        SmartDashboard.putNumber("Arm/Wrist Motor Current", current);
        return current;
    }

    /** @return Wrists Positon as a boolean
     *         True: Down
     *         False: Up
     */
    public boolean getPosition() {
        SmartDashboard.putBoolean("Arm/Wrist Position", position);
        return position;
      }


    /** Updates Motor Speeds from shuffleboard */
    public void updateSpeed() {
      OperatorConstants.WristMotorSpeed = SmartDashboard.getNumber("Arm/Wrist Motor Speed", OperatorConstants.WristMotorSpeed);
    }

    /**
     * Sets Wrist motor(s) to coast, allowing arm to be manupilated by hand
     */
    public void coast() {
        Wrist0.setIdleMode(CANSparkMax.IdleMode.kCoast); 
    }

    /**
     * Sets wrist to be up or down
     * @param status True is up, False is down
     */
    public void SetWristPos(boolean status) {
        if (status) {
            goToHardStop(OperatorConstants.WristMotorSpeed, OperatorConstants.WristAmpLimit);
            position = true;
        }
        else if (!status) {
            goToHardStop(OperatorConstants.WristMotorSpeed, OperatorConstants.WristAmpLimit);
            position = false;
        }
    }


    /**
     * Drive motor until it hits a hardstop, provided by the frame
     * @param MotorSpeed Maximuim allowed speed of motor
     * @param AmpLimit Current Limit to know when hardstop has been reached
     */
    private void goToHardStop( double MotorSpeed, double AmpLimit) {
        while (Wrist0.getOutputCurrent() < AmpLimit) {
            Wrist0.set(MotorSpeed);
        }
        Wrist0.set(0.0);
    }


    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        getCurrent();
        getPosition();
        updateSpeed();
    }
}
