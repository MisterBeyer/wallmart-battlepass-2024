package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Helpers.IntakeCommands;
import frc.robot.commands.Helpers.Intake.IntakeNote;
import frc.robot.commands.Helpers.Intake.ShootRampUp;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class AutoOperator{
    private Arm arm;
    private Wrist wrist;
    private Intake intake;

    private IntakeCommands intakeC;

    private FourPos arm_control;

    /** Automatic Scoring and Intaking with a single button press (Skibbidi) */
    public AutoOperator(Arm armprovider, Wrist wristprovider, Intake intakeprovider) {
        this.arm = armprovider;
        this.wrist = wristprovider;
        this.intake = intakeprovider;
        
        this.intakeC = new IntakeCommands(this.intake);

        this.arm_control = new FourPos(arm, wrist);
    }

    

    /** Intakes note then stows when note is intaken 
     * @return Command
    */
    public SequentialCommandGroup Intake() {
        return new SequentialCommandGroup(
            Commands.runOnce(() -> System.out.println("[AutoOp] Intaking")),
            arm_control.Intake(),
            new WaitCommand(0.5),
            new IntakeNote(intake),
            arm_control.Stow()
        );
    }

    /** Goes from any position to Shoot in speaker and then Stows once note has been launched */
    public SequentialCommandGroup Speaker() {
        return new SequentialCommandGroup(
            Commands.runOnce(() -> System.out.println("[AutoOp] Shooting Speaker")),
            arm_control.Speaker(),
            new WaitCommand(1),
            new ShootRampUp(intake),
            arm_control.Stow()
        );
    }

    public SequentialCommandGroup SpeakerBackwards() {
        return new SequentialCommandGroup(
            Commands.runOnce(() -> System.out.println("[AutoOp] Shooting Speaker Backwards")),
            arm_control.SpeakerBackwards(),
            new WaitCommand(1),
            new ShootRampUp(intake),
            arm_control.Stow()
        );
    }

    public SequentialCommandGroup SpeakerPodium() {
        return new SequentialCommandGroup(
            Commands.runOnce(() -> System.out.println("[AutoOp] Shooting Speaker From Podium")),
            arm_control.SpeakerPoduim(),
            new WaitCommand(1),
            new ShootRampUp(intake),
            arm_control.Stow()
        );
    }

    /** Goes from any position to Shoot in speaker and the Stows once note has been launched */
    public SequentialCommandGroup Amp() {
        return new SequentialCommandGroup(
            Commands.runOnce(() -> System.out.println("[AutoOp] Shooting Amp")),
            arm_control.Amp(),
            new WaitCommand(2),
            new ParallelRaceGroup(intakeC.EjectBackward(),
                                  new WaitCommand(1)),
            intakeC.Stop(),
            arm_control.Stow()
        );
    }


    /** Just Launches Note using ShootRampUp Command and then Stows */
    public ParallelRaceGroup Launch(){
        return new ParallelRaceGroup(
            new ShootRampUp(intake),
            new WaitCommand(1.25)
        );
    } 
}
