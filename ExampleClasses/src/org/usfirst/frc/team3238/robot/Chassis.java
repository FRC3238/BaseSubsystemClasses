package org.usfirst.frc.team3238.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
	/* A template for simplifying future chassis classes
	 * 
	 * @author FRC Team 3238
	 * @author Garrett Smith
	 * 
	 * @version untested
	 */
public class Chassis {
	private Logger log;
	
	private CANTalon[] DriveMotors, leftMotors, rightMotors;
	
	private Timer localTimer = new Timer();
	
	private double leftSpeed = 0.0, rightSpeed = 0.0;
	/**
	 * Precondition: driveMotors is a nonzero Talon array with a multiple of two motors
	 * */
	public Chassis(CANTalon[] driveMotors) {
		this.DriveMotors = driveMotors;
		log.info("Motors from index 0 to " + driveMotors.length/2 + " are defined as left motors");
		log.info("Other motors are right drive system");
		init();
	}
	

	public void init() {
		setupLeftRightIndex();
		checkMotorExistence();
	}
	
	public void setupLeftRightIndex() {
		int leftEndIndex = DriveMotors.length/2;
		this.leftMotors = Arrays.copyOfRange(DriveMotors, 0, leftEndIndex);
		this.rightMotors = Arrays.copyOfRange(DriveMotors, leftEndIndex, DriveMotors.length);
	}
	
	
	public void invertLeft() {
		for(CANTalon cs: leftMotors) cs.setInverted(!cs.getInverted());	
	}
	public boolean getInverted() {
		return leftMotors[0].getInverted() && rightMotors[0].getInverted();
	}
	
	public void invertRight() {
		for(CANTalon cs: rightMotors)  cs.setInverted(!cs.getInverted());
	}
	
	
	//public void addEncoder(CANTalon addEncoderTo, Encoder encoderToAdd) {}
	
	public void checkMotorExistence() {
		for(int i = 0; i < DriveMotors.length; i++) 
			if(DriveMotors[i] == null) 
				log.error("Motor Invalid At Index: " + i, new NullPointerException());
	}
	
	public void setSpeed(double speed) {
		for(CANTalon each : DriveMotors) each.set(limitOutput(speed));
	}
	
	public void setSpeed(double lSpeed, double rSpeed) {
		for(CANTalon left : leftMotors) left.set(limitOutput(lSpeed));
		for(CANTalon right : rightMotors) right.set(limitOutput(rSpeed));
	}
	
	public void turn(double speed) {
		setSpeed(speed, -speed);
	}
	
	public void disable() {
		setSpeed(0.0);
	}
	
	public double getLeftSpeed() {
		return leftMotors[0].getSpeed();
	}
	public double getRightSpeed() {
		return rightMotors[0].getSpeed();
	}
	
	private double limitOutput(double d) {
		if(Math.abs(d) > 1.0) return Math.abs(d)/d; return d;
	}
	
	public void driveAbsoluteCurved(double maxSpeed, double time) {
		
			localTimer.start();
		while(localTimer.get() < time) {
			double curveAmplifier = Math.toRadians(localTimer.get()/time);
			setSpeed(maxSpeed, maxSpeed - (Math.sin(curveAmplifier)*Math.PI));
		} 
			localTimer.stop();
			localTimer.reset();
	}
	public void tankDrive(Joystick leftSide, Joystick rightSide) {
		setSpeed(leftSide.getY(), rightSide.getY());
	}
	//WIP
	public void arcadeDrive(Joystick primary) {
		setSpeed(primary.getY() + primary.getTwist(), primary.getY() - primary.getTwist());
	}
	//public void zonedDrive() 
	
	public void invert() {
		invertLeft();
		invertRight();
	}
}
