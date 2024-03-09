package frc.robot.commands.Helpers.Climber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class RetractToAmpLimit2 extends Command {
    private Climber climber;

    private enum RetractionStates {
        PRE,
        RETRACTTOCHAIN,
        RETRACTFULLY,
        DONE
    };

    RetractionStates state;
    RetractionStates previoustate;

    private boolean LChainReached;
    private boolean RChainReached;


    public RetractToAmpLimit2(Climber module) {
        this.climber = module;
        addRequirements(climber);

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
        state = RetractionStates.RETRACTTOCHAIN;
        previoustate = RetractionStates.PRE;

        System.out.println("[ClimberCommands/RetractToAmpLimit] Retracting Climber");
    }

    @Override
    public void execute() {
        boolean handlerDone = false;
        do {
            boolean firsttime = (state != previoustate);
            previoustate = state;
            switch(state) {
                case RETRACTTOCHAIN:
                    handlerDone = handleRetractToChain(firsttime, ClimberConstants.ChainReachedAmps);
                    break;
                case RETRACTFULLY:
                    handlerDone = handleRetractFully(firsttime, ClimberConstants.FullRetractionEncoder);
                    break;
                case DONE:
                    break;

                default:
                    System.out.println("[ClimberCommands/ExtendToEncoder] Invalid State Reached! [" + state + "]" );
                    state = RetractionStates.DONE;
                    break;
            }

            if(handlerDone) state = advanceState(state);
        }
        while(handlerDone);
    }

    private RetractionStates advanceState(RetractionStates state) {
        return RetractionStates.values()[state.ordinal() + 1];
    }



    @Override
    public void end(boolean interrupted) {
        climber.stop();
        System.out.println("[ClimberCommands/RetractToAmpLimit] Climber Fully Retracted");
    }

    @Override 
    public boolean isFinished() {
        return state.equals(RetractionStates.DONE);
    }



    /** Retract Both CLimbers until they both reach chain */
    private boolean handleRetractToChain(boolean firsttime, double amplimit) {
        if(firsttime) {
            LChainReached = false;
            RChainReached = false; 

            climber.setBoth(-ClimberConstants.RetractSpeed);
        }

        if(!LChainReached) {  // Check if Left Climber has reached Chain
            if(climber.getLeftCurrent() > amplimit) {
                System.out.println("[ClimberCommands/RetractToAmpLimit] Left Chain Reached");
                climber.setLeft(0);
                
                LChainReached = true;
            }
        }
        if(!RChainReached) { // Check if Right Climber has reached Chain
            if(climber.getRightCurrent() > amplimit) {
                System.out.println("[ClimberCommands/RetractToAmpLimit] Right Chain Reached");
                climber.setRight(0);

                RChainReached = true;
            }
        }

        return (LChainReached && RChainReached);
    }

    /** Find which cLimber is the lowest, to find which one to watch then 
     *  Retrat both motors evenly until the lowest encoder reaches goal retraction encoder positon
    */
    private boolean handleRetractFully(boolean firsttime, double target) {
        if(firsttime) {
            climber.setBoth(ClimberConstants.RetractSpeed);
        }

        return ((climber.getLeftEncoder() <= target) ||
                (climber.getRightEncoder() <= target));
    }
}

