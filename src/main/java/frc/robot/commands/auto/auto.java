package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Helpers.ArmCommands;
import frc.robot.commands.Helpers.IntakeCommands;
import frc.robot.commands.Helpers.WristCommands;
import frc.robot.subsystems.Intake;


public class auto extends SequentialCommandGroup {
    private ArmCommands armCommands;
    private WristCommands wristCommands;
    private IntakeCommands intakeShoot;

    public auto(ArmCommands inarmCommands, WristCommands inwristCommands, IntakeCommands inintakeShoot
    ) {

    this.armCommands =  inarmCommands;
    this.intakeShoot = inintakeShoot;
    this.wristCommands = inwristCommands;


      addCommands(armCommands.goToSpeaker());
      addCommands(wristCommands.goToSpeaker());
      addCommands(new InstantCommand(intakeShoot::Shoot));


    }
    
}
