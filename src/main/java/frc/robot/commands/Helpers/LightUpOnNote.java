package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Bluetooth;
import frc.robot.subsystems.Intake;

public class LightUpOnNote extends Command {
    Bluetooth bluetooth;
    Intake intake;

    public LightUpOnNote(Bluetooth bluetooth, Intake intake) {
        this.bluetooth = bluetooth;
        this.intake = intake;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(intake.getNoteStatus()) bluetooth.setColor(255, 0, 0);
        else bluetooth.bluetoothOFF();

    }


    @Override
    public void end(boolean interrupted) {
        bluetooth.bluetoothOFF();
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
