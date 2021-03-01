import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
public class Grid2 implements ActionListener
{
	private JFrame win;
	private JLabel label;
	private JPanel panels[];
	private JButton colorButton[];
	private String[] names = {"Red", "Green", "Blue", "Orange", "Reset", "Quit"};
	private JButton button[];
	private Color colorA;
	private Color colorB;
	public Grid2()
	{
		//JFrame
		win = new JFrame();
		//Label
		label = new JLabel(new Date().toString(), 0);
		//panels 
		panels = new JPanel[5];
		//color
		colorButton = new JButton[names.length];
		//buttons
		button = new JButton[64];
		
		for(int i = 0; i < names.length; i++)
		{
			colorButton[i] = new JButton(names[i]);
			colorButton[i].setActionCommand(names[i]);
			colorButton[i].addActionListener(this);
		}
		colorA = colorButton[5].getBackground();
		colorB = colorButton[5].getBackground();
		
		win.setDefaultCloseOperation(3);
		win.setPreferredSize(new Dimension(500,500));
		win.setLayout(new BorderLayout());
	}
	public void build()
	{
		//label
		panels[0].add(label);
		//west
		panels[1].add(colorButton[0], colorButton[1]);//red//green
		//east
		panels[2].add(colorButton[2], colorButton[3]);//blue//orange
		//south
		panels[3].add(colorButton[4], colorButton[5]);//reset//quit

		for(int i = 0; i < colorButton.length; i++)
		{
			colorButton[i].setActionCommand(colorButton[i].getText());
		}
		for(int i = 0; i < colorButton.length; i++)
		{
			colorButton[i].addActionListener(this);
		}		
		for(int i = 0; i < button.length; i ++)
		{
			panels[4].add(this.button[i] = new JButton("" + i));
			button[i].setActionCommand(button[i].getText());
			button[i].addActionListener(this);
		}
		panels[0].setLayout(new GridLayout(1,1));
		panels[1].setLayout(new GridLayout(1,2));
		panels[2].setLayout(new GridLayout(2,1));
		panels[3].setLayout(new GridLayout(2,1));
		panels[4].setLayout(new GridLayout(8,8));
		
		win.add(panels[0], "North");
		win.add(panels[1], "West");
		win.add(panels[2], "East");
		win.add(panels[3], "South");
		win.add(panels[4], "Center");
	}
	private void colorChanger(ActionEvent e)
	{
		((JButton)e.getSource()).setBackground(colorA);
	}
	private void reset()
	{
		for(int i = 0; i < button.length; i++)
		{
			button[i].setBackground(colorB);
		}
	}
	public void display()
	{
		win.pack();
		win.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		if(command.equals("Red"))
		{
			
		}
		else if(command.equals("Green"))
		{
			
		}
		else if(command.equals("Blue"))
		{
			
		}
		else if(command.equals("Orange"))
		{
			
		}
		else if(command.equals("Reset"))
		{
			
		}
		else if(command.equals("Quit"))
		{
			System.exit(0);
		}
	}
	public static void main(String[] args)
	{
		Grid2 win = new Grid2();
		win.build();
		win.display();
	}
}
