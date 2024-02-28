package frc.robot.commands.Helpers.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;

public class ShootRampUp extends Command {
    private Intake intake;

    private int state;

    public ShootRampUp(Intake module) {
        this.intake = module;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        state = 0;

        intake.setIndividualspeed(0.1,true);
        new WaitCommand(1);
        intake.stop();
    }

    @Override
    public void execute() {
        if (state == 0) {
            if(intake.getFrontRPM() > OperatorConstants.FrontRPM) {
                intake.setSpeed(OperatorConstants.FrontEject, 0);
            }
            else state = 1;
        }
        else if (state == 1) {
            if(true) { //TODO: Find when note has left the intake
                intake.setSpeed(-OperatorConstants.FrontEject, -OperatorConstants.BackEject);
            }
            else state = 2;
        }

    }


    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }

    @Override 
    public boolean isFinished() {
        return state == 2;
    }
}
