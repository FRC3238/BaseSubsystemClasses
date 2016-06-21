package org.usfirst.frc.team3238.robot;
/* A template for saving constants and ports
 * 
 * @author FRC Team 3238
 * 
 * @version untested
 * */
 import edu.wpi.first.wpilibj.IterativeRobot;
public class Configuration {
	public class Constants{
		int DriveMotorLeftFrontDeviceID = 0;
	}
	public class misc{
		CANTalon DriveMotorLeftFront = new CANTalon(Configuration.Constants.DriveMotorLeftFrontDeviceID);
		
		//Shooter shooter = 
	} 
}
