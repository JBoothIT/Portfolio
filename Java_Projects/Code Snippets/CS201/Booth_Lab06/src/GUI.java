import java.awt.*;
import java.util.Date;
import javax.swing.*;
import java.awt.event.*;
public class GUI implements ActionListener
{
	private static int width = 500;
	private static int height = 200;
	private JFrame win;
	private JLabel label;
	private JButton ColorB[];
	private final static String[] ColorButton = {"Red", "Orange", "Blue", "Green"};

	public GUI()
	{
		win = new JFrame("CS 302 Lab 6 In-Class P1 - MedivalJ");
		label = new JLabel("Time: " + new Date().toString());
		label.setFont(label.getFont().deriveFont(new Float(20.0)));
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ColorB = new JButton[ColorButton.length];
		for(int i = 0; i < ColorB.length; i++)
		{
			ColorB[i] = new JButton(ColorButton[i]);
			ColorB[i].setActionCommand(ColorButton[i]);
			ColorB[i].addActionListener(this);
		}
	}
	public void build()
	{
		win.setPreferredSize(new Dimension(width, height));	
		win.getContentPane().add(ColorB[0], BorderLayout.NORTH);
		win.getContentPane().add(ColorB[1], BorderLayout.SOUTH);
		win.getContentPane().add(ColorB[2], BorderLayout.WEST);
		win.getContentPane().add(ColorB[3], BorderLayout.EAST);
		win.getContentPane().add(label, BorderLayout.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
	}
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if(command.equals("Red"))
		{
			label.setForeground(Color.RED);
		}
		else if(command.equals("Orange"))
		{
			label.setForeground(Color.ORANGE);
		}
		else if(command.equals("Blue"))
		{
			label.setForeground(Color.BLUE);
		}
		else if(command.equals("Green"))
		{
			label.setForeground(Color.GREEN);
		}
	}
	public void display()
	{
		win.pack();
		win.setVisible(true);
	}
	public static void main(String[] args)
	{
		GUI win = new GUI();
		win.build();
		win.display();
	}
	
}
