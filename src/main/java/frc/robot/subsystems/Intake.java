package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Intake extends SubsystemBase{
    private final CANSparkMax Intake0 = new CANSparkMax(30, MotorType.kBrushless);
    private final CANSparkMax Intake1 = new CANSparkMax(31, MotorType.kBrushless);

    public void intake() {
      Intake0.setIdleMode(CANSparkMax.IdleMode.kCoast);
      Intake1.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public void setspeed(double speedTop, double speedBottom){
        Intake0.set(speedTop);
        Intake1.set(speedBottom);
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