/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3597.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

public class Robot extends IterativeRobot {
	
	//Drive & Motor
	private DifferentialDrive Robot;
	public static float speed = 0.8f;
	
	//Controller
	public static Joystick Controller;
	public static boolean buttonValueA;
	public static boolean buttonValueB;
	
	//Time
	double autoWaitTime;
	double autoDriveTime;
	
	public void robotInit() {
		System.out.println("Robot Initializing!");
		Robot = new DifferentialDrive(new Spark(IO.LEFT_MOTOR), new Spark(IO.RIGHT_MOTOR));
		Controller = new Joystick(IO.CONTROLLER);
		SmartDashboard.setDefaultNumber("Wait Timer", 0);
		
	}

	public void autonomousInit() {
		System.out.println("Autonomous Robot Initializing!");
		autoWaitTime = SmartDashboard.getNumber("Wait Timer", 0); // Gets how long to wait before moving forwards, drivers must type this in when setting up the match
		autoDriveTime = 3;  // TODO: See if this drives where you need it to be
	}

	public void autonomousPeriodic() {
		double timeElapsed = 15 - DriverStation.getInstance().getMatchTime(); // The DriverStation gives an approximate time until the end of the period
		
		if (timeElapsed >= autoWaitTime) {
			if (timeElapsed <= autoWaitTime + autoDriveTime) {
				Robot.tankDrive(.2, .2); // Left and Right speeds, 20% power
			}
		}
	}

	public void teleopPeriodic() {
		//Get Button Inputs to Control Speed
		buttonValueA = Controller.getRawButton(IO.BUTTON_A);
		buttonValueB = Controller.getRawButton(IO.BUTTON_B);
		
		//Increase speed on button A & decrease on button B
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
	
}
