// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.*;
import frc.robot.commands.Helpers.*;
import frc.robot.commands.teleop.AutoOperator;
import frc.robot.commands.teleop.FourPos;

import frc.robot.subsystems.*;

import java.beans.Beans;
import java.io.File;
/*  Not sure how this got hre
import java.sql.DriverPropertyInfo; */
import java.sql.Driver;

import org.photonvision.PhotonCamera;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
//import com.pathplanner.lib.commands.PathPlannerAuto;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
  private final int DriverControllerPort = 0;
  private final int OperatorControllerPort = 1;

  // Create and auto chooser for use with SmartDashboard
  private SendableChooser<Command> autoChooser;

  // The robot's subsystems and commands are defined here...
  private SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve/neo"));
  private final Intake intake = new Intake();
  private final Wrist wrist = new Wrist();
  private final Arm arm = new Arm();

  private final Climber climber = new Climber();
  //private final Climber climber = new Climber();
 // private final Bluetooth bluetooth = new Bluetooth();

  private final ArmCommands armCommands = new ArmCommands(arm);
  private final WristCommands wristCommands = new WristCommands(wrist);
  private final IntakeCommands intakeCommands = new IntakeCommands(intake);
  private final ClimberCommands climberCommands = new ClimberCommands(climber);
  private final CaprisonCommands limelightCommands = new CaprisonCommands();
  private final DoritoCommands visionCommands = new DoritoCommands();

  private final LimeLight Limelight = new LimeLight();
  private final PhotonCamera apriltagCam = new PhotonCamera("");
 // private final Vision apriltag = new Vision(drivebase.getPose(), drivebase.getObject(field));


  // Define Command Helpers
  private FourPos arm_control = new FourPos(arm, wrist, intake);
  private AutoOperator autoOP = new AutoOperator(arm, wrist, intake);

  private double x = 1;

  // OperatorIntake intake_control = new OperatorIntake(intake);

  // CommandJoystick rotationController = new CommandJoystick(1);
  // Replace with CommandPS4Controller or CommandJoystick if needed
  //CommandJoystick driverController = new CommandJoystick(1);

  // CommandJoystick driverController   = new CommandJoystick(3);//(OperatorConstants.DRIVER_CONTROLLER_PORT);
  CommandXboxController driverXbox = new CommandXboxController(DriverControllerPort);
  CommandXboxController operatorXbox = new CommandXboxController(OperatorControllerPort);

  // Define Rumble Commands
  //private Rumble rumble = new Rumble(DriverControllerPort, OperatorControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Enable Lean Protection
    LeanProtection.LeanProtectEnable();
    Limelight.doLimelightThing();
    // Register Named Auto Commands

    /* Manual Controls for Auto - Only for certain edge cases, use Automated controls for most autos */
    NamedCommands.registerCommand("ArmToStow", arm_control.Stow());               // Arm/Wrist
    NamedCommands.registerCommand("ArmToIntake", arm_control.Intake());
    NamedCommands.registerCommand("ArmToAmp", arm_control.Amp());
    NamedCommands.registerCommand("ArmToSpeaker", arm_control.Speaker());
    NamedCommands.registerCommand("ArmToBackwardsSpeaker", arm_control.SpeakerBackwards());
    NamedCommands.registerCommand("ArmToPodiumSpeaker", arm_control.SpeakerPoduim());
    NamedCommands.registerCommand("AutoSpeakerMoveBackwards", arm_control.SpeakerBackwards()); // Added to avoid pathplanner changes

    NamedCommands.registerCommand("IntakeEjectF", intakeCommands.EjectForward()); // Intake
    NamedCommands.registerCommand("IntakeEjectB", intakeCommands.EjectBackward());
    NamedCommands.registerCommand("IntakeIn", intakeCommands.Intake());
    NamedCommands.registerCommand("IntakePullBack", intakeCommands.PullBackNote());
    NamedCommands.registerCommand("IntakeShoot", intakeCommands.LaunchNote());
    NamedCommands.registerCommand("IntakeStop", intakeCommands.Stop());

    /* Automated Controls for Auto */
    NamedCommands.registerCommand("AutoIntake", autoOP.Intake());                 // AutoOP
    NamedCommands.registerCommand("AutoAmp", autoOP.Amp());
    NamedCommands.registerCommand("AutoSpeaker", autoOP.Speaker());
    NamedCommands.registerCommand("AutoSpeakerBackwards", autoOP.SpeakerBackwards());
    NamedCommands.registerCommand("AutoSpeakerPodium", autoOP.SpeakerPodium());
    NamedCommands.registerCommand("AutoSpeakerLaunchBackwards", autoOP.Launch());


    // Build an auto chooser. This will use "Skibbidi Auto" as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);

    /* try {
      autoChooser.setDefaultOption("Disabled", new WaitCommand(0.1));
      autoChooser.addOption("Skibbidi Auto", new PathPlannerAuto("Skibbidi Auto"));
      SmartDashboard.putData("Auto Chooser", autoChooser);
    }
    catch (Exception e) {
      System.out.println("PathPlanner Error: Failed to Load Path");
      System.out.println(e.toString());
    } */

    SmartDashboard.putNumber("X", x);

  

    // Creates a UsbCamera and MjpegServer [1] and connects them
    //CameraServer.startAutomaticCapture(0);

    // Creates the CvSink and connects it to the UsbCamera
    //CvSink cvSink = CameraServer.getVideo();

    // Creates the CvSource and MjpegServer [2] and connects them
    //CvSource outputStream = CameraServer.putVideo("Blur", 640, 480);

    // Activate LimeLight65
    //Bluetooth.setDefaultCommand(limelight);


    // Configure the trigger bindings
    configureBindings();

    /* Code Broken due to CommandXboxController Switch
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
                                                                   driverXbox::getBButtonPressed); */

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
   //@SuppressWarnings("unused")
    Command driveFieldOrientedDirectAngle = drivebase.driveCommand(
        () -> -MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND)*x,
        () -> -MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND)*x,
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
       !RobotBase.isSimulation() ? driveFieldOrientedDirectAngle: driveFieldOrientedDirectAngle);
    
    // Start LightOnNote Loop
    //Command lightOnNote = new LightUpOnNote(bluetooth, intake);
    //bluetooth.setDefaultCommand(lightOnNote);
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
    /* Special */
    //driverXbox.start().onTrue(rumble.driver());               // Rumble Driver Controller
    driverXbox.y().onTrue(new SequentialCommandGroup( // Update Shuffleboard
                            new InstantCommand(armCommands::updateConstants),
                            new InstantCommand(wristCommands::updateConstants),
                            new InstantCommand(intakeCommands::updateConstants),
                            new InstantCommand(climberCommands::updateConstants),
                            new InstantCommand(limelightCommands::updateConstants),
                            Commands.runOnce(()-> {x = SmartDashboard.getNumber("X", x);})
    ));


    /* Other Subsystems */
    //driverXbox.x().onTrue(intakeCommands.EjectForward());     // Outake 
    driverXbox.b().whileTrue(visionCommands.AdjustDriveBase(drivebase));
    //driverXbox.a().whileTrue(limelightCommands.AdjustArm(arm));

    /* Drivebase */
    driverXbox.x().onTrue((new InstantCommand(drivebase::zeroGyro))); // Reset Heading
    //driverXbox.b().onTrue(new InstantCommand(drivebase::addFakeVisionReading));
    //driverXbox.a().whileTrue(
    //    Commands.deferredProxy(() -> drivebase.driveToPose(
    //                               new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
     //                         ));
    driverXbox.rightStick().whileTrue(drivebase.aimAtTarget(apriltagCam));
    // driverXbox.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
    //new JoystickButton(driverXbox, 3).whileTrue(new RepeatCommand(new InstantCommand(drivebase::lock, drivebase)));

    //   Operator Controller Binds

    /* Specail */ //skibidi
    //operatorXbox.start().onTrue(rumble.operator());  // Rumble Driver Controller

    /* Main Arm Movement Controls */
    operatorXbox.x().onTrue(arm_control.Stow()); // Arm Positions
    operatorXbox.b().onTrue(arm_control.SpeakerBackwards());//operatorXbox.b().onTrue(arm_control.Intake());
    operatorXbox.a().onTrue(arm_control.Amp());
    operatorXbox.y().onTrue(arm_control.Speaker());

    /* Climber Controls */
    //operatorXbox.leftStick().whileTrue(climberCommands.Extend());
    operatorXbox.start().whileTrue(climberCommands.adjustLeftDown());
    operatorXbox.back().whileTrue(climberCommands.adjustRightDown());
    operatorXbox.leftStick().whileTrue(climberCommands.Extend());
    //operatorXbox.back().whileTrue(climberCommands.Retract()); 


    /* Intake Controls */
    operatorXbox.leftTrigger().whileTrue(autoOP.Intake());
    operatorXbox.rightTrigger().whileTrue(intakeCommands.LaunchNote());//ShootForward());
    operatorXbox.rightStick().whileTrue(intakeCommands.adjustBackward()); //intakeCommands.EjectForward());
    operatorXbox.leftBumper().whileTrue(intakeCommands.EjectBackward());

    /* Direct Arm Movement Controls */ // Removed to streamline operator controller
    driverXbox.leftTrigger().whileTrue(armCommands.MoveForward());
    driverXbox.rightTrigger().whileTrue(armCommands.MoveBackward());
    driverXbox.leftBumper().whileTrue(wristCommands.MoveForward());
    driverXbox.rightBumper().whileTrue(wristCommands.MoveBackward());


    //Commands.startEnd(()->climber.deploy(Constants.ClimberConstants.FullExtensionEncoder), ()->climber.stop(), climber);
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
    drivebase.zeroGyro();
    return autoChooser.getSelected();
  }

  /**
   * Called when Robot Set to Test Mode in Driverstation
   * Makes Robot go "limp" allowing it to be manipulated easily by hang
   */
  public void TestMode() {
    operatorXbox.b().onTrue(arm_control.Intake());
    operatorXbox.x().onTrue(arm_control.Stow());

    operatorXbox.leftStick().whileTrue(climberCommands.adjustBothUp());
    operatorXbox.start().whileTrue(climberCommands.adjustLeftDown());
    operatorXbox.back().whileTrue(climberCommands.adjustRightDown());
  }

  public void setDriveMode()
  {
    // Shake the Driver Controller so we don't repeat Block Party
    //rumble.driver();
    //drivebase.setDefaultCommand();/..
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
