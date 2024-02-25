package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.OperatorConstants;

public class Rumble extends SubsystemBase{
    private XboxController driver;
    private XboxController operator;

    private double time = 1; // seconds
    
    public Rumble(XboxController driverXbox, XboxController operatorXbox) {
        // Define Xbox Controllers
        this.driver = driverXbox;
        this.operator = operatorXbox;

        
    }

    // Ligma Rumble Support
    // Don't Question this code


    /* Shakes Driver Controller */
    public SequentialCommandGroup driver() {
        SequentialCommandGroup command = new SequentialCommandGroup(
            Commands.runOnce(() -> driver.setRumble(GenericHID.RumbleType.kBothRumble, OperatorConstants.RumbleStrength), this),
            new WaitCommand(time),
            Commands.runOnce(() -> driver.setRumble(GenericHID.RumbleType.kBothRumble, OperatorConstants.RumbleStrength), this)
        );
       return command;
    }

    /* Shakes Operator Controller */
    public SequentialCommandGroup operator() {
        SequentialCommandGroup command = new SequentialCommandGroup(
            Commands.runOnce(() -> operator.setRumble(GenericHID.RumbleType.kBothRumble, OperatorConstants.RumbleStrength), this),
            new WaitCommand(time),
            Commands.runOnce(() -> operator.setRumble(GenericHID.RumbleType.kBothRumble, OperatorConstants.RumbleStrength), this)
        );
       return command;
    }
}
