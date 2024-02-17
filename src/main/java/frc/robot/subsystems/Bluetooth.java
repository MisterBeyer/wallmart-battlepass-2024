package frc.robot.subsystems;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.StrobeAnimation;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Bluetooth extends SubsystemBase{
    private static boolean toggle = false;
    // Bofa these bluetooth

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




