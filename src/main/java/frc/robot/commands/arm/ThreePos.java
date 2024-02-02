package frc.robot.commands.arm;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class ThreePos extends Command{
    Arm arm;
    Wrist wrist;
    Intake intake;

    public ThreePos(Intake intake, Arm arm, Wrist wrist) {
        this.arm = arm;
        this.wrist = wrist;
        this.intake = intake;
        
        addRequirements(this.arm);
        addRequirements(this.wrist);
        addRequirements(this.intake);  
    }

    /** Brings Intake Out */
    public void IntakeOut() {
        arm.Down();
        wrist.SetWristPos(true);
        intake.setSpeed(-OperatorConstants.IntakeSpeedTop, -OperatorConstants.IntakeSpeedBottom);
    }

    /** Brings Intake In */
    public void IntakeStow() {
        intake.setSpeed(0, 0);
        arm.Down();
        wrist.SetWristPos(false);
    }

    /** Brings Arm Up */
    public void ArmUp() {
        intake.setSpeed(0,0);
        arm.Up();

    }
    
    /** Shoots note from intake */
    public void Shoot() {
        if(arm.getPosition() != 1 ) intake.setSpeed(OperatorConstants.IntakeSpeedTop, OperatorConstants.IntakeSpeedBottom);
    }

}
