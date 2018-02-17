package org.usfirst.frc.team3597.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
	
	private static float speed;
	private static DifferentialDrive Robot;
	private static boolean buttonValue = false;
	
	//Creates a new DriveTrain object
	public DriveTrain (int leftPort, int rightPort, float speed) {
		DriveTrain.speed = speed;
		DriveTrain.Robot = new DifferentialDrive(new Spark(leftPort), new Spark(rightPort));
	}
	
	//Sets the left and right motors to their set speed
	public static void drive(double leftMotorSpeed, double rightMotorSpeed) {
		Robot.tankDrive(leftMotorSpeed, rightMotorSpeed);
	}
	
	//Calculates the speed the left motor should be
	public static double getLeftMotorSpeed (Joystick controller, int leftJoystickYAxis) {
		return (double) (controller.getRawAxis(leftJoystickYAxis) * speed);
	}
	
	//Calculates the speed the right motor should be
	public static double getRightMotorSpeed (Joystick controller, int rightJoystickYAxis) {
		return (double) (controller.getRawAxis(rightJoystickYAxis) * speed);
	}
	
	public static void changeSpeed (Joystick controller, int shooterButton, float defaultSpeed) {
		
		//Get Button Input to Control Speed
		boolean shooterButtonValue = controller.getRawButton(shooterButton);
		
		//Increase speed on button A & decrease on button B
		if (shooterButtonValue && buttonValue) {
			speed = defaultSpeed;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			buttonValue = false;
		} else if (shooterButtonValue) {
			speed = defaultSpeed / 2;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			buttonValue = true;
		}
	}
}
