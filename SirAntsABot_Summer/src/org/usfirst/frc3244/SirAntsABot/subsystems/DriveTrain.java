// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3244.SirAntsABot.subsystems;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;
import org.usfirst.frc3244.SirAntsABot.commands.*;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class DriveTrain extends Subsystem {
	
	public static double velacityFactor = RobotMap.RobotDriveTrainSettings.VELOCITY_NORMAL.get();

	private double currentRate;
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController motor_Right_Front = RobotMap.driveTrainMotor_Right_Front;
    private final SpeedController motor_Right_Rear = RobotMap.driveTrainMotor_Right_Rear;
    private final SpeedController motor_Left_Rear = RobotMap.driveTrainMotor_Left_Rear;
    private final SpeedController motor_Left_Front = RobotMap.driveTrainMotor_Left_Front;
    private final AnalogGyro robot_Gyro = RobotMap.driveTrainRobot_Gyro;
    private final Encoder encoder_Left_Front = RobotMap.driveTrainEncoder_Left_Front;
    private final Encoder encoder_Left_Rear = RobotMap.driveTrainEncoder_Left_Rear;
    private final Encoder encoder_Right_Rear = RobotMap.driveTrainEncoder_Right_Rear;
    private final Encoder encoder_Right_Front = RobotMap.driveTrainEncoder_Right_Front;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    NordicSpeedController nLF = RobotMap.nordicLF;
    NordicSpeedController nRF = RobotMap.nordicRF;
    NordicSpeedController nLR = RobotMap.nordicLR;
    NordicSpeedController nRR = RobotMap.nordicRR;
    RobotDrive robotDrive41 = RobotMap.driveTrainRobotDrive41;

	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new Drive_Robot_Robot_Oriented_Mecanum());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    public void my_Auto_Drive_Mecanum_No_VelocityFactor(double x, double y, double r, double gyro){
    	x = Robot.driveTrain.target(x,.1,-1,1);
    	y = Robot.driveTrain.target(y,.1,-1,1);
    	r = Robot.driveTrain.target(r,.1,-1,1);
    	
    	robotDrive41.mecanumDrive_Cartesian(x, y, -r, gyro);
    }
    
    public void my_Drive_Mecanum(double x, double y, double r, double gyro){
    	
    	//Slow It down
    	x=x*velacityFactor;
    	y=y*velacityFactor;
    	r=r*velacityFactor;
    	
    	x = Robot.driveTrain.target(x,.1,-1,1);
    	y = Robot.driveTrain.target(y,.1,-1,1);
    	r = Robot.driveTrain.target(r,.1,-1,1);
    	
    	//x = Robot.driveTrain.rampTarget(x,.1,-1,1,.2);
    	//y = Robot.driveTrain.rampTarget(y,.1,-1,1,.2);
    	//r = Robot.driveTrain.rampTarget(r,.1,-1,1,.2);
    	
    robotDrive41.mecanumDrive_Cartesian(x, y, -r, gyro);	
    }
    
    //public void tankDrive(double leftValue, double rightValue) {
	//	// TODO Auto-generated method stub
    //	robotDrive41.tankDrive(leftValue, rightValue);
	//}
    
    public double target(double target,double deadband, double min, double max){
    	if(Math.abs(target)<deadband){
    		target=0;
    	}
    	
    	//Check Target less than max
    	if(target>max){
    		target=max;
    	}
    	
    	//Check Target Greater Than Min
    	if(target<-max){
    		target=-max;
    	}
    	
    	return target;
    }
    
    private double rampTarget(double target,double deadband, double min, double max, double ramp){
    	//Check That the Target is in bound
    	target = Robot.driveTrain.target(target,deadband,min,max);
    	if(target==0){
    		ramp = .8;
    	}
    	currentRate = currentRate + ((target - currentRate)*ramp);
		return currentRate;
    }
    
    //////////////////////////
    //   SMARTDASHBOARD CODE
    //////////////////////////
    
    public void updateStatus(){
    	SmartDashboard.putNumber("Distance Travled", encoder_Left_Front.getDistance());
    	SmartDashboard.putNumber("Current Velacity Factor", DriveTrain.velacityFactor);
    	SmartDashboard.putData("Gyro",robot_Gyro);
    	SmartDashboard.putBoolean("Demo Mode",!RobotMap.isCompetitionBot);
    	if (velacityFactor>=RobotMap.RobotDriveTrainSettings.VELOCITY_TURBO.get()){
    		Robot.oi.launchPad.setOutput(7, true);
    		Robot.oi.launchPad.setOutput(8, false);
    		Robot.oi.launchPad.setOutput(9, false);
    	}else if (velacityFactor==RobotMap.RobotDriveTrainSettings.VELOCITY_NORMAL.get()){
    		Robot.oi.launchPad.setOutput(7, false);
    		Robot.oi.launchPad.setOutput(8, true);
    		Robot.oi.launchPad.setOutput(9, false);
    	}else{
    		Robot.oi.launchPad.setOutput(7, false);
    		Robot.oi.launchPad.setOutput(8, false);
    		Robot.oi.launchPad.setOutput(9, true);
    	}
    	
    	
    }
    
    public void diagnostics(double x, double y, double r, double gyro){
    	SmartDashboard.putNumber("X= ", x);
    	SmartDashboard.putNumber("Y= ", y);
    	SmartDashboard.putNumber("R= ", r);
    	SmartDashboard.putNumber("Current Velacity Factor", DriveTrain.velacityFactor);
    	//SmartDashboard.putNumber("Gyro= ", gyro);
    	SmartDashboard.putData("Gyro",robot_Gyro);
    }
    
    
    /////////////////////////
    //  GYRO CODE
    ////////////////////////
    
    public double my_Get_Gyro(){
    	return robot_Gyro.getAngle();
    }
    
    public void my_zeroHeading(boolean fullReset) {
		if(fullReset){
			robot_Gyro.initGyro();
		} else {
			robot_Gyro.reset();
		}
	}
    
    ///////////////////////
    // ENCODER CODE
    ///////////////////////
    
    public void my_Reset_ALL_Encoders(){
    	encoder_Left_Front.reset();
    	encoder_Left_Rear.reset();
    	encoder_Right_Front.reset();
    	encoder_Right_Rear.reset();
    }
    
    public void my_Reset_Left_Front_Encoder(){
    	encoder_Left_Front.reset();
    }
    
    public double my_Get_Left_Front_Encoder(){
    	return encoder_Left_Front.getDistance();
    }

	
}

