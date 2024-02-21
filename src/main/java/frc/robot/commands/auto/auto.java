package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class auto extends SequentialCommandGroup {
    private autoShoot autoshoot;
    public auto(){
        addCommands(autoshoot); 
    }
}
