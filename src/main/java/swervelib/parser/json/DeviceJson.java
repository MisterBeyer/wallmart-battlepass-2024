package swervelib.parser.json;

import com.revrobotics.SparkRelativeEncoder.Type;
import edu.wpi.first.wpilibj.DriverStation;
import swervelib.encoders.AnalogAbsoluteEncoderSwerve;
import swervelib.encoders.CANCoderSwerve;
import swervelib.encoders.SparkMaxAnalogEncoderSwerve;
import swervelib.encoders.SparkMaxEncoderSwerve;
import swervelib.encoders.SwerveAbsoluteEncoder;
import swervelib.imu.Pigeon2Swerve;
import swervelib.imu.SwerveIMU;
import swervelib.motors.SparkFlexSwerve;
import swervelib.motors.SparkMaxSwerve;
import swervelib.motors.SwerveMotor;

/**
 * Device JSON parsed class. Used to access the JSON data.
 */
public class DeviceJson
{

  /**
   * The device type, e.g. pigeon/pigeon2/sparkmax/talonfx/navx
   */
  public String type;
  /**
   * The CAN ID or pin ID of the device.
   */
  public int    id;
  /**
   * The CAN bus name which the device resides on if using CAN.
   */
  public String canbus = "";

  /**
   * Create a {@link SwerveAbsoluteEncoder} from the current configuration.
   *
   * @param motor {@link SwerveMotor} of which attached encoders will be created from, only used when the type is
   *              "attached" or "canandencoder".
   * @return {@link SwerveAbsoluteEncoder} given.
   */
  public SwerveAbsoluteEncoder createEncoder(SwerveMotor motor)
  {
    if (id > 40)
    {
      DriverStation.reportWarning("CAN IDs greater than 40 can cause undefined behaviour, please use a CAN ID below 40!",
                                  false);
    }
    switch (type)
    {
      case "none":
        return null;
      case "integrated":
      case "attached":
        return new SparkMaxEncoderSwerve(motor, 1);
      case "sparkmax_analog":
        return new SparkMaxAnalogEncoderSwerve(motor);
      case "canandcoder":
        return new SparkMaxEncoderSwerve(motor, 360);
      case "ma3":
      case "ctre_mag":
      case "rev_hex":
      case "throughbore":
      case "am_mag":
      case "thrifty":
      case "analog":
        return new AnalogAbsoluteEncoderSwerve(id);
      case "cancoder":
        return new CANCoderSwerve(id, canbus != null ? canbus : "");
      default:
        throw new RuntimeException(type + " is not a recognized absolute encoder type.");
    }
  }

  /**
   * Create a {@link SwerveIMU} from the given configuration.
   *
   * @return {@link SwerveIMU} given.
   */
  public SwerveIMU createIMU()
  {
    if (id > 40)
    {
      DriverStation.reportWarning("CAN IDs greater than 40 can cause undefined behaviour, please use a CAN ID below 40!",
                                  false);
    }
    switch (type)
    {
      case "pigeon2":
        return new Pigeon2Swerve(id, canbus != null ? canbus : "");
      default:
        throw new RuntimeException(type + " is not a recognized imu/gyroscope type.");
    }
  }

  /**
   * Create a {@link SwerveMotor} from the given configuration.
   *
   * @param isDriveMotor If the motor being generated is a drive motor.
   * @return {@link SwerveMotor} given.
   */
  public SwerveMotor createMotor(boolean isDriveMotor)
  {
    if (id > 40)
    {
      DriverStation.reportWarning("CAN IDs greater than 40 can cause undefined behaviour, please use a CAN ID below 40!",
                                  false);
    }
    switch (type)
    {
      case "neo":
      case "sparkmax":
        return new SparkMaxSwerve(id, isDriveMotor);
      case "sparkflex":
        return new SparkFlexSwerve(id, isDriveMotor);
      default:
        throw new RuntimeException(type + " is not a recognized motor type.");
    }
  }
}
