// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

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
    * 6/9/2016 This started from the "Copy of SirAntsABOT_Talahi" Code
    * 			ok	1	Remove the Demo speed modifier now boots up as isCompetitionBot = true
    * 			ok	2 	added addSequential(new Drive_Robot_Orianted_Distance(45));
    * 						to "Auto_SpinFromLowBarForShotReady()"
    * 			3 	should we addParallel the wrist moment with Above item #2 the drive forward
    * 			ok	4   Claw_To_Position added a constructor that get the setpoint from the smartdashboard
    * 			ok	5	using new Claw_To_Position constructor in pinball_Cycle
    * 			ok	6	added to OI a command test smart dashboard button
    * 6/12/2016 
    * 			1	Added this.isAuto and Pinball_Shoot(boolean isAuto) constructor
    * 6/14/2016
    * 			1 	Added the correct isAutonomous to Pinball_Shooter DriverStation.getInstance().isAutonomous()
    * 			ok	2	ReEnabled the DemoMode Code for Graduation Parties
    * 6/15/2016
    * 			ok 	1 	Added Vision Subsystem and Light Relay
    * 6/17/2016
    * 			ok	1	Commented out Autonomous Schedules
    * 6/19/2016
    * 			ok  1 	Coding Out Autonomous Schedules for Grade Parties boolean GRADUATION_PARTY = false;
    * 			2	Removed Tank Drive Method in Subsystem DriveTrain. Use Robot.driveTrain.my_Drive_Mecanum in Command
    * 				Drive_Spin_In_Place
    * 			3	DriveTrain rampTarget Code started
    * 			bugged	4	Added updateStatus(); in disabledPeriodic(). Scroll not working on driverstation
    * 			5	Added paradeAction()
    * 6/20/2016
    * 			ok	1	Installed small Claw Gears and Enum
    * 			ok	2	Installed 12:1 Gears for sea Leags "No Code Changes"
    * 
    * 6/28/2016
    * 			Monitor 1	Claw with 80 tooth gear PID needs Tuning 
    * 						Seemed to be a bolt loose Monitor
    * 7/5/2016
    * 			ok		1 	added timeout to Auto_ready_Ball and Auto_Cycle_Secure_Bal
    * 			ok		2	OI.Java - Changed ScissorStartClimb.whileHeld to ScissorStartClimb.whenPresse
    * 			ok		3	Added cancel code to wrist to set point like Wrist jog
    * 						to protect the claw if scissor is up
    * 			ok		4	changed reset_1.whenPressed(new SeaLegs_Return_To_TDC());
    * 						to
    * 						reset_1.whileHeld(new SeaLegs_Return_To_TDC());
    * 7/10/2016
    * 
    * 			1	Scissor_To_Position.java
    * 				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    				// Not Allow movement if Wrist Is up !
    				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    				if(m_setpoint>70 && Robot.wristPID.getSetpoint()>25){  //now 26
    					end();
    				}else{
    *			
    *7/20/2016
    *			added some vision stuff
    *
    *7/22/2016
    *			1 Auto_ready_Climb now parrells the wrist and scissor action
    *			2 added Scissor to defend command group to also move wrist out of the way
    */


package org.usfirst.frc3244.SirAntsABot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc3244.SirAntsABot.commands.*;
import org.usfirst.frc3244.SirAntsABot.subsystems.*;

//Grip Imports
import java.io.IOException;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	private static final boolean GRADUATION_PARTY = false;
	public static NetworkTable grip;
	static Preferences prefs;
	double prefs_Shoot_Angle;
	
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
    
    //Manually Created Subsystems
    public static Vision vision;
    public static DriveVelocity driveVelocity;
    
   
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	grip = NetworkTable.getTable("GRIP");
    	
    	RobotMap.init();
    	prefs = Preferences.getInstance();
    	prefs_Shoot_Angle = prefs.getDouble("prefs_Shoot_Angle", RobotMap.WRIST_STOW_ANGLE);
    	
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrain = new DriveTrain();
        pinBall = new PinBall();
        scissorPID = new ScissorPID();
        wristPID = new WristPID();
        control = new Control();
        clawsPID = new ClawsPID();
        seaLegs = new SeaLegs();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        vision = new Vision();
        driveVelocity = new DriveVelocity();
      
        
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
        
        //Add Subsystem Monitoring
        //SmartDashboard.putData(Scheduler.getInstance());
        SmartDashboard.putData(driveTrain);
        //SmartDashboard.putData(pinBall);
        //SmartDashboard.putData(seaLegs);

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
        //autonomousChooser.addObject("Demo 1", new Auto_20_Demo_01());
        //autonomousChooser.addObject("Demo 2", new Auto_21_Demo_02());
        //autonomousChooser.addObject("Demo 3",  new Auto_22_Demo_03_Drive_N_Score());
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
    	SmartDashboard.putString("autonomousInit Auto Choice", " ");
    	RobotMap.driveTrainRobotDrive41.setSafetyEnabled(false);
    	Robot.oi.launchPad.setOutputs(0);
    	//Maybe Will Want to do this here too!!!!!!!!!
    	driveTrain.my_zeroHeading(false);    	
    	
    	
    	autonomousCommand = (Command) autonomousChooser.getSelected(); 
    	
    	autonomousSelected = autonomousChooser.getSelected().toString();
        
        
    	///********* Force this 
    	
    	//autonomousCommand = new Auto_02_Straight_Under_LowBar();
    	
    	//Maybe try to do this????
    	//if (autonomousCommand == null) autonomousCommand = new Auto_00_ReachEdgeOfObstical();
    	
        // schedule the autonomous command (example)
    	
    	//if (!GRADUATION_PARTY){
    	//	if (autonomousCommand != null) autonomousCommand.start();
    	//}
    	SmartDashboard.putString("autonomousInit Auto Choice", autonomousSelected);
    	if (autonomousCommand != null) autonomousCommand.start();
     
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    	//if (!GRADUATION_PARTY){
    		Scheduler.getInstance().run();
    	//}
    	
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
        //paradeAction();
        //Why are there two calls to this?
        //Scheduler.getInstance().run();
        
        
        /* Get published values from GRIP using NetworkTables */
        
        //for (double area : grip.getNumberArray("myContoursReport/area", new double[0])) {
        //    System.out.println("Got contour with area=" + area);
       // }
        
        
    }
    private void paradeAction(){
    	if(DriverStation.getInstance().getStickButton( 2, (byte) 7)){
        	Robot.scissorPID.enable();
        }else{
        	Robot.scissorPID.disable();
        }
        
        if(Robot.scissorPID.getPosition()>50.0){
        	Robot.scissorPID.setSetpoint(14.0);
        }else if(Robot.scissorPID.getPosition()<15.0){
        	Robot.scissorPID.setSetpoint(60.0);
        }
        
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    
    
    	
    private void updateStatus() {
    	Robot.driveTrain.updateStatus();
    	//Robot.seaLegs.updateStatus();
		//Robot.scissorPID.updateStatus();
		Robot.wristPID.updateStatus();
		//Robot.clawsPID.updateStatus();
		
	}
	
	
}
