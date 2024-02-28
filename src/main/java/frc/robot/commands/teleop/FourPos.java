package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Helpers.ArmCommands;
import frc.robot.commands.Helpers.IntakeCommands;
import frc.robot.commands.Helpers.WristCommands;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Wrist;


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

    // Define Helpers
    private ArmCommands ArmC;
    private WristCommands WristC;


    public FourPos(Arm armprovider, Wrist wristprovider) {
        this.arm = armprovider;
        this.wrist = wristprovider;

        // Setup Helper Commands
        ArmC = new ArmCommands(this.arm);
        WristC = new WristCommands(this.wrist);
    }



    /** Brings the Robot into the Stow Position */
    public ParallelCommandGroup Stow() {
        ParallelCommandGroup stow = new ParallelCommandGroup(
            ArmC.goToStow(),
            WristC.goToStow()
        );
        return stow;
    }

    /** Brings the Robot's Intake out */
    public ParallelCommandGroup Intake() {
        ParallelCommandGroup ground = new ParallelCommandGroup(
            ArmC.goToStow(),
            WristC.goToIntake()

        );
        return ground;
    }

    /** Brings the Robot into Position to Shoot into Amp */
    public ParallelCommandGroup Amp() {
        ParallelCommandGroup amp = new ParallelCommandGroup(
            ArmC.goToAmp(),
            WristC.goToAmp()
        );
        return amp;
    }

    /** Brings the Robot into Position to Shoot into Speaker */
    public ParallelCommandGroup Speaker() {
        ParallelCommandGroup speaker = new ParallelCommandGroup(     
            ArmC.goToSpeaker(),
            WristC.goToSpeaker()

        );
        return speaker;
    }



    /** Update Constants of all Subsystems */
    public SequentialCommandGroup updateShuffleboard() {
        SequentialCommandGroup update = new SequentialCommandGroup(
            new InstantCommand(ArmC::updateConstants),
            new InstantCommand(WristC::updateConstants)
        );

        return update;
    }
}