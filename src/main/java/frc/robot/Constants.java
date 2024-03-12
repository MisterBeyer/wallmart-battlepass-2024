// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

import com.pathplanner.lib.util.PIDConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{

  public static final double ROBOT_MASS = (30) * 0.453592; // 32lbs * kg per pound
  public static final Matter CHASSIS    = new Matter(new Translation3d(Units.inchesToMeters(26), Units.inchesToMeters(26),  Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag

  public static final class Pathplanner
  {
    // Translation PID constants
    public static final PIDConstants TranslationPID = new PIDConstants(5, 0, 0);
    public static final PIDConstants RotationPID = new PIDConstants(5, 0, 0);

    // Max module speed, in m/s
    public static final double MaxModuleSpeed = Units.feetToMeters(15.1);
  }

  public static final class Drivebase
  {
    // Drivebase Max Speed
    public static final double MaxSpeed = Units.feetToMeters(15.1); //15.1
    
    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static final class ArmConstants 
  {
    // Amp Limits
    public static int AmpLimit = 40;

    // Rate of change to use when Changing position by ReletiveSoftStop
    public static double ReletiveSoftStopDelta = 1.0; // radians


    // Trapazoidal Profile Constants
    public static double kMaxVelocityRadPerSecond = 75; 
    public static double kMaxAccelerationRadPerSecSquared = 100; //75
    public static double kArmOffsetRads = 0.0;

    // Arm Feedforward Constants
    public static double kSVolts = 1;
    public static double kGVolts = 1;
    public static double kVVoltSecondPerRad = 0.80;
    public static double kAVoltSecondSquaredPerRad = 0.15;

    // PID Values
    public static double P = 1.0;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double Iz = 0.0;
    public static double FF = 0.0;
  }

  public static final class WristConstants 
  {
    // Amp Limits
    public static int AmpLimit = 45;

    // Rate of change to use when Changing position by ReletiveSoftStop
    public static double ReletiveSoftStopDelta = 1.0; // radians
  

    // Trapazoidal Profile Constants
    public static double kMaxVelocityRadPerSecond = 60; 
    public static double kMaxAccelerationRadPerSecSquared = 60;
    public static double kWristOffsetRads = 0.0;

    // Arm Feedforward Constants
    public static double kSVolts = 1;
    public static double kGVolts = 1;
    public static double kVVoltSecondPerRad = 0.80;
    public static double kAVoltSecondSquaredPerRad = 0.15;

    // PID Values
    public static double P = 1.0;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double Iz = 0.0;
    public static double FF = 0.0;

  }

  public static class ClimberConstants
  {
    // Max Amp Limit
    public static final int AmpLimit = 60;

    // Climber Max Speed
    public static double ExtendSpeed = 0.95; //15.1
    public static double RetractSpeed = 0.95; //15.1
    
    // Amp Limits
    public static double ChainReachedAmps = 12;

    // Set Encoder values
    public static double FullExtensionEncoder = 187;
    public static double FullRetractionEncoder = 10;
  }

  public static class OperatorConstants
  {
    // Intake/Outake Speed
    public static double BackOut = 0.95;
    public static double FrontOut = 0.95;
    public static double FrontIn = 0.7;
    public static double BackSlow = 0.6;
    public static double FrontSlow = 0.6;
    public static double BackSlow2 = 0.15;
    public static double FrontSlow2 = 0.15;
    public static double FrontRPM = 3500;
    public static double IntakeNoteAmps = 110;
    public static double NoteLeftFrontAmps = 24.0; //changed from 30
    public static double NoteShotFrontAmps = 20.0; //changed from 30

    // Arm Encoder Positions
    public static double ArmAmpPosition = -40.8;
    public static double ArmSpeakerPosition =  -23; // -25.5; //-35.0\ //24.5
    public static double ArmSpeakerBackwardsPosition = -41.00;
    public static double ArmSpeakerPodiumPosition = 0.0; //TODO: tune this

    public static double WristAmpPosition = -12.5;
    public static double WristSpeakerPosition = -7.3; // -8.3; // -11.65
    public static double WristSpeakerBackwardsPosition = -7.11;
    public static double WristIntakePosition = -13.5;
    public static double WristPodiumPosition = 0.0; //TODO: tune this

    
    // Joystick Deadband
    public static final double LEFT_X_DEADBAND = 0.05;
    public static final double LEFT_Y_DEADBAND = 0.05;
    public static final double RIGHT_X_DEADBAND = 0.6;
    public static final double TURN_CONSTANT = 0.75;
    public static final double IntakeDeadBand = 0.05;

    // Joystick Rumble Stength
    public static final double RumbleStrength = 1.0;
  }
}
