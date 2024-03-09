package frc.robot.commands.Helpers.Climber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class RetractToAmpLimit extends Command {
    private Climber climber;

    private int state;

    private boolean LChainReached;
    private boolean RChainReached;


    public RetractToAmpLimit(Climber module) {
        this.climber = module;
        addRequirements(climber);

        // Reset State
        state = 0;
        LChainReached = false;
        RChainReached = false; 

        // Shuffleboard!
        SmartDashboard.putNumber("Climber/Full Extention Encoder Goal", ClimberConstants.FullExtensionEncoder);
        SmartDashboard.putNumber("Climber/Full Retraction Encoder Goal", ClimberConstants.FullRetractionEncoder);
        SmartDashboard.putNumber("Climber/Chain reached Amp Limit", ClimberConstants.ChainReachedAmps);
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        ClimberConstants.FullExtensionEncoder = SmartDashboard.getNumber("Climber/Full Extention Encoder Goal", ClimberConstants.FullExtensionEncoder);
        ClimberConstants.FullRetractionEncoder = SmartDashboard.getNumber("Climber/Full Retraction Encoder Goal", ClimberConstants.FullRetractionEncoder);
        ClimberConstants.ChainReachedAmps = SmartDashboard.getNumber("Climber/Chain reached Amp Limit", ClimberConstants.ChainReachedAmps);

        System.out.println("[ClimberCommands/ExtendToEncoder] Shuffleboard Updated");
    } 
    

    @Override
    public void initialize() {
        System.out.println("[ClimberCommands/RetractToAmpLimit] Retracting Climber");
        climber.setBoth(-ClimberConstants.RetractSpeed);
    }

    @Override
    public void execute() {
        if(state == 0) { // Retract Both CLimbers until they both reach chain
            if(!LChainReached) {  // Check if Left Climber has reached Chain
                if(climber.getLeftCurrent() > ClimberConstants.ChainReachedAmps) {
                    System.out.println("[ClimberCommands/RetractToAmpLimit] Left Chain Reached");
                    climber.setLeft(0);
                    
                    LChainReached = true;
                }
            }
            if(!RChainReached) { // Check if Right Climber has reached Chain
                if(climber.getRightCurrent() > ClimberConstants.ChainReachedAmps) {
                    System.out.println("[ClimberCommands/RetractToAmpLimit] Right Chain Reached");
                    climber.setRight(0);

                    RChainReached = true;
                }
            }

            if(LChainReached && RChainReached) state = 1;
        }

        if(state == 1) { // Find which cLimber is the lowest, to find which one to watch for next step
            if(climber.getLeftEncoder() <= climber.getRightEncoder()) state = 2;       // Left is Lowest so go to state 2
            else state = 3;  // Right is Lowest so go to state 3
        }

        // Retrat both motors evenly until the lowest encoder reaches goal retraction encoder positon
        if(state == 2) {
            if(climber.getLeftEncoder() > ClimberConstants.FullRetractionEncoder) {
                climber.setBoth(-ClimberConstants.RetractSpeed);
            }
            else state = 4;
        }
        if(state == 3) {
            if(climber.getRightEncoder() > ClimberConstants.FullRetractionEncoder) {
                climber.setBoth(-ClimberConstants.RetractSpeed);
            }
            else state = 4;
        }
    }


    @Override
    public void end(boolean interrupted) {
        climber.stop();
        System.out.println("[ClimberCommands/RetractToAmpLimit] Climber Fully Retracted");
    }

    @Override 
    public boolean isFinished() {
        return state == 4;
    }
}
