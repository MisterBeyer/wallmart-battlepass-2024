package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class intake extends SubsystemBase{
    private final CANSparkMax Intake0 = new CANSparkMax(2, MotorType.kBrushless);
    private final CANSparkMax Intake1 = new CANSparkMax(1, MotorType.kBrushless);

    public intake() {
      Intake0.setIdleMode(CANSparkMax.IdleMode.kCoast);
      Intake1.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public void setspeed(double speed){
        Intake0.set(speed);
        Intake1.set(-speed);
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