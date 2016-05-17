package org.usfirst.frc.team3238.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;

/* Manages a SwerveDrive System
 *  @author Garrett Smith
 *  @version 0.0
 * */
public class SwerveDriveChassis {
	private Encoder[] AngleMeasurer, SpeedMeasurer;
	private CANTalon[] DriveMotors, AngleMotors;
	private boolean hasSpeedEncoders = true;
	private int[] angleCompensations, updateTick;
	private final int accelerationConstant = Constants.SwerveDrive.accelerationConstant;

	/*
	 * Constructor to create Swerve Drive Management Object
	 * 
	 * @param Tilt An array of encoders added to measure the current angle of
	 * their controlled wheels.
	 * 
	 * @param Speed An optional array of encoders to measure motor speed
	 * 
	 * @param DriveMotor Array of CANTalons that move the bot
	 * 
	 * @param AngleMotor Array of CANTalons that angle the DriveMotors
	 */
	public SwerveDriveChassis(Encoder[] Tilt, Encoder[] Speed,
			CANTalon[] DriveMotor, CANTalon[] AngleMotor) {
		AngleMeasurer = Tilt;
		DriveMotors = DriveMotor;
		AngleMotors = AngleMotor;

		if (Speed.length > 0)
			SpeedMeasurer = Speed;
		else
			hasSpeedEncoders = false;
		init();
	}

	public SwerveDriveChassis(Encoder[] Tilt, CANTalon[] DriveMotor,
			CANTalon[] AngleMotor) {
		this(Tilt, new Encoder[0], DriveMotor, AngleMotor);
	}

	public void init() {
		implementAngleCompensation();
	}

	/*
	 * May be removed depends on mechanical aspects of design
	 */
	public void implementAngleCompensation() {
		/*
		 * angleCompensations = new int[4] {Constants.lFCompensation,
		 * Constants.lBCompensation, Constants.rFCompensation,
		 * Constants.rBCompensation};
		 */
	}

	/*
	 * @return the current angles of the swerve drive motors
	 */
	public int[] getTilt() {
		int[] tilt = new int[4];
		for (int i = 0; i < 4; i++)
			tilt[i] = AngleMeasurer[i].get();
		return tilt;
	}

	/*
	 * Minimizes the movement required to tilt the motor
	 * 
	 * @param tilt[], an array of absolute adjustments to be made to the
	 * rotating motor
	 * 
	 * @return difference[], an array of the fastest relative adjustments each
	 * wheel can take
	 */
	public int[] getRelativeCompensation(int[] tilt) {
		int[] difference = new int[4];
		for (int i = 0; i < 4; i++) {
			int equivalentComplement = (tilt[i] + 180) % 360, primeDif = (AngleMeasurer[i]
					.get() % 360) - tilt[i], secDif = (AngleMeasurer[i].get() % 360)
					- equivalentComplement;
			System.out.println("Calculated setAngle-firstPossibleAngle: "
					+ primeDif);
			System.out.println("Calculated setAngle-secondPossibleAngle: "
					+ secDif);
			if ((360 - primeDif) < primeDif)
				primeDif = 360 - primeDif;
			if ((360 - secDif) < secDif)
				secDif = 360 - secDif;
			if (Math.abs(secDif) % 180 > Math.abs(primeDif) % 180) {
				difference[i] = -((AngleMeasurer[i].get() % 360) - tilt[i]) % 180;
			} else {
				difference[i] = -((AngleMeasurer[i].get() % 360) - (tilt[i] + 180)) % 180;
			}
		}
		return difference;
	}

	/*
	 * @param tilt[] the adjustments that are to be made relative to the current
	 * tilt of drive motors Note: Since this is constantly being called by an
	 * iterative teleop loop this will work but there may need to be
	 * compensation for communication latency.
	 */
	public void rotateDriveMotorRelative(int[] tilt) {
		updateTick = tilt;
		for (int i = 0; i < 4; i++) {
			if (Math.abs(tilt[i]) > 0)
				AngleMotors[i].set(getSecureAccel(tilt[i]));
		}
	}

	/*
	 * Converts absolute coordinates to relative coordinates and calls relative
	 * adjustment
	 */
	public void rotateDriveMotorAbsolute(int[] tilt) {
		rotateDriveMotorRelative(getRelativeCompensation(tilt));
	}

	/*
	 * Scales the adjustment of motor speeds to adjust for communication latency
	 * and not to force into a PID loop
	 */
	private double getSecureAccel(int input) {
		return accelerationConstant * (Math.abs(input) / input)
				* Math.sin((double) 90 - (2 * input) / 90);
	}
}
