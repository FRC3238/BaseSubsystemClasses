package org.usfirst.frc.team3238.robot;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class RobotWhisperer extends JFrame {

	public static final int FRAME_HEIGHT = 100, FRAME_WIDTH = 325;
	JTextField[] identifierValues;
	JTextField jtfText1, jtfUneditableText;
	String disp = "";
	TextHandler handler = null;
	ArrayList<String> names;
	ArrayList<Object> values;
	
	public static boolean update = false;
	
	public RobotWhisperer(ArrayList<String> names, ArrayList<Object> values) {
		super("Robot Value Reader/Writer");

		this.names = names;
		this.values = values;
		
		identifierValues = new JTextField[names.size()];
		
		Container container = getContentPane();
		container.setLayout(new FlowLayout());

		handler = new TextHandler();
		
		for(int i = 0; i < identifierValues.length; i++) 
			identifierValues[i] = new JTextField(names.get(i) + ": " + values.get(i), 10);
			
		for(JTextField j : identifierValues) {
			container.add(j);
			j.addActionListener(handler);
		}
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setVisible(true);
	}
	
	public boolean hasUpdate() {
		return update;
	}
	public Object getUpdate(String id) {
		return values.get(values.indexOf(id));
	}
	private class TextHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < identifierValues.length; i++)
				if(identifierValues[i] == e.getSource()) {
					try{ 
						values.set(i, e.getActionCommand());
						update = true;
						identifierValues[i].setText(names.get(i) + ": " + e.getActionCommand());
					}catch(Exception x) {
						JOptionPane.showMessageDialog(null, "invalid argument");
					}
				}
			JOptionPane.showMessageDialog(null, disp);
		}
	}
}
