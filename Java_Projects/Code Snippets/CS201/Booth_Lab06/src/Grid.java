import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Grid implements ActionListener
{
	/*JFrame*/
	private JFrame win;
	/*JLabel*/
	private JLabel clock;
	/*panels*/
	private JPanel up;
	private JPanel down;
	private JPanel right;
	private JPanel left;
	private JPanel center;
	/*buttons*/
	private  JButton red;
	private JButton blue;
	private JButton green;
	private JButton orange;
	private JButton reset;
	private JButton quit;
	private JButton[] buttonNum;
	/*color*/
	private Color colorA;
	private Color colorB;
	
	//Grid constructor creates new components, sets the color defaults, and sets the size and layout of the gui window.
	public Grid()
	{	
		//win is assigned a new JFrame
		win = new JFrame("CS 302 Lab 6 In-Class P3- MedivalJ");
		//clock receives a new JLabel with a new date
		clock = new JLabel(new Date().toString(), 0);
		//JPanel assignment
		up = new JPanel();
		down = new JPanel();
		right = new JPanel();
		left = new JPanel();
		center = new JPanel();
		//JButton assignment
		red = new JButton("Red");
		blue = new JButton("Blue");
		green = new JButton("Green");
		orange = new JButton("Orange");
		reset = new JButton("Reset");
		quit = new JButton("Quit");
		buttonNum = new JButton[64];
		//Color assignment
		colorA = reset.getBackground();
		colorB = reset.getBackground();
		//settings
		win.setDefaultCloseOperation(3);
		win.setPreferredSize(new Dimension(600, 600));
		win.setLayout(new BorderLayout());	
	}
	/**@param adds the components, sets the listeners and actions to be performed and sets the layout of the GUI*/
	public void build()
	{
		addComponents();
		setActions();
		setLayout();
	}
	/**@param adds the components*/
	public void addComponents()
	{
		//Add components to panels
		up.add(clock);
		down.add(reset);
		down.add(quit);
		right.add(blue);
		right.add(orange);
		left.add(red);
		left.add(green);
		//Center
		for(int i = 0; i < buttonNum.length; i++)
		{
			center.add((this.buttonNum[i] = new JButton("" + i)));
			buttonNum[i].setActionCommand(buttonNum[i].getText());
			buttonNum[i].addActionListener(this);
		}
		//add panels to JFrame win
		win.add(up, "North");
		win.add(down, "South");
		win.add(right, "East");
		win.add(left, "West");
		win.add(center,"Center");
	}
	/**@param sets and adds the ActionCommand and ActionListeners to each component*/
	public void setActions()
	{
		//Set action commands
				reset.setActionCommand(reset.getText());
				quit.setActionCommand(quit.getText());
				blue.setActionCommand(blue.getText());
				orange.setActionCommand(orange.getText());
				red.setActionCommand(red.getText());
				green.setActionCommand(green.getText());
				//add Listeners
				reset.addActionListener(this);
				quit.addActionListener(this);
				blue.addActionListener(this);
				orange.addActionListener(this);
				red.addActionListener(this);
				green.addActionListener(this);
	}
	/**@param sets the layout of the each of the components*/
	public void setLayout()
	{
		//Set layout
		up.setLayout(new GridLayout(1,1));
		down.setLayout(new GridLayout(1,2));
		right.setLayout(new GridLayout(2,1));
		left.setLayout(new GridLayout(2,1));
		center.setLayout(new GridLayout(8,8));
	}
	//changes the color of the the selected button
	private void colorChanger(ActionEvent e)
	{
		((JButton)e.getSource()).setBackground(colorA);
	}
	//resets all the numbered buttons to their default color.
	private void reset()
	{
		for(int i = 0; i < buttonNum.length; i++)
		{
			buttonNum[i].setBackground(colorB);
		}
	}
	//Sets listeners perform the appropriate actions of whichever button is pressed
	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		if(command.equals("Red"))
		{
			colorA = Color.RED;
		}
		else if(command.equals("Blue"))
		{
			colorA = Color.BLUE;
		}
		else if(command.equals("Green"))
		{
			colorA = Color.GREEN;
		}
		else if(command.equals("Orange"))
		{
			colorA = Color.ORANGE;	
		}
		else if(command.equals("Reset"))
		{
			colorA = colorB;
			reset();
		}
		else if(command.equals("Quit"))
		{
			System.exit(0);
		}
		else
		{
			for(int i = 0; i < buttonNum.length; i++)
			{
				if(command.equals(buttonNum[i].getText()))
				{
					colorChanger(e);
				}
			}
		}
		if(colorA != reset.getBackground())
		{
			System.out.println(command);
			clock.setForeground(colorA);
		}
	}
	//displays the GUI
	public void display()
	{
		win.pack();
		win.setVisible(true);
	}
	public static void main(String[] args)
	{
		Grid win = new Grid();
		win.build();
		win.display();
	}
}	