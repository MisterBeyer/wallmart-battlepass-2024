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
    public static final PIDConstants TranslationPID = new PIDConstants(1, 0, 0.02);
    

    // Max module speed, in m/s
    public static final double MaxModuleSpeed = Units.feetToMeters(2);
  }

  public static final class Drivebase
  {
    // Drivebase Max Speed
    public static final double MaxSpeed = Units.feetToMeters(1);
    
    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static class OperatorConstants
  {
    // Intake Speed
    public static double IntakeSpeedTop = 0.1; 
    public static double IntakeSpeedBottom = 0.1;

    // Arm/Wrist Motor Speed and amp limits
    public static double ArmMotorSpeed = 0.1;
    public static double WristMotorSpeed = 0.1;

    public static double ArmAmpLimit = 2;
    public static double WristAmpLimit = 2;
    
    // Joystick Deadband
    public static final double LEFT_X_DEADBAND = 0.05;
    public static final double LEFT_Y_DEADBAND = 0.05;
    public static final double RIGHT_X_DEADBAND = 0.05;
    public static final double TURN_CONSTANT = 0.75;
    public static final double IntakeDeadBand = 0.5;

  }
}
