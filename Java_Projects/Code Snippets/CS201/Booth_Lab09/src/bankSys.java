import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class bankSys implements ActionListener, KeyListener
{
	//Variable Declarations
	private JFrame win;
	private int numCount = 0;
	private JPanel north = new JPanel();
	private JPanel west = new JPanel();
	private JPanel east = new JPanel();
	private JPanel center = new JPanel();
	private JPanel south = new JPanel();
	private JLabel acc = new JLabel("Account Number: ");
	private JLabel text = new JLabel("Please enter required fields: ");
	private JLabel alias = new JLabel("Name: ");
	private JLabel bal = new JLabel("Balance: ");
	private JLabel ir = new JLabel("Interest Rate: ");
	private JLabel word = new JLabel("Word Count: " + numCount);
	private JTextField account = new JTextField();
	private JTextField name = new JTextField();
	private JTextField balance = new JTextField();
	private JTextField interest = new JTextField();
	private JButton button[];
	private String buttons[] = {"Submit", "Clear", "Quit"};
	
	public Bank a;
	//bankSys constructor
	public bankSys()
	{
		win = new JFrame("Banking System");		
		win.setPreferredSize(new Dimension(500,300));
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//Build method
	public void build()
	{
		addComponents();
		setLayout();
	}
	//addComponents()
	public void addComponents()
	{
		button = new JButton[buttons.length];
		for(int i = 0; i < button.length; i++)
		{
			button[i] = new JButton(buttons[i]);
			button[i].setActionCommand(buttons[i]);
			button[i].addActionListener(this);
		}
		for(int i = 0; i < button.length; i++)
		{
			south.add(button[i]);
		}
		acc.addKeyListener(this);
		north.add(text);
		north.setLayout(new GridLayout(1,1, 5, 50));
		east.add(word);
		west.add(acc);
		west.add(alias);
		west.add(bal);
		west.add(ir);
		center.add(account);
		center.add(name);
		center.add(balance);
		center.add(interest);
		win.getContentPane().add(north, "North");
		win.getContentPane().add(west, "West");
		win.getContentPane().add(east, "East");
		win.getContentPane().add(center, "Center");
		win.getContentPane().add(south, "South");
		
	}
	//setLayout()
	public void setLayout()
	{
		west.setLayout(new GridLayout(4, 1, 0, 35));
		center.setLayout(new GridLayout(4, 1, 0, 35));
	}
	//show()
	public void show()
	{
		win.pack();
		win.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if(command == "Submit")
		{
			//a = new Bank();
		}
		else if(command == "Clear")
		{
			account.setText("");
			name.setText("");
			balance.setText("");
			interest.setText("");
		}
		else if(command == "Quit")
		{
			System.exit(0);
		}
	}
	public void keyPressed(KeyEvent e) 
	{
		
	}
	public void keyReleased(KeyEvent e) 
	{
		
	}
	public void keyTyped(KeyEvent e) 
	{
		if(e.equals(Event.BACK_SPACE) || e.equals(Event.DELETE))
		{
			numCount--;
		}
		else
		{
			numCount++;
		}
	}
	//main
	public static void main(String[] args)
	{
		bankSys win = new bankSys();
		win.build();
		win.show();
	}
}
