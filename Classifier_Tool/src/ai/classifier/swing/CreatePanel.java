package swing;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;


/**
 * @author Chirag Tayal
 * @Date 07/05/2012
 * @Description 
 */
public class CreatePanel extends JPanel implements ActionListener{
	
	public static JFrame frame = new JFrame("Classifier");
	
	public static JLabel label1, label2,label3, label4, label5, label7;
	public static JButton button1, button2,button3, refresh;
	JTextArea textArea1;
	public static JTextField textField1,textField2,textField3;
	public static JComboBox comboBox1,comboBox2;
	public static JScrollPane scrollar;
	public static String splitPercentage, algoName;
	
	
	public void addComponenet(Container pane){
		
		pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		pane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		
		refresh = new JButton("Reset");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 50;
		gbc.gridy = 350;
		pane.add(refresh,gbc);
		refresh.addActionListener(this);
		
		label1 = new JLabel("File Name");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 50;
		gbc.gridy = 0;
		pane.add(label1,gbc);
		
		
		textField1 = new JTextField();textField1.setEditable(false);
		gbc.weightx = 100; 
		gbc.gridx = 80;
		gbc.gridy = 0;
		pane.add(textField1,gbc);
		//textField1.addActionListener(this);
		
		button1 = new JButton("Upload");
		gbc.weightx = 10;
		gbc.gridx = 140;
		gbc.gridy = 0;
		pane.add(button1,gbc);
		button1.addActionListener(this);
		
		
		label4 = new JLabel("Stop Word File");
		gbc.gridx = 50;
		gbc.gridy = 80;
		pane.add(label4,gbc);
		
		
		textField2 = new JTextField();textField2.setEditable(false);
		gbc.weightx = 100; 
		gbc.gridx = 80;
		gbc.gridy = 80;
		pane.add(textField2,gbc);
		//textField1.addActionListener(this);
		
		button3 = new JButton("Upload");
		gbc.weightx = 10;
		gbc.gridx = 140;
		gbc.gridy = 80;
		pane.add(button3,gbc);
		button3.addActionListener(this);
		
		
		label2 = new JLabel("Algorithm");
		gbc.gridx = 50;
		gbc.gridy = 120;
		pane.add(label2,gbc);
		
		
		String[] nameOfAlgo = {"Select","Simple Perceptron","Multilayer Perceptron","Naive Bayes"};
		comboBox1 = new JComboBox(nameOfAlgo);
		gbc.gridx = 80;
		gbc.gridy = 120;
		pane.add(comboBox1,gbc);
		comboBox1.addActionListener(this);
		
		label3 = new JLabel("Split %");
		gbc.gridx = 50;
		gbc.gridy = 150;
		pane.add(label3,gbc);
		
		
		String[] split = {"Select","80%-20%","70%-30%","60%-40%"};
		comboBox2 = new JComboBox(split);
		gbc.gridx = 80;
		gbc.gridy = 150;
		pane.add(comboBox2,gbc);
		comboBox2.addActionListener(this);
		
		label7 = new JLabel("Iteration Count");
		gbc.gridx = 50;
		gbc.gridy = 200;
		pane.add(label7,gbc);
		
		textField3 = new JTextField();textField1.setEditable(false);
		gbc.weightx = 100; 
		gbc.gridx = 80;
		gbc.gridy = 200;
		pane.add(textField3,gbc);
		
		
		textArea1 = new JTextArea(10,10);
		textArea1.setAutoscrolls(true);
		textArea1.setLineWrap(true);
		textArea1.setEditable(false);
		textArea1.setWrapStyleWord(true);
		
		scrollar = new JScrollPane(textArea1);
		gbc.weightx = 300;
		gbc.gridx = 80;
		gbc.gridy = 300;
		pane.add(scrollar,gbc);
		
		button2 = new JButton("Run");
		gbc.weightx = 10;
		gbc.gridx = 80;
		gbc.gridy = 350;
		pane.add(button2,gbc);
		button2.addActionListener(this);
		
	}
		
	private static String fileName = "";
	public void actionPerformed(ActionEvent e){
		
		//check for the correct path in button 
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV File", ".csv");
		chooser.setFileFilter(filter);
		
//		try{			
			if(e.getSource() == button1){
				chooser.showOpenDialog(this);
//				if(chooser.isShowing()){
					fileName = chooser.getSelectedFile().getName();
					textField1.setText(chooser.getSelectedFile().getPath());
					textField1.setBorder(new LineBorder(new Color(0,0,0)));
//				}
			}
			if(e.getSource() == comboBox1){
				algoName = comboBox1.getSelectedItem().toString();
				comboBox1.setBorder(new LineBorder(new Color(0,0,0)));
			}
			if(e.getSource() == comboBox2){
				splitPercentage = comboBox2.getSelectedItem().toString();
				comboBox1.setBorder(new LineBorder(new Color(0,0,0)));
			}
			if(e.getSource() == refresh){
				textArea1.setText("");textField1.setText("");textField2.setText("");
				comboBox1.setSelectedIndex(0);comboBox2.setSelectedIndex(0);
				comboBox1.setBorder(new LineBorder(new Color(0,0,0)));comboBox2.setBorder(new LineBorder(new Color(0,0,0)));
				textField1.setBorder(new LineBorder(new Color(0,0,0)));textField2.setBorder(new LineBorder(new Color(0,0,0)));
			}
			//here it will be shooting events for actual algorithms
			if(e.getSource() == button2){
				if(textField1.getText().equals("")){
					JOptionPane.showMessageDialog(null,"Please Select .CSV File","ERROR",JOptionPane.ERROR_MESSAGE);
					textField1.setBorder(new LineBorder(new Color(255,0,0)));
				}
				else if(!textField1.getText().endsWith(".csv")){
					JOptionPane.showMessageDialog(null,"Please Select Only .CSV File","ERROR",JOptionPane.ERROR_MESSAGE);
					textField1.setBorder(new LineBorder(new Color(255,0,0)));
				//	throw new Exception();
				}
				else if(comboBox1.getSelectedItem() == "Select" ||comboBox2.getSelectedItem() == "Select" ){
					JOptionPane.showMessageDialog(null,"Please Select Algorithm/Split ","ERROR",JOptionPane.ERROR_MESSAGE);
					comboBox1.setBorder(new LineBorder(new Color(255,0,0)));
					comboBox2.setBorder(new LineBorder(new Color(255,0,0)));
				}
				else if(!textField3.getText().matches("[0-9].*")){
					JOptionPane.showMessageDialog(null,"Iteration Count should be a number","ERROR",JOptionPane.ERROR_MESSAGE);
				}
				else{
					textArea1.setText("Started reading "+fileName+" file \n");
					Next csv = new Next(textField1.getText(),textArea1, algoName, splitPercentage, textField3.getText());
					
				}
			}
//		}catch (Exception i) {
//			JOptionPane.showMessageDialog(null,"","ERROR",JOptionPane.ERROR_MESSAGE);
//		}
		
	}
	
	
	
	public static void main(String args[])
	{
		frame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		new CreatePanel().addComponenet(frame.getContentPane());
		frame.pack();
		frame.setResizable(false);
		frame.setSize(800,340);
		frame.setVisible(true);
	
	}
	
	
	

}
