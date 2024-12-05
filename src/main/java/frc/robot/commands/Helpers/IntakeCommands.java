package frc.robot.commands.Helpers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Helpers.Intake.*;
import frc.robot.subsystems.Intake;

//dont unplug the ethernet
public class IntakeCommands{
    
    private Intake intake;


    /** Helper Commands For Intake
     *  @param module module to use as intake
     */
    public IntakeCommands(Intake module) {
        // Assign Control
        this.intake = module;

        // ShuffleBoard!
        SmartDashboard.putNumber("Intake/Front Motor Speed", OperatorConstants.FrontOut);
        SmartDashboard.putNumber("Intake/Rear Motor Speed", OperatorConstants.BackOut);
        SmartDashboard.putNumber("Operator/Shoot [Front] Goal RPM", OperatorConstants.FrontRPM); 
        SmartDashboard.putNumber("Operator/Intake [Back] Goal RPM", OperatorConstants.IntakeNoteAmps);
    }



    /** Updates Motor Speeds and limits from shuffleboard */
    public void updateConstants() {
        // Motor Speed Posistions
        OperatorConstants.FrontOut = SmartDashboard.getNumber("Intake/Front Motor Speed", OperatorConstants.FrontOut);
        OperatorConstants.BackOut  = SmartDashboard.getNumber("Intake/Rear Motor Speed", OperatorConstants.BackOut);

        // RPM Goals
        OperatorConstants.FrontRPM = SmartDashboard.getNumber("Operator/Shoot [Front] Goal RPM", OperatorConstants.FrontRPM); 
        OperatorConstants.IntakeNoteAmps = SmartDashboard.getNumber("Operator/Intake [Back] Goal RPM", OperatorConstants.IntakeNoteAmps); 

        System.out.println("[IntakeCommands] Shuffleboard Updated");

        // Update Constants of Subsystems and Helpers
        IntakeNote.updateConstants();
        ShootPullBack.updateConstants();
        ShootNote.updateConstants();
        intake.updateConstants();



        /*⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡠⠤⠤⠤⠄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠤⠖⠒⣂⣉⣩⠥⠬⢤⣉⣭⣌⡉⠒⠦⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠔⢋⣉⣩⣤⡴⣖⢲⠶⣦⣄⡉⠑⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⢒⣭⠷⡚⣭⢩⣴⣣⣖⣶⣹⣦⣟⣼⡻⣿⢷⣦⣄⡉⠶⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡤⢊⡴⢞⢯⡿⣵⣻⣼⣏⣽⣳⢳⣎⢿⡷⣤⡙⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⢋⡴⢋⡔⣣⣽⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣾⣶⣣⢟⣦⡈⢣⡀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠔⣡⣖⣻⣌⣷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣾⣧⣿⣗⡻⢦⡝⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡜⣰⢏⡞⣵⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣮⡽⣄⠳⡄⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⣤⢃⡼⣵⡿⣹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣽⡯⣿⣦⠙⣄⠀⠀⠀⠀⠀⠀⣀⡀⠤⠒⠒⣙⣉⣉⣍⣉⣉⣽⣛⣙⡓⠒⠒⠤⢀⣀⠀⠀⠀⠀⠀⠀⡼⢱⢏⡾⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣟⣆⢹⡆⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⡼⣠⢏⣾⢟⡾⣿⣿⣿⣿⣿⣿⣿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣿⣧⠈⣦⣀⣠⠔⠒⣉⣡⣴⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣦⣌⣑⠒⠤⣀⡀⢰⠃⣾⣫⣿⣿⣿⡿⣿⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡼⡀⢹⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⡜⣡⢧⡾⢣⢟⡵⣻⢽⣻⢿⣻⣾⡽⣿⣿⣿⣻⣿⣿⣿⣿⣿⣿⣿⣿⣷⣿⣷⣀⣠⣴⣾⣿⣿⡿⢿⠿⢿⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⣟⣛⡛⡟⠿⣿⣷⣦⣌⡉⠐⣧⣿⣿⣿⣟⣿⣽⣿⣯⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⢸⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⢸⢱⢣⡞⢡⢚⣼⡻⣵⢣⣏⢯⣳⢯⣿⣻⣟⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⣍⠲⢥⢎⡵⢊⣧⣙⢦⢻⠽⣿⣿⣿⣿⣟⠿⣼⡹⣶⣡⢷⡸⣱⢢⡙⣿⣿⣿⣾⣿⣿⣿⣿⣿⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⢸⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⡇⡿⠀⡘⠬⣝⢶⡛⣼⢳⢮⡻⣜⡧⣷⢻⡾⣽⣻⣿⡿⣿⢿⣿⣿⣿⣿⣿⣿⣿⡟⡱⣚⢬⣛⢮⢞⡼⢳⢶⢩⡞⡽⣺⢥⣛⢿⣱⢞⡿⣣⣟⡶⣝⡶⣳⢥⢧⣹⢲⡻⣿⣿⣿⣿⣿⣯⣿⣿⣿⣿⣿⣻⣯⣿⣿⣿⢿⣿⣿⣿⣳⣿⣿⡟⣿⣽⣿⣿⣿⣿⠇⢸⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⢐⡇⣿⠀⢍⡺⢭⡞⣝⢮⣛⡾⣽⣹⢞⣭⢷⣫⢗⢯⢾⡽⢯⡿⣽⣿⣿⣿⣿⣿⠏⣿⡔⠩⠒⡍⠚⢮⡙⢣⠎⢓⠾⣱⢏⣾⣙⣮⣝⣾⣽⣳⣽⣾⣧⢻⡵⢫⢎⡳⢫⣽⣿⣿⣿⣿⣿⣿⣽⣾⣟⣷⣟⣿⣿⣿⣿⣽⣿⡿⣏⣾⣟⣿⢯⣝⣾⣟⣼⣿⣿⣿⠀⡿⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⢸⠀⣿⡆⢎⣝⣣⢟⣼⣳⣏⢾⡵⣫⢿⣜⣧⣛⢾⡹⢾⣹⢯⣿⣿⣿⣿⣿⣿⢇⡂⢼⣷⠀⢣⡐⣉⠒⡌⠃⠞⡩⠚⡍⢯⡙⢯⡹⢫⠯⡝⣯⢳⠿⣜⢧⣛⡟⣬⢓⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⣯⣿⣿⣿⣿⢿⣷⣻⣽⢿⣽⡿⣛⣮⣿⣳⣾⣿⣿⣿⠇⣸⠃⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠘⡆⣿⣿⣾⡲⢭⢾⡱⣏⣾⡻⣼⢯⡟⣼⢲⡝⡮⣝⡻⣜⣯⣿⣿⣿⣿⣿⣿⢘⣾⠟⠁⠈⡀⠰⠖⠛⠛⠛⠷⣶⢷⣼⢆⡟⢢⣍⣣⣯⣱⣎⣯⣽⡾⠿⡞⢾⡰⢫⡜⣻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣾⡷⣯⣟⣾⣵⣻⣿⣾⣿⣿⣿⣿⡟⢠⠇⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⣇⢻⣿⣧⣿⢏⣾⡱⣟⢮⢷⣹⢮⡝⣬⠳⣎⡵⣫⢗⡿⣾⣿⣿⣿⣿⣿⣏⣾⠏⠀⠀⢀⣀⣄⣦⣌⡽⣙⢣⢓⡎⠦⢓⣌⠣⠞⠫⢅⠫⡜⣱⣦⣿⣷⣿⣶⣭⣧⡜⡰⢣⢽⣿⣿⣿⣿⣿⣿⣿⣽⢾⣽⣾⣿⣿⣿⣿⣿⣿⣿⣽⣿⣿⣿⣿⣿⣿⡟⢡⠎⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠘⡮⢿⣿⣿⣻⣶⡽⢞⣯⡗⣯⢾⣍⣳⢟⣬⡳⣭⣻⣽⣻⣿⣿⣿⣿⣿⡧⣿⡀⠰⠾⢟⡛⣛⠛⡿⠿⣿⣷⣾⣼⣶⣧⢸⣎⣱⢉⣾⣷⣿⣿⠟⣫⣭⣙⣻⣛⣿⢿⡿⣳⣾⣿⣿⣿⣿⣿⣿⣿⡾⣿⢿⣯⣿⣞⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠋⣠⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠘⢎⠻⣿⣿⣿⣿⣟⣶⣻⣽⣿⣾⣯⣿⣾⣿⣷⣿⣷⣿⣿⣿⣿⣿⣿⣷⠘⣿⡧⣽⠶⠶⢇⠩⣴⣧⣶⡌⣹⠛⡟⢟⣾⠇⣿⣿⡿⢟⣫⣵⣾⣟⣟⣻⣛⡝⣫⢇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣽⣯⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⢋⡤⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠈⠣⡙⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠛⢡⣴⣿⡿⠿⣿⡿⣿⣶⡿⢽⣿⡞⠶⠟⢨⣿⣷⣾⣿⡿⢿⣿⢿⣿⣿⡟⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠛⠻⠿⠿⣿⣿⣿⠿⠿⠛⣛⣭⠦⠒⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠒⠬⣉⡙⠻⠿⢿⣿⣿⣿⣿⣿⡿⠿⠟⠋⡉⣿⣿⣿⣿⣿⣿⣿⡆⠂⠼⠿⣷⣤⣙⣿⣿⣿⠷⠟⡛⡐⣿⡇⠀⣿⣿⣿⡻⢶⣤⣟⣿⣛⣯⣴⠿⣿⡿⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⣼⠗⠚⠓⠒⠒⠒⠶⠋⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠑⠒⠢⠤⠄⠓⠤⠔⠓⠚⠛⣽⠃⣿⣿⣿⣿⣿⣿⣿⣿⣦⡁⠂⠄⡈⠁⠄⠂⠄⡈⠰⢠⠑⡸⣧⠈⣽⣿⣿⣿⣬⢻⠼⣭⡙⠲⣌⡳⣇⢿⣱⣾⣿⣿⣿⣿⣿⣿⣿⣿⡀⢻⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠔⣡⣾⠟⠋⠈⢩⠛⡛⢿⣿⣿⣿⣦⡔⠀⡈⠄⢂⠡⠘⠠⢃⡘⣰⡿⣐⢾⣿⣿⡿⢭⣋⠗⣦⢙⢣⡍⢷⡩⣾⣿⡿⣿⢿⣿⣻⢟⣿⣿⣿⣷⡀⢧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠎⣼⠋⠀⢀⠑⠀⠂⠁⠈⠀⠀⠱⠄⠤⠀⡐⠐⡈⠄⡘⢈⠁⠢⠐⣿⡇⢈⢎⣿⣿⡝⣧⢎⡽⢠⡋⠖⣌⢣⢷⡹⢶⡹⣎⡷⣏⡾⣽⣻⣿⣿⣿⣿⡌⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠇⣾⠃⠀⡰⠀⠈⠀⠐⠀⠀⠀⠀⠀⠌⡀⢃⠀⢂⠐⡠⠐⠠⠈⠄⢁⣻⡇⠈⡜⣿⣟⡞⣲⢭⡒⢧⣙⡚⣌⢧⡚⣭⢳⢯⡝⣾⢽⣹⣳⢿⣿⣿⣿⣿⣿⡌⢧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡟⢸⡟⠀⠤⢡⠀⠂⠡⠐⡀⠀⠀⠀⠌⠠⠐⠠⠈⠄⠂⠄⠡⠘⣀⠢⣰⡿⢀⠠⠜⣿⣿⣼⢣⣞⡹⣖⡱⢜⡰⢦⡹⣜⢣⡟⣼⢣⡟⡷⢯⡿⣯⣿⣿⣿⣿⣿⠸⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡇⣹⠀⠠⢃⠆⡐⠠⢁⠂⡐⢀⠀⡈⠄⢁⠂⠡⠐⡀⠌⠀⠁⠂⣄⣻⣏⣶⣾⡶⣿⣯⣿⣿⣻⣶⡹⢲⡙⡎⢖⡢⡝⣎⠳⡞⡥⣟⠾⣽⣫⢟⡽⣿⣿⣿⣿⣿⡇⣅⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣷⡘⣧⠀⡉⠒⡌⠐⡠⠌⡐⠄⠂⡔⢈⠠⢀⠂⢀⠀⢀⣤⣶⣿⡟⢯⡉⠐⠠⠐⣠⢲⡭⣿⣿⣿⣿⣷⣽⣘⠣⡵⡑⣎⢳⡹⣜⢧⡟⣧⣛⣮⢽⣿⣿⣿⣿⣿⡅⢻⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡏⣸⡿⣷⡀⠱⡈⢁⠐⠰⡈⢌⠡⡐⡀⠂⠄⡀⠀⣰⣿⣿⣿⡿⣾⣦⣕⣨⣰⣱⣮⣵⣾⣿⣿⣿⣿⣿⣿⣿⣷⡱⡹⣌⢧⠳⣭⢞⡽⢶⣏⣞⣳⣾⣿⣿⣿⣿⣿⡘⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡞⣠⡿⠀⠘⠿⡷⣌⢤⠈⢂⡁⢎⡰⢁⠄⡁⠂⠀⣼⡿⣿⣿⣿⣿⣷⣿⣯⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⣷⡳⣜⡎⣿⣼⣫⣞⣳⠾⣜⣷⣿⣿⣿⣿⣿⣿⣷⡹⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡞⣴⡟⠁⠀⠐⡰⢢⢍⡖⣫⠆⡜⠢⠜⠂⣾⠃⢀⣾⠏⢠⠉⠿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⢫⢑⠲⣙⢿⡼⣹⢾⣷⣟⡾⣽⣿⣿⣿⡟⣯⣽⡹⣿⣿⣿⣷⢹⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠏⣰⠏⢀⠀⢂⠡⣈⠑⣎⠹⣖⢮⡐⠁⠂⣰⡏⢀⣾⠋⠄⠡⠈⠔⠂⠤⡉⡙⠛⢛⠻⢿⢛⠛⡉⢍⡩⢐⠢⡉⢆⠨⡓⣌⡞⢿⣧⣻⣿⣯⢿⣿⢼⡻⣜⡟⣶⢳⢯⢻⣽⣿⣿⣯⠹⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡰⢉⣾⠃⠀⢢⡘⢄⠒⠤⡉⠤⡉⠼⠈⢿⣄⢠⡿⠁⠠⢀⠒⡈⠄⡉⢢⢉⡐⢡⢀⣁⣂⡑⢎⣆⣶⣶⣤⣥⣃⢂⠱⠈⢆⠱⢢⡙⣮⢳⣹⣿⣿⣿⣛⢮⡳⢯⡝⣮⢟⣮⢳⡽⢿⣿⣿⣧⡹⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡜⣡⡿⠁⠀⡸⢠⠓⡌⣘⠢⡑⢠⢁⠊⠁⠀⢹⣾⠃⠠⠁⢌⠒⢠⡑⣘⣴⣦⡿⢟⡛⢭⠻⣿⢿⡟⢫⠜⣛⡛⣛⢛⣷⣷⣬⣡⢣⡝⢦⣋⢾⣿⣿⢧⣟⢮⡝⣧⢻⡜⣯⢞⡧⣏⢻⣿⣿⣿⣷⡸⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⢰⣿⠁⠀⠠⠑⡀⢣⠜⠤⠓⣌⠂⢆⢂⠀⠀⠀⢻⡇⠈⢌⠠⣌⣦⣷⡟⢯⣖⣿⣺⣝⣫⢳⡜⡲⣍⣷⣾⣷⣿⣿⣿⣿⣿⣿⣿⣿⣾⣦⡝⣾⣿⣿⣿⣭⣳⢻⣜⢧⠿⣜⡯⢶⣩⢷⣟⣿⣯⣿⣷⠸⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⡌⢿⡀⠀⠀⠀⠀⠢⢌⢣⠙⡤⡙⢤⠊⡄⠀⠀⢸⡇⠈⠄⡚⠿⣿⡛⠛⠋⠉⠉⡉⠭⣙⡙⠿⣿⣾⢿⢟⡟⣫⠽⡹⠎⡱⢊⣭⣿⣟⡳⣼⣿⣿⣿⣿⣯⡗⣯⢞⣭⣛⡾⣽⣷⣻⣟⣿⣿⣿⣿⣿⢀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢆⡙⢷⣤⡀⠀⠀⠀⠢⠙⢤⠙⣢⠱⡈⠄⠀⠈⣷⠀⢈⠐⠤⠉⠻⢷⣤⡁⠆⠱⢁⠒⢨⠙⣌⠣⢍⡚⡜⢡⠂⠐⣠⣴⡿⣻⠱⣎⠵⣿⣿⣿⣿⣿⣿⣛⣼⡳⣾⣽⣽⣿⣿⣟⣿⣿⣿⣿⠟⣁⠞⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠢⣬⠙⢶⣄⡀⠀⠈⠠⠙⣄⠣⡑⢂⠀⠀⣿⡄⠠⢈⠀⠂⠁⢀⠉⠻⠶⡁⠆⣉⠤⡑⢌⠚⢤⣑⣌⣦⣾⠾⣟⡽⣶⡷⡹⢼⣹⣿⣿⣿⣿⢿⣷⢿⣞⣿⣿⣿⣿⣿⣿⣿⡿⠟⢋⡴⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠑⠲⣈⠛⢶⣄⡀⠁⠄⡓⢌⠢⠄⠀⢸⣧⠀⠄⠂⠀⠄⢀⡀⠂⠐⢈⠰⠙⣿⢿⣿⣿⣿⣿⣿⣹⣋⣵⣯⣾⠟⣡⢛⣼⣿⣿⣿⣿⣿⣿⣿⣻⣾⣿⣿⣿⡿⠟⢋⣡⡖⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠓⢤⣈⠻⢦⣄⡀⢋⠄⡐⠀⣿⡿⠆⠐⡈⠠⠀⠈⠻⠖⠀⣀⣠⣰⣬⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⢁⠲⡥⣟⣾⣿⣿⣿⣿⣿⣿⣳⣿⣿⣿⣟⣡⣠⣠⣀⣉⣉⣐⣒⡒⣒⠢⠤⠤⠤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣴⠿⢇⣀⣩⣿⣦⣥⡐⢸⡿⠁⢀⠂⡔⠁⠌⡀⠀⠀⠂⠘⠋⠉⢉⠉⠩⠍⡉⠉⠽⣻⡛⠇⡰⣉⢾⣱⢻⣿⣿⣿⣿⣿⣿⣿⡿⣿⣿⣿⠿⢟⣫⢟⣻⣾⢟⣽⣫⣟⣯⣙⡷⣶⣦⣤⣍⣉⠒⠤⢄⡀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⢴⠖⣏⣩⡭⡵⠶⠖⢾⣿⢿⡻⢿⣿⣿⣿⣿⠃⠌⢠⠊⡐⢌⠠⠐⡀⠁⠠⠁⠠⠐⡀⠂⡁⠢⢄⠡⠂⣃⠱⢈⠴⣫⢞⣭⣟⣻⣿⣿⣿⣿⢻⡑⢣⢷⣿⠃⡽⢋⢱⡏⣮⢟⣽⠞⡿⣹⢶⣻⠿⣏⣉⣍⢛⠿⣿⣶⣤⣉⡉⠒⠤⣀⡀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡠⠔⣊⣹⠷⠒⠚⠉⠁⢀⠠⣀⡐⢌⢂⡙⢿⣟⢾⣿⠆⣽⡏⠀⠐⢂⠱⣈⠂⡅⠣⢄⠡⣀⠡⢂⠡⠄⡡⢂⠅⠎⡰⠡⢄⡊⡔⡸⡱⢞⣮⣷⣿⣿⣿⣿⣿⠂⢌⣱⣿⡏⠰⢀⢂⠣⡜⡰⢊⢦⣛⠶⣩⢏⡵⣫⡟⡽⣎⢾⡱⣆⢯⣛⢿⣿⢿⣶⣤⣉⠒⠤⡀
⠀⠀⠀⠀⠀⠀⣀⠴⣂⡯⠗⠋⠉⠁⢀⠠⣄⠆⠲⡘⢆⠣⡱⠜⡬⠒⡌⠦⡙⢎⣽⡆⣿⡇⠀⠈⠄⡃⢤⠘⠄⠣⢌⠒⡠⠱⢌⡘⢂⠱⢨⠌⡒⡅⢋⠦⡱⣍⢳⣙⣧⣟⣷⣿⣿⣿⣿⣿⠀⢂⣾⡿⢁⠃⠌⡀⠓⣄⢣⣙⢦⡻⡜⡵⣎⡷⣳⣝⡳⣽⢺⣝⣮⢯⡽⣾⢿⣿⣿⣻⢿⣿⣶⣮
⠀⠀⢀⡠⢔⡭⠞⠋⠁⢀⣀⠰⠒⡙⠊⠑⣀⢊⡑⣈⠤⢡⠔⡢⢔⡡⢚⠴⣉⠖⡬⢷⢸⣷⠀⠀⠀⡓⢦⡉⣎⠱⢊⡔⠡⢆⢣⡘⡌⢣⠥⣊⢕⢊⢧⠞⡱⣜⣣⢝⣶⣿⢿⣿⣿⣿⣿⡟⠀⠜⡘⠁⡄⢨⠐⡌⡱⢰⣦⡟⣦⢻⡜⣳⡝⡾⡵⣎⢷⡭⣗⣞⢾⡭⢿⡽⣯⣿⣿⣿⣿⡿⣿⣿
⠤⢞⡹⠞⠉⠀⠀⡀⠌⡤⠄⠦⡑⡴⣉⠳⡌⢦⠱⣌⠲⣡⠞⡱⢊⡔⡫⢜⡡⢏⠴⣩⠆⢿⣧⡀⠀⢹⣲⠹⣆⢯⢣⢼⡑⣎⢦⢱⡈⢇⢮⣱⢾⣯⢿⡿⣕⡾⣲⣿⣿⣟⣿⣿⣿⣿⡿⢁⡈⠐⣀⠢⠐⡅⢎⣰⣱⢏⡳⣌⠷⣙⡾⣱⠞⡷⣽⣹⡞⣷⣹⡞⣧⣟⣯⣿⣷⣿⣿⣿⣿⣿⣿⣿
⠴⠋⠀⢀⠠⡘⢤⡱⢋⡔⣋⢧⢳⡘⡥⢓⡜⢢⠓⡤⢓⢦⡙⠴⣋⠴⣙⢬⠓⣭⢚⡴⣛⢬⣻⣿⣦⣤⣯⣻⣜⣣⠟⣮⡝⣎⣞⣶⣽⣿⣾⣽⣿⣞⣿⣽⣯⣿⣿⣿⣻⣿⣿⣿⣿⠟⣁⠂⠤⣁⡆⠱⠡⣈⣶⠾⣍⢏⠶⣩⠞⣡⠷⡝⣯⢳⡽⣲⢹⢶⣭⣟⠷⣞⢷⣻⡼⣿⣾⣟⣿⣿⣿⣿
⠀⡄⡘⣄⢣⠝⣦⣙⢧⢺⣭⢲⣭⣷⢣⠏⡜⣡⢋⠶⣉⢦⡙⠼⡰⢣⢍⠶⣉⠶⣩⠖⡽⣎⢷⡫⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⢛⣦⡾⢢⢙⡰⠜⣀⢣⣵⣾⠟⣶⣚⣮⢷⣝⢮⡽⣹⣼⣜⣧⣶⣧⢿⣼⣶⣿⣯⣿⣿⣾⣿⣿⣿⣿⣿⣿⣿⣿
⡒⢬⠱⣎⢇⡻⢴⣙⣎⡳⣬⣷⣟⠮⡥⣋⡔⢣⢍⠲⣉⠖⣌⡳⢍⢧⡚⣌⢧⠛⡴⣻⢵⣫⢞⣭⣓⣦⣙⢎⡛⣉⠯⡭⣉⢧⡙⢆⠳⣌⢚⠭⣹⣿⡿⢭⣛⠽⣩⢋⡝⣤⣷⣿⠫⡐⢅⠢⣁⣣⡾⣟⣳⣭⣿⣽⣟⣾⣿⡿⣿⠿⣟⣟⡻⣏⣟⣻⣛⣟⣻⣟⣿⣻⣟⡿⣿⢿⡿⣿⣿⣿⣿⣿
⣝⢪⡓⣮⢚⡵⣣⣞⣶⡿⣽⣾⣍⣣⣵⣢⣝⣮⣜⣵⣬⣺⣴⣙⣞⣦⣻⣜⣮⣳⣽⣧⣿⣼⣿⣼⣯⣽⣯⣿⣷⣧⣮⣑⣍⠲⢌⢣⠓⡌⢮⢡⢹⢟⡙⠦⣌⣳⣥⣿⣾⣿⣹⣬⣵⣬⣶⠾⣭⠿⠽⠛⠟⠋⣍⠩⣍⠳⣨⣙⡬⡽⢮⡼⣹⢷⣯⣷⢿⣾⢿⣿⣞⣿⣿⣽⣿⣿⣿⣿⣿⣯⣿⣿
⣯⣳⣽⠾⣯⠟⠿⣛⠻⣝⠻⣙⢏⡹⢃⢏⡙⢏⠞⡽⢳⡭⣟⠿⣽⣻⠿⣽⣟⡿⣽⣷⢿⣿⣾⣧⣿⣾⣷⣿⣿⣿⣽⣿⣿⣿⣾⣶⣭⣘⡔⣂⢆⣬⣼⣷⠿⠿⢛⠛⡋⠍⢢⠐⣀⠂⠄⢂⠄⡐⢠⠉⡬⣑⠦⠳⣘⢋⠳⣉⠖⣙⠦⡻⣝⡻⢾⡽⡻⣿⢿⡾⢿⣻⣾⢿⡿⣿⣿⣿⣷⣿⣿⣿
⡍⡱⢌⠲⢄⢫⡱⢎⠳⣌⠳⡍⢎⡖⡩⢖⡹⢎⡽⡱⢫⢼⣩⢻⣜⡷⣿⣟⡿⣿⢿⡿⣿⣻⡽⣯⣛⣯⣿⢿⡿⣿⣿⣿⣿⣾⣽⣿⣿⣿⣿⣿⣿⠿⣋⠵⢊⠴⣁⠢⠌⡄⠣⠰⠠⠌⠒⠄⢂⠄⠣⢌⠡⠡⢎⠱⠠⣍⠒⣄⠚⡌⢲⠱⣌⠳⣣⣝⣳⢹⣏⡟⣿⣳⡿⣿⣻⣯⣿⢾⣿⣯⣿⣿
⠜⡰⡌⢦⡙⣆⢳⡩⢖⡬⣓⢬⡓⣬⢳⡭⢎⡳⣜⣱⢻⡎⢷⠻⣞⡹⢧⣫⢽⡹⣎⢷⣳⢯⣷⣻⢯⡿⣽⢯⣟⣿⣿⢿⣿⣿⣿⣿⣿⢿⣿⣿⢧⢳⡜⢚⠷⢶⠶⡶⣷⡼⢶⡷⢷⡾⠟⡞⢣⣜⣴⡾⣼⣳⣾⣼⣧⣦⣍⢢⡙⢌⠣⡙⢂⠳⢡⠲⣌⠳⣌⠿⡲⣽⣹⢿⢷⣻⢿⡿⣯⣿⣷⣿ */
    }


