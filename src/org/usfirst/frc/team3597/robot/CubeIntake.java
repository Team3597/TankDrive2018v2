package org.usfirst.frc.team3597.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;

public class CubeIntake {
	
	private static Spark leftIntakeMotor;
	private static Spark rightIntakeMotor;
	private static Spark armMotor;
	private static CubeShooter shooter;
	private static final float SPEED = 1f;
	
	//Creates a CubeShooter object
	public CubeIntake (int leftIntakeMotor, int rightIntakeMotor, int armMotor,
			int leftShooterMotor, int rightShooterMotor) {
		CubeIntake.leftIntakeMotor = new Spark(leftIntakeMotor);
		CubeIntake.rightIntakeMotor = new Spark(rightIntakeMotor);
		CubeIntake.armMotor = new Spark(armMotor);
		shooter = new CubeShooter(leftShooterMotor, rightShooterMotor, SPEED);
	}
	
	//Sets the shooter motors to moving or not moving
	public static void intake(boolean intaking, boolean feeding) {
		if (intaking) {
			System.out.println("Intake");
			leftIntakeMotor.set(-0.8f);
			rightIntakeMotor.set(-0.8f);
		} else  if (feeding) {
			System.out.println("Feed");
			leftIntakeMotor.set(1);
			rightIntakeMotor.set(1);
			shooter.shoot(true);
		} else {
			leftIntakeMotor.set(0);
			rightIntakeMotor.set(0);
			shooter.shoot(false);
		}
	}
	
	
	public static void moveArm (boolean moveUp, boolean moveDown) {
		if (moveDown) {
			System.out.println("Move Down");
			armMotor.set(0.3f);
		} else if (moveUp) {
			System.out.println("Move Up");
			armMotor.set(-0.5f);
		} else {
			armMotor.set(0);
		}
	}
	
	public static boolean getButtonValue (Joystick controller, int button) {
		return controller.getRawButton(button);
	}
}
