package frc.robot.commands.auto;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Drivebase;
import frc.robot.commands.Helpers.IntakeShoot;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.Timer;



public class autoShoot extends Command{
    private IntakeShoot intake;

    public void initialize() {
    intake.Shoot();
    }
    public void execute(){
    Timer.delay(1);
    }
    public void end(boolean interrupted) 
  {
    intake.Stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }

}
