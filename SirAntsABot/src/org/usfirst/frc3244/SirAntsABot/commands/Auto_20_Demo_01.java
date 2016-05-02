package org.usfirst.frc3244.SirAntsABot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto_20_Demo_01 extends CommandGroup {
    
    public  Auto_20_Demo_01() {
    	
    	 addSequential(new Auto_Cycle_Secure_Ball(),10); //10 second time out
    	 //addSequential(new Auto_Drive_UnderLowBar());
    	 addSequential(new Wrist_To_Setpoint(10));
         addSequential(new Drive_Robot_Orianted_Distance(-20));
         addSequential(new Drive_Spin_In_Place(-75));
     	addSequential(new Drive_Robot_Orianted_Distance_Strafe(20));
     	addParallel(new Drive_Robot_Orianted_Distance(20));
     	addSequential(new Auto_SetWrist_to_GoalChooser());
         
         
         // Auto_Score_Ball_In_Goal() Not Tested
         //addSequential(new Auto_Score_Ball_In_Goal()); //NOPE
         //**********TryThis
         //Integer goalChoice = (Integer) Robot.goalChooser.getSelected();
         //addSequential(new Auto_Cycle_Shoot_Ball(goalChoice));
         
         //************This Always Shoots
         addSequential(new PinBall_Cycle());
         addSequential(new PinBall_Reset());
    	 
    	 
 
        
    }
}
