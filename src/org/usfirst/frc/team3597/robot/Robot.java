package org.usfirst.frc.team3597.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class Robot extends IterativeRobot {
	
	//Autonomous
	private int robotPosition;
	private String gameData;
	// Keeps track of time state was entered
    private Timer autonStateTimer;
    // Keeps track of current state
    private int autonState;
    // List of possible states    
    private final static int AUTON_STATE_DRIVE_FORWARD = 1;
    private final static int AUTON_STATE_STOP = 2;
    private final static int AUTON_STATE_SHOOT = 3;
    private final static int AUTON_STATE_FINISHED = 4;
    private final static int AUTON_STATE_DRIVE_TURN = 5;
	
	//Controller
	private static Joystick driveController;
	private static Joystick shooterController;
	
	//DriveTrain
	DriveTrain RobotDrive;
	float defaultSpeed;
	
	//CubeIntake
	CubeIntake RobotIntake;
	
	public void robotInit() {
		System.out.println("Robot Initializing!");
		
		//Controller setup
		driveController = new Joystick(IO.DRIVE_CONTROLLER);
		shooterController = new Joystick(IO.SHOOTER_CONTROLLER);
		
		//Robot setup
		defaultSpeed = 0.8f;
		RobotDrive = new DriveTrain(IO.LEFT_DRIVE_MOTOR, IO.RIGHT_DRIVE_MOTOR, defaultSpeed);
		RobotIntake = new CubeIntake(IO.LEFT_INTAKE_MOTOR, IO.RIGHT_INTAKE_MOTOR, IO.ARM_MOTOR,
				IO.LEFT_SHOOTER_MOTOR, IO.RIGHT_SHOOTER_MOTOR);
		
		//Smart Dashboard setup
		SmartDashboard.putNumber("Position", 1);
		//CameraServer.getInstance().startAutomaticCapture();
	}

	private void changeAutonState(int nextState) {
    	if (nextState != autonState) {
    		autonState = nextState;
    		autonStateTimer.reset();
    	}
    }
    
    public void autonomousInit() {
    	System.out.println("Autonomous Initalized!");
    	
    	//Get SmartDashboard & Game Data
    	robotPosition = (int) SmartDashboard.getNumber("Position", 1);
    	gameData = DriverStation.getInstance().getGameSpecificMessage();
    	
    	// Reset auton state to initial drive forward and reset the timer
    	autonState = AUTON_STATE_DRIVE_FORWARD;
    	if (robotPosition == 2) autonState = AUTON_STATE_DRIVE_TURN;
    	autonStateTimer = new Timer();
        autonStateTimer.start();
    }

    public void autonomousPeriodic() {
    	//Left & Right starting position
    	if (robotPosition == 1 || robotPosition == 3) {
    		if (gameData.length() > 0) {
    			//Left side
    			if(gameData.charAt(0) == 'L' && robotPosition == 1 ||
    					gameData.charAt(0) == 'R' && robotPosition == 3) {
    				System.out.println("L1 || R3");
    				switch (autonState) {
    		    	
    		    	case AUTON_STATE_DRIVE_FORWARD: {
    		    		RobotDrive.drive(0.5, 0.5);
    		    		if (autonStateTimer.hasPeriodPassed(3.3)) {
    		    			changeAutonState(AUTON_STATE_STOP);
    		    		}
    		    		break;
    		    	}
    		    	
    		    	case AUTON_STATE_STOP: {
    		    		RobotDrive.drive(0, 0);
    		    		if (autonStateTimer.hasPeriodPassed(0.5)) {
    		    			changeAutonState(AUTON_STATE_SHOOT);
    		    		}
    		    		break;
    		    	}
    		    	
    		    	case AUTON_STATE_SHOOT: {
    		    		RobotIntake.shooter.speed = 0.35d;
    		    		RobotIntake.intake(false, true);
    		    		if (autonStateTimer.hasPeriodPassed(1.5)) {
    		    			changeAutonState(AUTON_STATE_FINISHED);
    		    		}
    		    		break;
    		    	}

    		    	case AUTON_STATE_FINISHED: {
    		    		RobotIntake.shooter.speed = 1d;
    		    		RobotIntake.intake(false, false);
    		    		break;
    		    	}
    		    	}
    			}
    			//Right side
    			else {
    				System.out.println("R1 || L3");
    				switch (autonState) {
    				
    				case AUTON_STATE_DRIVE_FORWARD: {
    		    		RobotDrive.drive(0.5, 0.5);
    		    		if (autonStateTimer.hasPeriodPassed(4d)) {
    		    			changeAutonState(AUTON_STATE_STOP);
    		    		}
    		    		break;
    		    	}
    				
    				case AUTON_STATE_STOP: {
    		    		RobotDrive.drive(0, 0);
    		    		if (autonStateTimer.hasPeriodPassed(0.5)) {
    		    			changeAutonState(AUTON_STATE_SHOOT);
    		    		}
    		    		break;
    		    	}
    				
    				case AUTON_STATE_FINISHED: {
    		    		RobotIntake.shooter.speed = 1d;
    		    		RobotIntake.intake(false, false);
    		    		break;
    		    	}
    				
    				}
    			}
    		}
    	}
    	//Center starting position
    	if (robotPosition == 2) {
    		if (gameData.length() > 0) {
    			//Left side
    			if(gameData.charAt(0) == 'L') {
    				System.out.println("L2");
    				switch (autonState) {
    				
    				case AUTON_STATE_DRIVE_TURN: {
    					RobotDrive.drive(0, 0.5);
    					if (autonStateTimer.hasPeriodPassed(3d)) {
    						changeAutonState(AUTON_STATE_DRIVE_FORWARD);
    					}
    					break;
    				}
    				
    				case AUTON_STATE_DRIVE_FORWARD: {
    					RobotDrive.drive(0.5, 0.5);
    					if (autonStateTimer.hasPeriodPassed(2d)) {
    						changeAutonState(AUTON_STATE_STOP);
    					}
    					break;
    				}
    				
    				case AUTON_STATE_STOP: {
    		    		RobotDrive.drive(0, 0);
    		    		if (autonStateTimer.hasPeriodPassed(0.5)) {
    		    			changeAutonState(AUTON_STATE_SHOOT);
    		    		}
    		    		break;
    		    	}
    				
    				case AUTON_STATE_SHOOT: {
    		    		RobotIntake.shooter.speed = 0.5d;
    		    		RobotIntake.intake(false, true);
    		    		if (autonStateTimer.hasPeriodPassed(1.5)) {
    		    			changeAutonState(AUTON_STATE_FINISHED);
    		    		}
    		    		break;
    		    	}
    				
    				case AUTON_STATE_FINISHED: {
    		    		RobotIntake.shooter.speed = 1d;
    		    		RobotIntake.intake(false, false);
    		    		break;
    		    	}
    				}
    			}
    			//Right side
    			else {
    				System.out.println("R2");
    				switch (autonState) {
    				
    				case AUTON_STATE_DRIVE_TURN: {
    					RobotDrive.drive(0.5, 0);
    					if (autonStateTimer.hasPeriodPassed(2d)) {
    						changeAutonState(AUTON_STATE_DRIVE_FORWARD);
    					}
    					break;
    				}
    				
    				case AUTON_STATE_DRIVE_FORWARD: {
    					RobotDrive.drive(0.5, 0.5);
    					if (autonStateTimer.hasPeriodPassed(3.3d)) {
    						changeAutonState(AUTON_STATE_STOP);
    					}
    					break;
    				}
    				
    				case AUTON_STATE_STOP: {
    		    		RobotDrive.drive(0, 0);
    		    		if (autonStateTimer.hasPeriodPassed(0.5)) {
    		    			changeAutonState(AUTON_STATE_SHOOT);
    		    		}
    		    		break;
    		    	}
    				
    				case AUTON_STATE_SHOOT: {
    		    		RobotIntake.shooter.speed = 0.35d;
    		    		RobotIntake.intake(false, true);
    		    		if (autonStateTimer.hasPeriodPassed(1.5)) {
    		    			changeAutonState(AUTON_STATE_FINISHED);
    		    		}
    		    		break;
    		    	}
    				
    				case AUTON_STATE_FINISHED: {
    		    		RobotIntake.shooter.speed = 1d;
    		    		RobotIntake.intake(false, false);
    		    		break;
    		    	}
    				}
    			}
    		}
    	}
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
