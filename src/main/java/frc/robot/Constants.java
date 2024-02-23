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
    public static final double MaxSpeed = Units.feetToMeters(15.1);
    
    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static final class ArmConstants 
  {
    // Amp Limits
    public static double AmpLimit = 10;

    // Rate of change to use when Changing position by ReletiveSoftStop
    public static double ReletiveSoftStopDelta = 2;

    // Trapazoidal Profile Constants
    public static double kMaxVelocityRadPerSecond = 0.10; 
    public static double kMaxAccelerationRadPerSecSquared = 0.10;
    public static double kArmOffsetRads = 0.10;

    // Arm Feedforward Constants
    public static double kSVolts = 0.10;
    public static double kGVolts = 0.10;
    public static double kVVoltSecondPerRad = 0.10;
    public static double kAVoltSecondSquaredPerRad = 0.10;

    // PID Values
    public static double P = 0.15;
    public static double I = 0.0;
    public static double D = 0.0;
    public static double Iz = 0.0;
    public static double FF = 0.0;
  }

  public static final class WristConstants 
  {
    // Amp Limits
    public static double AmpLimit = 10;

    // Trapazoidal Profile Constants
    public static double kMaxVelocityRadPerSecond = 0.10; 
    public static double kMaxAccelerationRadPerSecSquared = 0.10;
    public static double kArmOffsetRads = 0.10;

    // Arm Feedforward Constants
    public static double kSVolts = 0.10;
    public static double kGVolts = 0.10;
    public static double kVVoltSecondPerRad = 0.10;
    public static double kAVoltSecondSquaredPerRad = 0.10;

  }

  public static class ClimberConstants
  {
    // Climber Max Speed
    public static double MaxSpeed = Units.feetToMeters(.1); //15.1
    
    // Amp Limits
    public static double ChainReachedAmps = 12;
    public static double RobotReachedAmps = 12;

    // Set Encoder values
    public static double FullExtensionEncoder = 360;
  }

  public static class OperatorConstants
  {
    // Intake/Outake Speed
    public static double IntakeSpeed = 0.1; 
    public static double BackOut = 0.8;
    public static double FrontOut = 0.8;
    public static double FrontRPM = 2500;
    public static int BackRPM = 0;

    // Arm/Wrist Motor Speed and amp limits
    public static double WristMotorSpeed = 0.1;

    
    // Joystick Deadband
    public static final double LEFT_X_DEADBAND = 0.05;
    public static final double LEFT_Y_DEADBAND = 0.05;
    public static final double RIGHT_X_DEADBAND = 0.05;
    public static final double TURN_CONSTANT = 0.75;
    public static final double IntakeDeadBand = 0.5;

  }
}
