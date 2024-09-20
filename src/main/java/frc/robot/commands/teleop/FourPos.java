package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Helpers.ArmCommands;
import frc.robot.commands.Helpers.IntakeCommands;
import frc.robot.commands.Helpers.WristCommands;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Intake;


/* Adds four commands to be used by operator to control the arm/wrist/intake module
 *      1. Stow
 *      2. Inake
 *      3. Amp position
 *      4. Speaker position
*/

public class FourPos{
    // Define Subsystems
    private Arm arm;
    private Wrist wrist;
    private Intake intake;

    // Define Helpers
    private ArmCommands ArmC;
    private WristCommands WristC;
    private IntakeCommands IntakeC;


    public FourPos(Arm armprovider, Wrist wristprovider, Intake intakeprovider) {
        this.arm = armprovider;
        this.wrist = wristprovider;
        this.intake = intakeprovider;

        // Setup Helper Commands
        ArmC = new ArmCommands(this.arm);
        WristC = new WristCommands(this.wrist);
        IntakeC = new IntakeCommands(this.intake);

        // Shuffleboard!
        SmartDashboard.putNumber("Operator/Note Pullback start delay", OperatorConstants.PullbackDelay);
    }



    /** Brings the Robot into the Stow Position */
    public ParallelCommandGroup Stow() {
        ParallelCommandGroup stow = new ParallelCommandGroup(
            Commands.runOnce(() -> System.out.println("[FourPos] Stow")),
            ArmC.goToStow(),
            WristC.goToStow()
        );
        return stow;
    }

    /** Brings the Robot's Intake out */
    public ParallelCommandGroup Intake() {
        ParallelCommandGroup ground = new ParallelCommandGroup(
            Commands.runOnce(() -> System.out.println("[FourPos] Intake")),
            ArmC.goToStow(),
            WristC.goToIntake()

        );
        return ground;
    }

    /** Brings the Robot into Position to Shoot into Amp */
    public ParallelCommandGroup Amp() {
        ParallelCommandGroup amp = new ParallelCommandGroup(
            Commands.runOnce(() -> System.out.println("[FourPos] Amp")),
            ArmC.goToAmp(),
            WristC.goToAmp()
        );
        return amp;
    }

    /** Brings the Robot into Position to Shoot into Speaker */
    public ParallelCommandGroup Speaker() {
        ParallelCommandGroup speaker = new ParallelCommandGroup(
            Commands.runOnce(() -> System.out.println("[FourPos] Speaker")),
            ArmC.goToSpeaker(),
            WristC.goToSpeaker(),
            new SequentialCommandGroup(
                new WaitCommand(OperatorConstants.PullbackDelay),
                IntakeC.PullBackNote()
            )
        );
        return speaker;
    }

    
    /** Brings the Robot to the Alternative Speaker Posisiton to Shoot into Speaker while facing backwards */
    public ParallelCommandGroup SpeakerBackwards() {
        ParallelCommandGroup backwardspeaker = new ParallelCommandGroup(
            Commands.runOnce(() -> System.out.println("[FourPos] Backwards Speaker")),
            ArmC.goToBackwardsSpeaker(),
            WristC.goToBackwardsSpeaker(),
            new SequentialCommandGroup(
                new WaitCommand(OperatorConstants.PullbackDelay),
                IntakeC.PullBackNote()
            )
        );
        return backwardspeaker;
    }

        
    /** Brings the Robot to the Alternative Speaker Posisiton to Shoot into Speaker while at the Podium */
    public ParallelCommandGroup SpeakerPoduim() {
        ParallelCommandGroup backwardspeaker = new ParallelCommandGroup(
            Commands.runOnce(() -> System.out.println("[FourPos] Speaker from Podium")),
            ArmC.goToPodiumSpeaker(),
            WristC.goToPodiumSpeaker(),
            new SequentialCommandGroup(
                new WaitCommand(OperatorConstants.PullbackDelay),
                IntakeC.PullBackNote()
            )
        );
        return backwardspeaker;
    }



    /** Update Constants of all Subsystems */
    public SequentialCommandGroup updateShuffleboard() {
        SequentialCommandGroup update = new SequentialCommandGroup(
            Commands.runOnce(() -> {
                OperatorConstants.PullbackDelay = SmartDashboard.getNumber("Operator/Note Pullback start delay", OperatorConstants.PullbackDelay);
                System.out.println("[FourPos] Shuffleboard Updated");
            }),
            new InstantCommand(ArmC::updateConstants),
            new InstantCommand(WristC::updateConstants)
        );

        return update;
    }
}