// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.




package org.usfirst.frc3244.SirAntsABot;

/*
    * 3-30-2016 no	1 Added No Goal Choice for goalChooser 
    * 					No Goal = 0, High Goal = 1, Low Goal = 2
    * 			no	2 Auto_Drive_UnderLowBar() Now selects "Auto_Score_Ball_In_Goal()"
    * 			ok	3 Added Velocity Factor Code 
    * 			ok	4 In Robot OI changed SmartDashboard button wrist to stow to use RobotMap.WRIST_STOW_ANGLE
    * 					This will get over written when Java is generated in Robotbuilder
    * 					NOTE : REMOVED ALL WRIST TO SMARTDASHBOAD BUTTON CHECKS IN ROBOTBUILDER
    * 				
    * 4-6-2016	Trying to Build the Auto Drive Under Lower bar code to shoot or not
    * 
    * 4-17-2016 Trying to straighten up the negative values to DriveTrain so in code it make sense.
    * 4-17-2016 Must fix "Drive_Robot_Orianted_Distance" isFinished    * 
    * 4-17-2016 teleopPeriodic() had two calls to Scheduler.getInstance().run();
    * 
    */

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3244.SirAntsABot.commands.*;
import org.usfirst.frc3244.SirAntsABot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Command autonomousCommand;
	private Integer scancount = 0 ;
	private Integer sequence = 0 ;
	private Integer count = 0;
	private String autonomousSelected;
	public static SendableChooser autonomousChooser;
	public static SendableChooser goalChooser;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static PinBall pinBall;
    public static ScissorPID scissorPID;
    public static WristPID wristPID;
    public static Control control;
    public static ClawsPID clawsPID;
    public static SeaLegs seaLegs;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public static DriveVelocity driveVelocity;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrain = new DriveTrain();
        pinBall = new PinBall();
        scissorPID = new ScissorPID();
        wristPID = new WristPID();
        control = new Control();
        clawsPID = new ClawsPID();
        seaLegs = new SeaLegs();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveVelocity = new DriveVelocity();
        
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
        
        //Add Subsystem Monitoring
        SmartDashboard.putData(Scheduler.getInstance());
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(pinBall);
        SmartDashboard.putData(seaLegs);

        // instantiate the command used for the autonomous period
        
        //Set up Choosers
        setupGoalChooser();
        setupAutomousChooser();
        
        //Zero The Gyro
        driveTrain.my_zeroHeading(true);
      
    }
    private void setupAutomousChooser(){
    	//Create the Auto Chooser
    	//SmartDashboard.putString("autonomous Title", "Autonomous Choice");
        autonomousChooser = new SendableChooser();
        autonomousChooser.addDefault("0: Reach Edge Of Obstical", new Auto_00_ReachEdgeOfObstical());
        autonomousChooser.addObject("1: Do Nothing:", new Auto_01_DoNothing());
        autonomousChooser.addObject("2: Straight Under LowBar", new Auto_02_Straight_Under_LowBar());
        autonomousChooser.addObject("3: Strafe To Position Under LowBar", new Auto_03_Strafe_To_Position_Under_LowBar());
        autonomousChooser.addObject("4: Straight_Over Rought Terrain", new Auto_04_Straight_Over_RoughtTerrain());
        autonomousChooser.addObject("Demo 1", new Auto_20_Demo_01());
        autonomousChooser.addObject("Demo 2", new Auto_21_Demo_02());
        //Add More Options
        //Place autonomousChooser on the SmartDashboard
        SmartDashboard.putData("Autonomous Chooser", autonomousChooser);
    }
    
    private void setupGoalChooser(){
    	//SmartDashboard.putString("goalChooser Title", "Auto Goal Target");
    	goalChooser = new SendableChooser();
    	goalChooser.addDefault("No Shot", 0);
    	goalChooser.addObject("High Goal", 1);
    	goalChooser.addObject("Low Goal", 2);
    	SmartDashboard.putData("Goal Chooser", goalChooser);
    }
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	Robot.oi.launchPad.setOutputs(0);
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        scancount  = scancount+1;
        Robot.oi.launchPad.setOutputs(sequence);
        
       
        
        int pattern = 1;
        
		if (pattern == 0){
			if (scancount > 10){
	        	sequence = sequence+1;
	        	scancount = 0;
	
	        	//Test SmartDashboar Send the current AutoChoice
	        	 autonomousSelected = autonomousChooser.getSelected().toString();
	             SmartDashboard.putString("Auto Choice", autonomousSelected);
	             
	        }
	        if (sequence > 2048){
	        	sequence = 0;
	        }
        }
		if (pattern == 1){
			if (scancount > 10){
	        	sequence = sequence<<1;
	        	scancount = 0;
	        	count =count +1;
	        }
	        if (count == 11){
	        	
	        	sequence = sequence+1;
	        	count = 0;
	        	
	        	//Test SmartDashboar Send the current AutoChoice
	        	 autonomousSelected = autonomousChooser.getSelected().toString();
	             SmartDashboard.putString("Auto Choice", autonomousSelected);
	        }
        }
        
        
    }

    public void autonomousInit() {
    	RobotMap.driveTrainRobotDrive41.setSafetyEnabled(false);
    	Robot.oi.launchPad.setOutputs(0);
    	//Maybe Will Want to do this here too!!!!!!!!!
    	//driveTrain.my_zeroHeading(true);    	
    	autonomousCommand = (Command) autonomousChooser.getSelected(); 
    	
    	///********* Force this 
    	
    	//autonomousCommand = new Auto_02_Straight_Under_LowBar();
    	
    	//Maybe try to do this????
    	//if (autonomousCommand == null) autonomousCommand = new Auto_00_ReachEdgeOfObstical();
    	
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        updateStatus();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        //Clear the Driver station Operator panel Outputs
        Robot.oi.launchPad.setOutputs(0);
        RobotMap.driveTrainRobotDrive41.setSafetyEnabled(true);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        updateStatus();
        
        //Why are there two calls to this?
        //Scheduler.getInstance().run();
        
        
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    
    
    private void updateStatus() {
    	Robot.driveTrain.updateStatus();
    	Robot.seaLegs.updateStatus();
		Robot.scissorPID.updateStatus();
		Robot.wristPID.updateStatus();
		Robot.clawsPID.updateStatus();
		
	}
   
}