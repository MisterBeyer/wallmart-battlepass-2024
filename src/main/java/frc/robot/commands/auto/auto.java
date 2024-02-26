package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Helpers.ArmCommands;
import frc.robot.commands.Helpers.IntakeShoot;
import frc.robot.commands.Helpers.WristCommands;
import frc.robot.subsystems.Intake;
import com.pathplanner.lib.*;


public class auto extends SequentialCommandGroup {
    private ArmCommands armCommands;
    private WristCommands wristCommands;
    private IntakeShoot intakeShoot;

    public auto(ArmCommands inarmCommands, WristCommands inwristCommands, IntakeShoot inintakeShoot
    ) {

    this.armCommands =  inarmCommands;
    this.intakeShoot = inintakeShoot;
    this.wristCommands = inwristCommands;
      addCommands(new ParallelCommandGroup(armCommands.goToSpeaker(), wristCommands.goToSpeaker()));
      //addCommands(new InstantCommand(intakeShoot::Shoot));
      addCommands(new ParallelCommandGroup(armCommands.goToStow(), wristCommands.goToStow()));
      addCommands(new PathPlannerAuto("CHS Roblox Exam"));


    }
    
}
