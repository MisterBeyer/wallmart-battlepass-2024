// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.*;

import frc.robot.commands.Helpers.*;
import frc.robot.commands.auto.auto;
import frc.robot.commands.swervedrive.AbsoluteDriveAdv;
import frc.robot.commands.teleop.FourPos;

import frc.robot.subsystems.*;

import java.io.File;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

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
  private SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve/neo"));
  private final Intake intake = new Intake();
  private final Wrist wrist = new Wrist();
  private final Arm arm = new Arm();
  private final Climber climber = new Climber();
  private final Bluetooth bluetooth = new Bluetooth();

  private final ArmCommands armCommands = new ArmCommands(arm);
  private final WristCommands wristCommands = new WristCommands(wrist);
  private final IntakeCommands intakeCommands = new IntakeCommands(intake);

  // Define Command Helpers
  private FourPos arm_control = new FourPos(arm, wrist, intake);
  // OperatorIntake intake_control = new OperatorIntake(intake);

  // CommandJoystick rotationController = new CommandJoystick(1);
  // Replace with CommandPS4Controller or CommandJoystick if needed
  //CommandJoystick driverController = new CommandJoystick(1);

  // CommandJoystick driverController   = new CommandJoystick(3);//(OperatorConstants.DRIVER_CONTROLLER_PORT);
  XboxController driverXbox = new XboxController(0);
  XboxController operatorXbox = new XboxController(1);

  // Define Rumble Commands
  private Rumble rumble = new Rumble(driverXbox, operatorXbox);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Enable Lean Protection
    LeanProtection.LeanProtectEnable();

    // Register Named Auto Commands
    NamedCommands.registerCommand("ArmToStow", arm_control.Stow());
    NamedCommands.registerCommand("ArmToAmp", arm_control.Amp());
    NamedCommands.registerCommand("ArmToSpeaker", arm_control.Speaker());

    // Build an auto chooser. This will use "Skibbidi Auto" as the default option.
    autoChooser = AutoBuilder.buildAutoChooser("Skibbidi Auto");
    SmartDashboard.putData("Auto Chooser", autoChooser);
  

    // Creates a UsbCamera and MjpegServer [1] and connects them
    CameraServer.startAutomaticCapture(0);

    // Creates the CvSink and connects it to the UsbCamera
    //CvSink cvSink = CameraServer.getVideo();

    // Creates the CvSource and MjpegServer [2] and connects them
    //CvSource outputStream = CameraServer.putVideo("Blur", 640, 480);


    // Configure the trigger bindings
    configureBindings();

    @SuppressWarnings("unused")
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

    // @SuppressWarnings("unused")
    /* intakeCommandsommands intakeshoot = new intakeCommandsommands(intake, 
                                              () -> MathUtil.applyDeadband(operatorXbox.getRawAxis(3),
                                                                           OperatorConstants.IntakeDeadBand),
                                              () -> MathUtil.applyDeadband(operatorXbox.getRawAxis(1),
                                                                           OperatorConstants.IntakeDeadBand)); */

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the desired angle NOT angular rotation
    @SuppressWarnings("unused")
    Command driveFieldOrientedDirectAngle = drivebase.driveCommand(
        () -> -MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> -MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> -driverXbox.getRightX(),
        () -> -driverXbox.getRightY());

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the angular velocity of the robot

    //bofa
    
    @SuppressWarnings("unused")
    Command driveFieldOrientedAnglularVelocity = drivebase.driveCommand(
        () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> driverXbox.getRawAxis(2));

    @SuppressWarnings("unused")
    Command driveFieldOrientedDirectAngleSim = drivebase.simDriveCommand(
        () -> MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND),
        () -> driverXbox.getRawAxis(2)); 

    drivebase.setDefaultCommand(
       !RobotBase.isSimulation() ? driveFieldOrientedDirectAngle: driveFieldOrientedAnglularVelocity);
    // intake.setDefaultCommand(intakeshoot);

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

    // Driver Controller Binds
    new JoystickButton(driverXbox, 4).onTrue(arm_control.updateShuffleboard());


    new JoystickButton(driverXbox, 1).onTrue((new InstantCommand(drivebase::zeroGyro)));
    new JoystickButton(driverXbox, 3).onTrue(new InstantCommand(drivebase::addFakeVisionReading));
    new JoystickButton(driverXbox,
                       2).whileTrue(
        Commands.deferredProxy(() -> drivebase.driveToPose(
                                   new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
                              ));
    //   new JoystickButton(driverXbox, 3).whileTrue(new RepeatCommand(new InstantCommand(drivebase::lock, drivebase)));


    //   Operator Controller Binds

    // Arm/Wrist
    //TODO: Redo all of these to prefered buttons and commands when theyre set
    /* Main Arm Movement Controls */
    new JoystickButton(operatorXbox,1).onTrue((arm_control.Stow()));
    new JoystickButton(operatorXbox,2).onTrue(arm_control.Intake());
    new JoystickButton(operatorXbox,3).onTrue(arm_control.Amp());
    new JoystickButton(operatorXbox,4).onTrue(arm_control.Speaker());

    /* Direct Arm Movement Controls */
    new JoystickButton(operatorXbox,5).onTrue(armCommands.MoveForward());
    new JoystickButton(operatorXbox,6).onTrue(armCommands.MoveBackward());
    new JoystickButton(operatorXbox,7).onTrue(wristCommands.MoveForward());
    new JoystickButton(operatorXbox,8).onTrue(wristCommands.MoveBackward());


    Commands.startEnd(()->climber.deploy(Constants.ClimberConstants.FullExtensionEncoder), ()->climber.stop(), climber);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    drivebase.zeroGyro();
    // An example command will be run in autonomous
    return /* new auto(armCommands, wristCommands, intakeCommands); */ autoChooser.getSelected();
  }

  /**
   * Called when Robot Set to Test Mode in Driverstation
   * Makes Robot go "limp" allowing it to be manipulated easily by hang
   */
  public void TestMode() {
    // Stop Intake Just in case
    intake.stop();

    // Set All Modules to Coast
    drivebase.setMotorBrake(false);
    arm.coast();
    wrist.coast();
  }

  public void setDriveMode()
  {
    // Shake the Driver Controller so we don't repeat Block Party
    rumble.driver();
    //drivebase.setDefaultCommand();/..
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
