package frc.robot.subsystems;

import com.ctre.phoenix.led.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Bluetooth extends SubsystemBase{

  private CANdle candle = new CANdle(40);


  // Animations
  private RainbowAnimation rainbowAnim = new RainbowAnimation(0.25, 0.5, 76);


  private boolean toggle = false;
  private boolean th5 = false;

  // Bofa these bluetooth

  //town hall 5 moment
  //so acurate
  //yes

  public Bluetooth(){
  }

  
  /**
   * Toggles the bluetooth
   * @return Status of blueooth (on/off) as a boolean
   */
  public boolean toggle() {
    toggle = !toggle
    ;if(toggle) setColor(255, 0, 0)
    ;else bluetoothOFF()

    ;return toggle;
  }

  public boolean th5() {
    th5 = !th5
    ;if(th5){ setColor(255, 50, 2)
    ;try {
    wait(2000)
    ;
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }candle.clearAnimation(0)
    ;candle.setLEDs(0, 0, 0)
    ;candle.setLEDs(255, 0, 0)
    ;}
    else bluetoothOFF()

    ;return th5;
  }

  /**
  * Sets color of the whole rgb strip
  * @param r Red brightness as int (0-255)
  * @param g Green brightness as int (0-255)
  * @param b Blue brightness as int (0-255)
  */
  public void setColor(int r, int g, int b) {
    candle.clearAnimation(0);
    candle.setLEDs(r, g, b);
  }


  /** Turns all Leds on with Rainbow Animation */
  public void rainbow(){
    //hackerman works
    candle.animate(rainbowAnim, 0);
  }


  // Level 5 town hall ran out of elixer
  /** Turns All LEDs off */
  public void bluetoothOFF(){
    candle.clearAnimation(0);
    candle.setLEDs(0, 0, 0);
  }
}




