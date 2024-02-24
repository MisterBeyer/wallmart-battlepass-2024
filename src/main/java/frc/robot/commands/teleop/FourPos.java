package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Helpers.ArmCommands;
import frc.robot.commands.Helpers.IntakeShoot;
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
    private IntakeShoot IntakeC;//TODO: Speak to oliver about adding intake Commands


    public FourPos(Arm armprovider, Wrist wristprovider, Intake intakeProvider) {
        this.arm = armprovider;
        this.wrist = wristprovider;
        this.intake = intakeProvider;

        // Setup Helper Commands
        ArmC = new ArmCommands(this.arm);
        WristC = new WristCommands(this.wrist);
        IntakeC = new IntakeShoot(this.intake, null, null);
    }



    /** Brings the Robot into the Stow Position */
    public ParallelCommandGroup Stow() {
        ParallelCommandGroup stow = new ParallelCommandGroup(
            new InstantCommand(intake::stop),    
        
            ArmC.goToStow(),
            WristC.goToStow()
        );
        return stow;
    }

    /** Brings the Robot's Intake out */
    public ParallelCommandGroup Intake() {
        ParallelCommandGroup ground = new ParallelCommandGroup(
            new InstantCommand(intake::stop),    // TODO: Idle intake? 
        
            ArmC.goToStow(),
            WristC.goToIntake()

        );
        return ground;
    }

    /** Brings the Robot into Position to Shoot into Amp */
    public ParallelCommandGroup Amp() {
        ParallelCommandGroup amp = new ParallelCommandGroup(
            new InstantCommand(intake::stop),    
        
            ArmC.goToAmp(),
            WristC.goToAmp()
        );
        return amp;
    }

    /** Brings the Robot into Position to Shoot into Speaker */
    public ParallelCommandGroup Speaker() {
        ParallelCommandGroup speaker = new ParallelCommandGroup(
            new InstantCommand(intake::stop),    
        
            ArmC.goToSpeaker(),
            WristC.goToSpeaker()

        );
        return speaker;
    }
}