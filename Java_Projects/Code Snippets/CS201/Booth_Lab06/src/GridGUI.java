import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
public class GridGUI 
{
	private JFrame win;
	private JButton color;
	private JButton numbers;
	private JLabel label;
	private final static String[] colorB = {"Red", "Green", "Blue", "Orange"};
	private final static String[] com = {"Reset", "Quit"};
	public GridGUI()
	{
		this.win = new JFrame("CS 302 Lab 6 P3");
		this.label = new JLabel(new Date().toString());
		this.label.setFont(label.getFont().deriveFont(new Float(20.0)));
	}
	public void build()
	{
		//win.getContentPane().add()
	}
	public void display()
	{
		win.pack();
		win.setVisible(true);
	}
	public static void main(String[] args)
	{
		GridGUI win = new GridGUI();
		win.build();
		win.display();
	}
}
