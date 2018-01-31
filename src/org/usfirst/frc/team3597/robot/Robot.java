/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3597.robot;

//import edu.wpi.first.wpilibj.CameraServer;
//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	//Drive & Motor
	private DifferentialDrive Robot;
	public static float speed = 0.8f;
	
	//Controller
	public static Joystick Controller;
	public static boolean buttonValueA;
	public static boolean buttonValueB;
	
	//Time
	double autoDriveTime;
	
	public void robotInit() {
		System.out.println("Robot Initializing!");
		Robot = new DifferentialDrive(new Spark(IO.LEFT_MOTOR), new Spark(IO.RIGHT_MOTOR));
		Controller = new Joystick(IO.CONTROLLER);
	}

	public void autonomousInit() {
		System.out.println("Autonomous Robot Initializing!");
	}

	public void autonomousPeriodic() {
		drive(1 * speed, 1 * speed, 3f);
	}

	public void teleopPeriodic() {
		//Get Button Inputs to Control Speed
		buttonValueA = Controller.getRawButton(IO.BUTTON_A);
		buttonValueB = Controller.getRawButton(IO.BUTTON_B);
		
		if (buttonValueA) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			speed += 0.1;
			System.out.println("Speed up");
			buttonValueA = false;
		} else if (buttonValueB) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			speed -= 0.1;
			System.out.println("Speed down");
			buttonValueB= false;
		}
		
		//Get Motor Speeds to Control Drive
		double leftMotorSpeed = (double) (Controller.getRawAxis(IO.LEFT_JOYSTICK_Y_AXIS) * speed);
		double rightMotorSpeed = (double) (Controller.getRawAxis(IO.RIGHT_JOYSTICK_Y_AXIS) * speed);
		
		Robot.tankDrive(leftMotorSpeed, rightMotorSpeed);
	}
	
	public void drive (float leftMotorSpeed, float rightMotorSpeed, float time) {
		time = (time * 1000) + System.currentTimeMillis();
		double timeElapsed = System.currentTimeMillis();
		
		while (timeElapsed <= time) {
			Robot.tankDrive(1 * speed, 1 * speed);
		}
	
		Robot.tankDrive(0, 0);
	}
	
}
