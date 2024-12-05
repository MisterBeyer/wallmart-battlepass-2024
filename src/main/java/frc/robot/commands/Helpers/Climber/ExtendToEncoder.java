package frc.robot.commands.Helpers.Climber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class ExtendToEncoder extends Command {
    private Climber climber;

    public ExtendToEncoder(Climber module) {
        this.climber = module;
        addRequirements(climber);

        // Shuffleboard!
        SmartDashboard.putNumber("Climber/Full Extention Encoder Goal", ClimberConstants.FullExtensionEncoder);
    }

    /** Updates Motor Speeds and limits from shuffleboard */
    public static void updateConstants() {
        ClimberConstants.FullExtensionEncoder = SmartDashboard.getNumber("Climber/Full Extention Encoder Goal", ClimberConstants.FullExtensionEncoder);

        System.out.println("[ClimberCommands/ExtendToEncoder] Shuffleboard Updated");
    } 
    

    @Override
    public void initialize() {
        System.out.println("[ClimberCommands/ExtendToEncoder] Extending Climber");
        climber.setBoth(ClimberConstants.ExtendSpeed);
    }

    @Override
    public void execute() {}


    @Override
    public void end(boolean interrupted) {
        climber.stop();
        System.out.println("[ClimberCommands/ExtendToEncoder] Climber Extended");
    }

    @Override 
    public boolean isFinished() {
        return (climber.getRightEncoder() > ClimberConstants.FullExtensionEncoder);
    }
}
