package org.usfirst.frc.team3597.robot;

public class TestAutonomous {

	public TestAutonomous() throws InterruptedException {
		System.out.println("1 L, 1 R");
		Thread.sleep(5000); //Do command for 5 seconds
		
		System.out.println("0.8f L, 0 R");
		Thread.sleep(1000); //Do command for 1 second
		
		System.out.println("0 L, 0 R");
		System.out.println("Shoot");
		Thread.sleep(500); //Do command for 0.5 second
		
		System.out.println("-0.8f L, 0 R");
		Thread.sleep(1000); //Do command for 1 second
		
		System.out.println("-1 L, -1 R");
		Thread.sleep(5000); //Do command for 5 seconds
	}
}
