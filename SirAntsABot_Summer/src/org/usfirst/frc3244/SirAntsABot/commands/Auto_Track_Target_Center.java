package org.usfirst.frc3244.SirAntsABot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3244.SirAntsABot.Robot;
import org.usfirst.frc3244.SirAntsABot.RobotMap;

/**
 *
 */
public class Auto_Track_Target_Center extends Command {

    private static final int image_Width = 350;
    private static final int image_Height = 260;
	private double errorX=0;
	private double errorY=0;
	private double errorTarget=0;
	private double image_targetArea = 5000;
	
	
	public Auto_Track_Target_Center() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.driveTrain);
		requires(Robot.wristPID);
		
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	System.out.println("Auto_Track_Target_Center init");
    	SmartDashboard.putNumber("Tunning", .005);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double[] x = Robot.grip.getNumberArray("myContoursReport/centerX", new double[0]);
    	double[] y = Robot.grip.getNumberArray("myContoursReport/centerY", new double[0]);
    	double[] area = Robot.grip.getNumberArray("myContoursReport/area", new double[0]);
    	
    	if(x.length>0){
    		//System.out.println("Yes");
    		double centerX = x[0];
    		double centerY = y[0];
    		double areaTarget = area[0];
    		errorX = (centerX-(image_Width/2));
    		errorY = (centerY-(image_Height/2));
    		errorTarget = areaTarget-image_targetArea;
    	}else{
    		//System.out.println("No");
    		double centerX = image_Width/2;
    		double centerY = image_Width/2;
    		double areaTarget = image_targetArea;
    		errorX = (centerX-(image_Width/2));
    		errorY = (centerY-(image_Height/2));
    		errorTarget = areaTarget-image_targetArea;
    	}
    	
    	
    	
    	//System.out.println("ErrorX=" +  errorX);
    	System.out.println("errorTarget=" +  errorTarget);
    	//errorTarget*SmartDashboard.getNumber("Tunning")
    	Robot.driveTrain.my_Drive_Mecanum(0,0, errorX*.003, 0);
    	
    	
    	
    	double w_setpoint = Robot.wristPID.getPosition()-(errorY*0.05);
    	
    	//System.out.println("w_setpoint=" +  w_setpoint);
		Robot.wristPID.setSetpoint(w_setpoint );
    	
    	
    	
    	
    	
    	//for (double area : Robot.grip.getNumberArray("myContoursReport/area", new double[0])) {
        //    System.out.println("Got contour with area=" + area);
        //}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}