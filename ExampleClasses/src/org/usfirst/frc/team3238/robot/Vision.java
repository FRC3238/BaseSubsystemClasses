package org.usfirst.frc.team3238.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Timer;

public class Vision {
	NetworkTable[] GRIPpipes;
	
	private boolean singlePipeline;

	private Logger log = new Logger();
	
	/* 3D Array where first is pipeline number, second is listed type,
	 * third is amount of values
	 * */
	double[][][] visionData; 
	
	private int x=0,y=1,area=2,height=3,width=4;

	private int threshold = 5;
	
	private double trackIteratorX, trackIteratorY;

	private Timer tempTimer = new Timer();
	
	public Vision() {
		this(new String[]{"myContoursReport"});
	}
	
	public Vision(String[] tableInputs){
		GRIPpipes = new NetworkTable[tableInputs.length];
		for(int i = 0; i < tableInputs.length; i++) 
			GRIPpipes[i] = NetworkTable.getTable("GRIP/"+tableInputs[i]);
		singlePipeline = GRIPpipes.length == 1;		
		visionData = new double[GRIPpipes.length][5][];
	}
	
	public void checkConfliction() {
		for(int i = 0; i < visionData.length;i++) {
			updateArray(i, x);
			if(visionData[i][x].length != 1) {
				log.warn("Invalid Contours for pipe " + i + " with amount " + visionData[i][x].length);
			}
		}
	}
	/*
	 * Intended to track the same Contour, WIP
	 * */
	public void trackContour(int pipe) {
		updateArray(pipe, x);
		updateArray(pipe, y);
		
		trackIteratorX = visionData[pipe][x][0];
		trackIteratorY = visionData[pipe][x][0];
	
	}
	private void updateArray(int pipe, int type){
		visionData[pipe][type] = GRIPpipes[pipe].getNumberArray(getType(type), new double[]{0.0});
	}
	private String getType(int type) {
		switch(type) {
		case 0:
			return "centerX";
		case 1:
			return "centerY";
		case 2:
			return "area";
		case 3:
			return "height";
		case 4:
			return "width";
		default:
			throw new IllegalArgumentException();
		}
	}
}
