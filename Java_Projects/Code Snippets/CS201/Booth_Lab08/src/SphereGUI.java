import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class SphereGUI implements ActionListener, MouseListener
{	//JFrame
	private JFrame win;
	//JPanels
	private JPanel south = new JPanel();
	private JPanel center = new JPanel();
	//Color
	public Color colorA;
	public Color colorDefault = Color.BLACK;
	//JButton array
	private JButton[] button;
	//String Array for Buttons
	private String[] buttonNames = {"Red", "Green", "Blue"};
	//Constructor SphereGUI
	public SphereGUI()
	{
		win = new JFrame("CS 302 Lab08 P3 - MedivalJ");
		win.setLayout(new BorderLayout());
		win.setPreferredSize(new Dimension(500,500));
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//Build() - calls methods which assemble different parts of GUI
	public void build()
	{
		addComponents();
		setLayout();
	}
	//addComponents - adds the components
	private void addComponents()
	{
		button = new JButton[buttonNames.length];
		for(int i = 0; i < button.length; i++)
		{
			button[i] = new JButton(buttonNames[i]);
			button[i].setActionCommand(buttonNames[i]);
			button[i].addActionListener(this);
		}
		for(int i = 0; i < button.length; i++)
		{
			south.add(button[i]);
		}
		center.setBackground(Color.WHITE);
		center.setSize(500,500);
		center.setLayout(new BorderLayout());
		win.add(center, "Center");
		win.add(south, "South");
	}
	//setLayout() - sets the layout of panels
	private void setLayout()
	{
		south.setLayout(new GridLayout(1,2));
	}
	//show() - shows the GUI(i.e. set it to be visible)
	public void show()
	{
		win.pack();
		win.setVisible(true);
	}
	//Listener method
	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		for(int i = 0; i < button.length; i++)
		{
			if(command.equals("Red"))
			{
				colorA = Color.RED;
			}
			else if(command.equals("Green"))
			{
				colorA = Color.GREEN;
			}
			else if(command.equals("Blue"))
			{
				colorA = Color.BLUE;
			}
		}
	}		
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		Sphere// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	//Main method
	public static void main(String[] args)
	{
		SphereGUI win = new SphereGUI();
		win.build();
		win.show();
	}
}