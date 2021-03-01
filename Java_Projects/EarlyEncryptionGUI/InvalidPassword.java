package Encryption;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Jeremy Booth
 * Spare Time project 
 * year: 2014
 * 
 * Creates a GUI window which displays an error message with a close button.
 *
 */

public class InvalidPassword implements ActionListener
{
	private JFrame win;
	private JPanel center = new JPanel();
	private JLabel text = new JLabel("Invalid Password! Terminating Program!");
	private JButton ok = new JButton("OK");
	private JPanel south = new JPanel();
	public InvalidPassword()
	{
		win = new JFrame("Warning: Invalid Password!");
		win.setPreferredSize(new Dimension(500, 100));
		win.setLayout(new BorderLayout());
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void build()
	{
		text.setFont(text.getFont().deriveFont(new Float(20.0)));
		center.add(text);
		ok.addActionListener(this);
		ok.setPreferredSize(new Dimension(0, 20));
		south.setLayout(new GridLayout(1, 1));
		south.add(ok);
		win.add(center, "Center");
		win.add(south, "South");
	}
	public void display()
	{
		win.pack();
		win.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if(command.equals("OK"))
		{
			System.exit(1);
		}
	}
}
