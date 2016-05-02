// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.SirAntsABot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3244.SirAntsABot.Robot;

/**
 *
 */
public class SeaLegs_Reference_Left extends Command {
	
	private Timer t = new Timer();

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public SeaLegs_Reference_Left() {
    	
    	

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.seaLegs);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	t.reset();
    	t.start();
    	if (!Robot.seaLegs.my_SeaLeg_Left_At_TDC()){
    		Robot.seaLegs.my_SeaLeg_Left_initializeCounter();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if (t.get()>3){
    		Robot.seaLegs.my_SeaLegs_Jog_Left(true,0.35);
    		Robot.seaLegs.SeaLeg_Left_TDC_Found_FWD = false;
    	}else{
    		Robot.seaLegs.my_SeaLegs_Jog_Left(false,0.35);
    		Robot.seaLegs.SeaLeg_Left_TDC_Found_FWD = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.seaLegs.my_SeaLeg_Left_isSwitchSet();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.seaLegs.my_SeaLegs_Jog_Left(true,0);
    	Robot.seaLegs.my_Reset_Left_Encoder();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.seaLegs.my_SeaLegs_Jog_Left(true,0);
    }
}
