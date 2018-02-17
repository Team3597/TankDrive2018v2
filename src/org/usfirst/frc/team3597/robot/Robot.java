package org.usfirst.frc.team3597.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

public class Robot extends IterativeRobot {
	
	//Autonomous
	SendableChooser autoChooser;
	
	//Controller
	public static Joystick driveController;
	public static Joystick shooterController;

	//Time
	double autoWaitTime;
	double autoDriveTime;
	
	//DriveTrain
	DriveTrain RobotDrive;
	float defaultSpeed;
	
	//CubeIntake
	CubeIntake RobotIntake;
	
	public void robotInit() {
		System.out.println("Robot Initializing!");
		
		//Controller & SmartDashboard setup
		driveController = new Joystick(IO.DRIVE_CONTROLLER);
		shooterController = new Joystick(IO.SHOOTER_CONTROLLER);
		
		SmartDashboard.setDefaultNumber("Wait Timer", 0);
		autoChooser = new SendableChooser();
		try {
			autoChooser.addDefault("Autonomous Program 1", new TestAutonomous());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//DriveTrain setup
		defaultSpeed = 0.8f;
		RobotDrive = new DriveTrain(IO.LEFT_DRIVE_MOTOR, IO.RIGHT_DRIVE_MOTOR, defaultSpeed);
		RobotIntake = new CubeIntake(IO.LEFT_INTAKE_MOTOR, IO.RIGHT_INTAKE_MOTOR, IO.ARM_MOTOR,
				IO.LEFT_SHOOTER_MOTOR, IO.RIGHT_SHOOTER_MOTOR);
	}

	public void autonomousInit() {
		System.out.println("Autonomous Robot Initializing!");
		autoWaitTime = SmartDashboard.getNumber("Wait Timer", 0);
		autoDriveTime = 2;
		
		DriveTrain Robot = new DriveTrain(IO.LEFT_DRIVE_MOTOR, IO.RIGHT_DRIVE_MOTOR, defaultSpeed);
		RobotIntake = new CubeIntake(IO.LEFT_INTAKE_MOTOR, IO.RIGHT_INTAKE_MOTOR, IO.ARM_MOTOR,
				IO.LEFT_SHOOTER_MOTOR, IO.RIGHT_SHOOTER_MOTOR);
	}

	public void autonomousPeriodic() {
		autoChooser.getSelected();
		/*double timeElapsed = 15 - DriverStation.getInstance().getMatchTime(); // The DriverStation gives an approximate time until the end of the period
		
		System.out.println("timeElapsed >= autoWaitTime\n" + timeElapsed + " >= " + autoWaitTime);
		if (timeElapsed >= autoWaitTime) {
			System.out.println("timeElapsed >= autoWaitTime + autoDriveTime\n" + timeElapsed + " >= " + autoWaitTime + " + " + autoDriveTime);
			if (timeElapsed <= autoWaitTime + autoDriveTime) {
				DriveTrain.drive(1, 1);
			}
		}*/
	}

	public void teleopPeriodic() {
		double leftMotorSpeed = DriveTrain.getLeftMotorSpeed(driveController, IO.DRIVE_LEFT_JOYSTICK_Y_AXIS);
		double rightMotorSpeed = DriveTrain.getRightMotorSpeed(driveController, IO.DRIVE_RIGHT_JOYSTICK_Y_AXIS);
		
		RobotDrive.changeSpeed(driveController, IO.DRIVE_BUTTON_A, defaultSpeed);
		
		RobotDrive.drive(leftMotorSpeed, rightMotorSpeed);
		
		boolean intakingButton = CubeIntake.getButtonValue(shooterController, IO.SHOOT_BUTTON_A);
		boolean feedingButton = CubeIntake.getButtonValue(shooterController, IO.SHOOT_BUTTON_Y);
		boolean armUpButton = CubeIntake.getButtonValue(shooterController, IO.SHOOT_BUTTON_RB);
		boolean armDownButton = CubeIntake.getButtonValue(shooterController, IO.SHOOT_BUTTON_LB);
		
		CubeIntake.intake(intakingButton, feedingButton);
		
		RobotIntake.moveArm(armUpButton, armDownButton);
	}
	
}
