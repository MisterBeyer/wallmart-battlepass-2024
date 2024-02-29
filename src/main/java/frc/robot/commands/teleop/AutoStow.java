package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.Helpers.Intake.IntakeNote;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class AutoStow extends SequentialCommandGroup {
    private Arm arm;
    private Wrist wrist;
    private Intake intake;

    private FourPos arm_control;

        public AutoStow(Arm armprovider, Wrist wristprovider, Intake intakeprovider) {
            this.arm = armprovider;
            this.wrist = wristprovider;
            this.intake = intakeprovider;
            
            this.arm_control = new FourPos(arm, wrist);


            addCommands(
                arm_control.Intake(),
                new IntakeNote(intake),
                arm_control.Stow()
            );
        }
}
