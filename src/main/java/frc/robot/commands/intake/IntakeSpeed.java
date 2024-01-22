package frc.robot.commands.intake;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.intake;

//dont unplug the ethernet
public class IntakeSpeed extends Command {
    private intake intake;
    private double speed;

    /** 
     * {@param module} {@link intake} to set speed for.
     * {@param speed} of the intake from provided as a {@link Double} between 0-1
     */
    public IntakeSpeed(intake module, double speed) {
        this.speed = speed; 
        this.intake = module;
        addRequirements(this.intake);
    }

      // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        intake.setspeed(speed);
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