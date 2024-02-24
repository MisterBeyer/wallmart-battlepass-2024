package frc.robot.subsystems;


import com.ctre.phoenix.led.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Bluetooth extends SubsystemBase{
    private static boolean toggle = false;
    // Bofa these bluetooth

    private static boolean th5 = false;
    //town hall 5 moment

    public Bluetooth(){
      
        }
        static CANdle candle = new CANdle(40);
        static RainbowAnimation rainbowAnim = new RainbowAnimation(0.25, 0.5, 76);
        static RainbowAnimation itsturningmeoff = new RainbowAnimation(0, 0, 0);


    /**
     * Toggles the bluetooth
     * @return Status of blueooth (on/off) as a boolean
     */
    public boolean toogle() {
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

      public void bluetoothON(){
        //hackerman works
        candle.animate(rainbowAnim, 0);
      }

      // Level 5 town hall ran out of elixer

      public static void bluetoothOFF(){
        candle.clearAnimation(0);
        candle.setLEDs(0, 0, 0);
      }
    }




