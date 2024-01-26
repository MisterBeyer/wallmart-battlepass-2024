package frc.robot.subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Intake extends SubsystemBase{
    private final CANSparkMax Intake0 = new CANSparkMax(30, MotorType.kBrushless);
    private final CANSparkMax Intake1 = new CANSparkMax(31, MotorType.kBrushless);

    public void intake() {
      Intake0.setIdleMode(CANSparkMax.IdleMode.kCoast);
      Intake1.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    /**
     * Pulls the IntakeSpeed variables from shuffleboard
     */
    public void updatespeed() {
      Constants.OperatorConstants.IntakeSpeedTop = SmartDashboard.getNumber("Top Intake Speed", Constants.OperatorConstants.IntakeSpeedTop);
      Constants.OperatorConstants.IntakeSpeedBottom = SmartDashboard.getNumber("Bottom Intake Speed", Constants.OperatorConstants.IntakeSpeedBottom);
    }

    /**
     * Sets speed of both intake motors
     * @param speedTop Speed of Top Motor
     * @param speedBottom Speed of Bottom Motor
     */
    public void setspeed(double speedTop, double speedBottom){
        Intake0.set(speedBottom);
        Intake1.set(speedTop);
        //I might be done guys
        //He might not be either
     }
     public void stop(){
      Intake0.set(0.0);
      Intake1.set(0.0);
     }
     
    // My code is perfect 
    // I wish
    //          - Honda Civic [2009]


     @Override
     public void periodic() {

       // This method will be called once per scheduler run
     }
}