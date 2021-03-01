package Encryption;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Jeremy Booth
 * Spare Time project 
 * year: 2014
 * 
 * My earliest attempt at creating a login GUI in java.
 *
 */

public class Login implements ActionListener 
{
	private JFrame win;
	private JPanel north = new JPanel();
	private JTextField password = new JTextField();
	private String[] buttonText = {"Enter"};
	private JButton[] buttons = new JButton[buttonText.length];
	private String currPass = "password";
	private JTextArea text = new JTextArea("Please enter password. \nProgram will perform next set of operations once password is entered. \nIf password is incorrect then program will terminate.");
	private JPanel center = new JPanel();
	private EncryptionGUI a = new EncryptionGUI();
	private InvalidPassword b = new InvalidPassword();
	public Login()
	{
		win = new JFrame("Login");
		win.setPreferredSize(new Dimension(500, 200));
		win.setLayout(new BorderLayout());
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void build()
	{
		for(int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JButton(buttonText[i]);
			buttons[i].setActionCommand(buttonText[i]);
			buttons[i].addActionListener(this);
		}
		north.add(password);
		north.add(buttons[0]);
		north.setLayout(new GridLayout(2, 1, 5, 5));
		win.add(north, "North");
		text.setEditable(false);
		text.setForeground(Color.BLUE);
		center.add(text);
		center.setLayout(new GridLayout(1, 1));
		win.add(center, "Center");
	}
	public void show()
	{
		win.pack();
		win.setVisible(true);
	}
	private boolean passChecker()
	{
		String text = password.getText();
		if(text.length() != currPass.length())
		{
			return false;
		}
		for(int i = 0; i < text.length(); i++)
		{
				if(text.charAt(i) != currPass.charAt(i))
				{
					return false;
				}
				else
					continue;
		}
		return true;
	}
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if(command.equals("Enter"))
		{
			if(passChecker())
			{
				a.build();
				a.display();
				win.dispose();
			}
			if(passChecker() == false)
			{
				b.build();
				b.display();
				win.dispose();
			}
		}
	}
	public static void main(String[] args)
	{
		Login a = new Login();
		a.build();
		a.show();
	}
}