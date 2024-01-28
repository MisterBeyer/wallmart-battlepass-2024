// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.intake.IntakeShoot;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDrive;
import frc.robot.commands.swervedrive.drivebase.AbsoluteFieldDrive;
import frc.robot.commands.swervedrive.drivebase.AbsoluteDriveAdv;
import frc.robot.commands.swervedrive.drivebase.TeleopDrive;
import frc.robot.subsystems.LeanProtection;
import frc.robot.subsystems.SwerveSubsystem;

import frc.robot.subsystems.Intake;

import java.io.File;

import com.pathplanner.lib.auto.AutoBuilder;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
  // Create and auto chooser for use with SmartDashboard
  private final SendableChooser<Command> autoChooser;

  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve/neo"));

  private final Intake noteintake = new Intake();

  // CommandJoystick rotationController = new CommandJoystick(1);
  // Replace with CommandPS4Controller or CommandJoystick if needed
  //CommandJoystick driverController = new CommandJoystick(1);

  // CommandJoystick driverController   = new CommandJoystick(3);//(OperatorConstants.DRIVER_CONTROLLER_PORT);
  XboxController driverXbox = new XboxController(0);
  XboxController operatorXbox = new XboxController(1);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands. 
  */
  public RobotContainer()
  {
    // Enable Lean Protection
    LeanProtection.LeanProtectEnable();

    // Build an auto chooser. This will use "Skibbidi Auto" as the default option.
    autoChooser = AutoBuilder.buildAutoChooser("Skibbidi Auto");
    SmartDashboard.putData("Auto Chooser", autoChooser);

    // Intake speed variables
    SmartDashboard.putNumber("Top Intake Speed", Constants.OperatorConstants.IntakeSpeedTop);
    SmartDashboard.putNumber("Bottom Intake Speed", Constants.OperatorConstants.IntakeSpeedBottom);

    // Configure the trigger bindings
    configureBindings();
    
AbsoluteDrive closedAbsoluteDrive = new AbsoluteDrive(drivebase,
// Applies deadbands and inverts controls because joysticks
// are back-right positive while robot
// controls are front-left positive
() -> MathUtil.applyDeadband(driverXbox.getLeftY(),
OperatorConstants.LEFT_Y_DEADBAND),
() -> MathUtil.applyDeadband(driverXbox.getLeftX(),
OperatorConstants.LEFT_X_DEADBAND),
() -> -driverXbox.getRightX(),
() -> -driverXbox.getRightY());

AbsoluteFieldDrive closedFieldAbsoluteDrive = new AbsoluteFieldDrive(drivebase,
() ->
MathUtil.applyDeadband(driverXbox.getLeftY(),
OperatorConstants.LEFT_Y_DEADBAND),
() -> MathUtil.applyDeadband(driverXbox.getLeftX(),
OperatorConstants.LEFT_X_DEADBAND),
() -> driverXbox.getRawAxis(2));

AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(drivebase,
() -> MathUtil.applyDeadband(driverXbox.getLeftY(),
OperatorConstants.LEFT_Y_DEADBAND),
() -> MathUtil.applyDeadband(driverXbox.getLeftX(),
OperatorConstants.LEFT_X_DEADBAND),
() -> MathUtil.applyDeadband(driverXbox.getRightX(),
OperatorConstants.RIGHT_X_DEADBAND), 
driverXbox::getYButtonPressed, 
driverXbox::getAButtonPressed, 
driverXbox::getXButtonPressed, 
driverXbox::getBButtonPressed);

TeleopDrive simClosedFieldRel = new TeleopDrive(drivebase,
() -> MathUtil.applyDeadband(driverXbox.getLeftY(),
OperatorConstants.LEFT_Y_DEADBAND),
() -> MathUtil.applyDeadband(driverXbox.getLeftX(),
OperatorConstants.LEFT_X_DEADBAND),
() -> driverXbox.getRawAxis(2), () -> true);

IntakeShoot intakeshoot = new IntakeShoot(noteintake,
() -> MathUtil.applyDeadband(operatorXbox.getLeftY(),
OperatorConstants.IntakeDeadBand),
() -> MathUtil.applyDeadband(operatorXbox.getRightY(),
OperatorConstants.IntakeDeadBand));
    //TeleopDrive closedFieldRel = new TeleopDrive(
     //   drivebase,
      //  () -> MathUtil.applyDeadband(driverController.getRawAxis(1), OperatorConstants.LEFT_Y_DEADBAND),
       // () -> MathUtil.applyDeadband(driverController.getRawAxis(0), OperatorConstants.LEFT_X_DEADBAND),
        //() -> -driverController.getRawAxis(2), () -> true);

    drivebase.setDefaultCommand(!RobotBase.isSimulation() ? closedAbsoluteDriveAdv : closedFieldAbsoluteDrive); 
    noteintake.setDefaultCommand(intakeshoot);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new JoystickButton(driverXbox, 1).onTrue((new InstantCommand(drivebase::zeroGyro)));
    new JoystickButton(operatorXbox,1).onTrue((new InstantCommand(noteintake::updatespeed)));

    //new JoystickButton(driverXbox, 3).onTrue(new InstantCommand(drivebase::addFakeVisionReading));
    //new JoystickButton(driverXbox, 3).whileTrue(new RepeatCommand(new InstantCommand(drivebase::lock, drivebase)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    return autoChooser.getSelected();
  }

  public void setDriveMode()
  {
    //drivebase.setDefaultCommand();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
