package org.usfirst.frc.team3238.robot;
import java.util.*;
public class Debugger {
	RobotWhisperer rw;
	
	ArrayList<String> names;
	ArrayList<Object> values;
	
	public Debugger() {
		initVariables();
		rw = new RobotWhisperer(names, values);
	}
	public void initVariables() {
		
	}
	public double getDouble(String s) {
		return (double) values.get(names.indexOf(s));
	}
	public void setDouble(String s, double d) {
		values.set(names.indexOf(s), d);
	}
	public int getInt(String s) {
		return (int) values.get(names.indexOf(s));
	}
	public void setInt(String s, int i) {
		values.set(names.indexOf(s), i);
	}
	
}
