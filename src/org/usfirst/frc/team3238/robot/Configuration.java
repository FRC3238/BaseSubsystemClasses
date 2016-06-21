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
		
		public class Joystick {
			int[] collectorInButton = new int[]{0, 1}; 
//used by other class with Configuration.Constants.Joystick.collectorInButton[Configuration.Constants.Joystick.activePreset];
			static int activePreset = 0;
		}
	}
	public class misc{
		CANTalon DriveMotorLeftFront = new CANTalon(Configuration.Constants.DriveMotorLeftFrontDeviceID);
		Chassis chassis = new Chassis(DriveMotorLeftFront, DriveMotorRightFront, ...);
		//Shooter shooter = 
	} 
	public void ChangeJoystickMapping(int preset) {
		Configuration.Constants.Joystick.activePreset = preset;
	}
}
