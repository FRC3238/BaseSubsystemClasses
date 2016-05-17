package org.usfirst.frc.team3238.robot;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class RobotWhisperer extends JFrame {

	JTextField[] pigeons, complimentaryPigeons;
	JTextField jtfText1, jtfUneditableText;
	String disp = "";
	TextHandler handler = null;
	ArrayList<String> names;
	ArrayList<Object> values;
	public RobotWhisperer(ArrayList<String> names, ArrayList<Object> values) {
		super("Robot Value Reader/Writer");

		this.names = names;
		this.values = values;
		
		pigeons = new JTextField[names.size()];
		complimentaryPigeons = new JTextField[names.size()];
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		for(int i = 0; i < pigeons.length; i++) {
			complimentaryPigeons[i] = new JTextField(names.get(i), 10);
			pigeons[i] = new JTextField(""+values.get(i),10);
		}
		jtfText1 = new JTextField(10);
		container.add(jtfText1);
		container.add(jtfUneditableText);
		handler = new TextHandler();
		jtfText1.addActionListener(handler);
		jtfUneditableText.addActionListener(handler);
		setSize(325, 100);
		setVisible(true);
	}
	
	private class TextHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jtfText1) {
				disp = "text1 : " + e.getActionCommand();
			} else if (e.getSource() == jtfUneditableText) {
				disp = "text3 : " + e.getActionCommand();
			}
			JOptionPane.showMessageDialog(null, disp);
		}
	}
}