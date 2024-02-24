package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;

public class Rumble extends SubsystemBase {
    private XboxController driver;
    private XboxController operator;

    private double time = 1; // seconds
    
    public Rumble(XboxController driverXbox, XboxController operatorXbox) {
        // Define Xbox Controllers
        this.driver = driverXbox;
        this.operator = operatorXbox;

        
    }

    /* Shakes Driver Controller */
   // public Command driver() {
     //   return driver.setRumble(GenericHID.RumbleType.kBothRumble, OperatorConstants.strength);
    }

    /* Shakes Operator Controller */
   // public Command operator() {
     //   return operator.setRumble(GenericHID.RumbleType.kBothRumble, OperatorConstants.strength);
    //}
//}