     /**
     * Pulls Note Back off of Front Rollers onto Rear Rollers
     * so that they can Spin-Up in prep for Shooting
     * 
     * @return Command
     */ 
    public Command PullBackNote() {
        return new ShootPullBack(intake);
    }  

    /**
     * Shoots note out of intake by ramping up front till it reaches
     * the speed in OperatorConstants.FrontRPM 
     * 
     * Then Shoots the Note out of the Front of the intake
     * @return Command
     */ 
    public Command LaunchNote() {
        return new ShootNote(intake);
    }  


    /**
     * Intakes note
     * Sets intake Speed to OperatorConstants.FrontOut
     * 
     * @return Command
     */ 
    public Command Intake() {
        return new IntakeNote(intake);
    }

    /** 
     * Runs intake Backward
     * Sets intake Speed to OperatorConstants FrontOut and BackOut
     * 
     * @return Command
    */
    public Command EjectBackward(){
        return Commands.startEnd(() -> {
                    System.out.println("[IntakeCommands] Eject Backward");
                    intake.setSpeed(OperatorConstants.FrontOut, -OperatorConstants.BackOut);
                    intake.setNoteStatus(false);
                },
                () -> intake.stop(),
                intake);
    }

    /** 
     * Runs intake Forward
     * Sets intake Speed to OperatorConstants FrontOut and BackOut
     * 
     * @return Command
    */
    public Command EjectForward(){
        return Commands.startEnd(() -> {
                  System.out.println("[IntakeCommands] Eject Forward");
                  intake.setSpeed(-OperatorConstants.FrontOut, OperatorConstants.FrontOut);
                  intake.setNoteStatus(false);
                },
                () -> intake.stop(), 
                intake);
    }


    /** 
     * Runs intake Backward Slowly
     * Sets intake Speed to OperatorConstants FrontOut and BackOut
     * 
     * @return Command
    */
    public Command adjustBackward(){
        return Commands.startEnd(() -> {
                    System.out.println("[IntakeCommands] Adjust Backward");
                    intake.setSpeed(OperatorConstants.FrontSlow2, -OperatorConstants.BackSlow2);
                },      
                () -> intake.stop(),
                intake);
    }

    /** Stops the Intake when called 
     *  @return Command
    */
    public Command Stop() {
        return Commands.runOnce(() -> {
                System.out.println("[IntakeCommands] Stop");
                intake.stop();
            },
            intake);
    }
}
