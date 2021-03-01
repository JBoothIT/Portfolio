import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
@SuppressWarnings("serial")
public class InvalidAccountException extends Exception implements ActionListener
{
	private JFrame win = new JFrame("Error!");
	private JPanel west = new JPanel();
	private JPanel east = new JPanel();
	private JPanel center = new JPanel();
	private JPanel north = new JPanel();
	private JPanel south = new JPanel();
	private JLabel label = new JLabel("Warning: " + getMessage());
	private JButton ok = new JButton("Ok");
	public InvalidAccountException()
	{
	}
	public String getMessage()
	{
		return "Invalid Account Number!";
	}
	public void error()
	{
		ok.setActionCommand("Ok");
		ok.addActionListener(this);
		south.add(ok);
		label.setFont(label.getFont().deriveFont(new Float(20.0)));
		center.add(label);
		center.setLayout(new FlowLayout());
		win.getContentPane().add(north, "North");
		win.getContentPane().add(east, "East");
		win.getContentPane().add(west, "West");
		win.getContentPane().add(center, "Center");
		win.getContentPane().add(south, "South");
		win.setPreferredSize(new Dimension(500, 200));
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.pack();
		win.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if(command == "Ok")
		{
			System.exit(0);
		}
	}
}
