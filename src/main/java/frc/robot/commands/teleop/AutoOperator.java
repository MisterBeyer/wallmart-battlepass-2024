package frc.robot.commands.teleop;

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
            arm_control.Intake(),
            new IntakeNote(intake),
            arm_control.Stow()
        );
    }

    /** Goes from any position to Shoot in speaker and then Stows once note has been launched */
    public SequentialCommandGroup Speaker() {
        return new SequentialCommandGroup(
            arm_control.Speaker(),
            new ShootRampUp(intake),
            arm_control.Stow()
        );
    }

    /** Goes from any position to Shoot in speaker and the Stows once note has been launched */
    public SequentialCommandGroup Amp() {
        return new SequentialCommandGroup(
            arm_control.Amp(),
            intakeC.EjectBackward(),
            new WaitCommand(1),
            intakeC.Stop(),
            arm_control.Stow()
        );
    }
}
