package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Helpers.IntakeCommands;
import frc.robot.commands.Helpers.Intake.IntakeNote;
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

        this.arm_control = new FourPos(arm, wrist, intake);

        // Shuffleboard!
        SmartDashboard.getNumber("Operator/AutoOperator/Arm Movement Delay", OperatorConstants.armMovementDelay);
        SmartDashboard.getNumber("Operator/AutoOperator/Intake Movement Delay", OperatorConstants.intakeMovementDelay);
        SmartDashboard.getNumber("Operator/AutoOperator/Shooting Cutoff Delay", OperatorConstants.shootingCutoffWaitLimit);
    }

    

    /** Intakes note then stows when note is intaken 
     * @return Command
    */
    public SequentialCommandGroup Intake() {
        return new SequentialCommandGroup(
            Commands.runOnce(() -> System.out.println("[AutoOp] Intaking")),
            arm_control.Intake(),
            new WaitCommand(OperatorConstants.intakeMovementDelay),
            new IntakeNote(intake),
            arm_control.Stow()
        );
    }

    /** Goes from any position to Shoot in speaker and then Stows once note has been launched */
    public SequentialCommandGroup Speaker() {
        return new SequentialCommandGroup(
            Commands.runOnce(() -> System.out.println("[AutoOp] Shooting Speaker")),
            arm_control.Speaker(),
            new WaitCommand(OperatorConstants.armMovementDelay),
            new ParallelRaceGroup(
                intakeC.LaunchNote(),
                new WaitCommand(OperatorConstants.shootingCutoffWaitLimit)),
            arm_control.Stow()
        );
    }

    public SequentialCommandGroup SpeakerBackwards() {
        return new SequentialCommandGroup(
            Commands.runOnce(() -> System.out.println("[AutoOp] Shooting Speaker Backwards")),
            arm_control.SpeakerBackwards(),
            new WaitCommand(OperatorConstants.armMovementDelay),
            new ParallelRaceGroup(
                intakeC.LaunchNote(),
                new WaitCommand(OperatorConstants.shootingCutoffWaitLimit)),
            arm_control.Stow()
        );
    }

    public SequentialCommandGroup SpeakerPodium() {
        return new SequentialCommandGroup(
            Commands.runOnce(() -> System.out.println("[AutoOp] Shooting Speaker From Podium")),
            arm_control.SpeakerPoduim(),
            new WaitCommand(OperatorConstants.armMovementDelay),
            new ParallelRaceGroup(
                intakeC.LaunchNote(),
                new WaitCommand(OperatorConstants.shootingCutoffWaitLimit)),
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
                                  new WaitCommand(OperatorConstants.shootingCutoffWaitLimit)),
            intakeC.Stop(),
            arm_control.Stow()
        );
    }


    /** Just Launches Note using ShootRampUp Command and then Stows */
    public ParallelRaceGroup Launch(){
        return new ParallelRaceGroup(
            intakeC.LaunchNote(),
            new WaitCommand(OperatorConstants.shootingCutoffWaitLimit)
        );
    } 

    /** Update Constants from shuffleboard */
    public Command updateConstants() {
        return Commands.runOnce(() -> {
            OperatorConstants.armMovementDelay = SmartDashboard.getNumber("Operator/AutoOperator/Arm Movment Delay", OperatorConstants.armMovementDelay);
            OperatorConstants.intakeMovementDelay = SmartDashboard.getNumber("Operator/AutoOperator/Intake Movement Delay", OperatorConstants.intakeMovementDelay);
            OperatorConstants.shootingCutoffWaitLimit = SmartDashboard.getNumber("Operator/AutoOperator/Shooting Cutoff Delay", OperatorConstants.shootingCutoffWaitLimit);

            System.out.println("[FourPos] Shuffleboard Updated");
        });
    }
}
