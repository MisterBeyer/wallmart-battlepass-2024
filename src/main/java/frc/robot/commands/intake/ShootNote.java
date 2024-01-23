package frc.robot.commands.intake;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.Intake;

//dont unplug the ethernet
public class ShootNote extends Command {
    private Intake intake;
    private double speed;

    /** 
     * Shoots Note out of Intake
     * {@param module} {@link Intake} to shoot out of
     * {@param speed} of the intake from provided as a {@link Double} between 0-1
     */
    public ShootNote(Intake module, double speed) {
        this.intake = module;
        this.speed = speed; 
        addRequirements(this.intake);
    }
    
      // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        intake.setspeed(speed);
    }
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.stop();
        }
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
        // Don't know any end conditions to add here, hoping RobotContainer just interupts it
    }
}