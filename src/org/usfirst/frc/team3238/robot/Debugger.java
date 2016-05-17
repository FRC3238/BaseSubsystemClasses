package org.usfirst.frc.team3238.robot;
import java.util.HashMap;
public class Debugger {
	RobotWhisperer rw;
	
	HashMap<String, Object> varAssignments = new HashMap<String, Object>();
	public Debugger() {
		rw = new RobotWhisperer();
	}
	public double getDouble(String s) {
		return (double) varAssignments.get(s);
	}
	public void setDouble(String s, double d) {
		varAssignments.put(s, d);
	}
	public int getInt(String s) {
		return (int) varAssignments.get(s);
	}
	public void setInt(String s, int i) {
		varAssignments.put(s, i);
	}
	
}
