package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;

import frc.robot.commands.Helpers.Climber.*;

//dont unplug the ethernet
public class ClimberCommands{
    
    private Climber climber;


    /** Helper Commands For Intake
     *  @param module module to use as intake
     */
    public ClimberCommands(Climber module) {
        // Assign Control
        this.climber = module;

        // ShuffleBoard!
        SmartDashboard.putNumber("Climber/Extention Motor Speed", ClimberConstants.ExtendSpeed);
        SmartDashboard.putNumber("Climber/Retraction Motor Speed", ClimberConstants.RetractSpeed);
    }


    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        // Motor Speeds
        ClimberConstants.ExtendSpeed = SmartDashboard.getNumber("Climber/Extention Motor Speed", ClimberConstants.ExtendSpeed);
        ClimberConstants.RetractSpeed = SmartDashboard.getNumber("Climber/Retraction Motor Speed", ClimberConstants.RetractSpeed);

        System.out.println("[ClimberCommands] Shuffleboard Updated");

        // Update Constants of Subsystems
        ExtendToEncoder.updateConstants();
        RetractToAmpLimit.updateConstants();
        climber.updateConstants();
    }


    /** Extends Climber until Fully Extended, as described by an encoder value */
    public Command Extend() {
        return new ExtendToEncoder(climber);
    }

     /** Retracts Climber fully, keeping robot level */
    public Command Retract() {
        return new RetractToAmpLimit(climber); 
    }



    /** Manually Extends Both Climbers */
    public Command adjustBothUp() {
        return Commands.startEnd(() -> {
                    System.out.println("[ClimberCommands] Adjust Both Up");
                    climber.setBoth(ClimberConstants.ExtendSpeed);
                },
                () -> climber.stop(),
                climber);
    }

    /** Manually Lowers Left Climber  */
    public Command adjustLeftDown() {
        return Commands.startEnd(() -> {
                System.out.println("[ClimberCommands] Adjust Left Down");
                climber.setLeft(-ClimberConstants.RetractSpeed);
                },
                () -> climber.stop());
    }

    /** Manually Lowers Right Climber  */
    public Command adjustRightDown() {
        return Commands.startEnd(() -> {
                System.out.println("[ClimberCommands] Adjust Right Down");
                climber.setRight(-ClimberConstants.RetractSpeed);
                },
                () -> climber.stop());
    }



    /** Stops both Climbers */
    public Command stop() {
        return Commands.runOnce(() -> climber.stop(), climber);
    }

}