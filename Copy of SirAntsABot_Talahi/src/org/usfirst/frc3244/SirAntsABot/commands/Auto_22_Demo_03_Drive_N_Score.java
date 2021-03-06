package org.usfirst.frc3244.SirAntsABot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_22_Demo_03_Drive_N_Score extends CommandGroup {
    
    public  Auto_22_Demo_03_Drive_N_Score() {
    	addSequential(new Auto_Cycle_Secure_Ball(),10); //10 second time out
   	 	//addSequential(new Auto_Drive_UnderLowBar());
   	 	//addSequential(new Wrist_To_Setpoint(10));
        addSequential(new Drive_Robot_Orianted_Distance(-125,0.25));
        addSequential(new Drive_Spin_In_Place(-20));
    	addSequential(new Drive_Robot_Orianted_Distance_Strafe(75,0.25));
    	addSequential(new Drive_Robot_Orianted_Distance(45,.25));
    	addSequential(new Auto_SetWrist_to_GoalChooser());
    	addSequential(new Auto_Score_Ball_In_Goal());
    	addSequential(new Drive_Robot_Orianted_Distance(-45,.25));
    	addSequential(new PinBall_Reset());
    }
}
