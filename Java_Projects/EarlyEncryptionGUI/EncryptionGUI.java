package Encryption;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Jeremy Booth
 * Spare Time project 
 * year: 2014
 * 
 * Creates a GUI window which contains buttons, textboxes and scrollwheels. 
 * The program takes a string from the user and performs a rudementary operation converting the text into an "encrypted" form.
 * The operation is somewhat remeniscent to that of a caesar cipher but works by simply shifting ASCII characters by a factor of 50. 
 * Does not use En(x) = (x+n)mod 26 or Dec(x) = (x+n) mod 26.
 *
 */
public class EncryptionGUI implements ActionListener
{
	private JFrame win;
	private JPanel center = new JPanel();
	private JPanel west = new JPanel();
	private JPanel east = new JPanel();
	private JPanel north = new JPanel();
	private JPanel south = new JPanel();
	private JLabel encryption = new JLabel("Encrypt: ");
	private JLabel decryption = new JLabel("Decrypt: ");
	private JLabel color = new JLabel("Color: ");
	private JLabel font = new JLabel("Font: ");
	private JTextField encrypt = new JTextField();
	private JTextField decrypt = new JTextField();
	private JTextArea result = new JTextArea();
	private final String[] buttons = {"Encrypt(Press me you know you want to...)", "Decrypt(No! Press me I have candy!)"};
	private JButton[] button = new JButton[buttons.length];
	private List colorChanger = new List();
	private List fontChanger = new List();
	private String[] fontOptions = {"8", "12", "15", "20", "25", "50"};
	private String[] colorOptions = {"Black","Red", "Blue","Green","Orange"};;
	private long Start = 0;
	private long Stop = 0;
	private boolean running = false;
	private JLabel secs = new JLabel("Secs: ");
	private JLabel milli = new JLabel("MilliSecs: ");
	private JTextField seconds = new JTextField();
	private JTextField milliSeconds = new JTextField();
	public EncryptionGUI()
	{
		win = new JFrame("Encryption Interface");
		win.setPreferredSize(new Dimension(700, 500));
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLayout(new BorderLayout());
	}
	public void start()
	{
		this.Start = System.currentTimeMillis();
		this.running = true;
	}
	public void stop()
	{
		this.Stop = System.currentTimeMillis();
		this.running = false;
	}
	/*public long getTimeNanosecs()
	{
		long time;
		if(running)
		{
			time = ((System.currentTimeMillis() - Start) * 1000);
		}
		else
		{
			time = (Stop - Start);
		}
		return time;
	}*/
	/*public long getTimeMillisecs()
	{
		long time;
		if(running)
		{
			time = (System.currentTimeMillis() - Start);
		}
		else
		{
			time = (Stop - Start);
		}
		return time;
	}*/
	/*public long getTimeSecs()
	{
		long time;
		if(running)
		{
			time = ((System.currentTimeMillis() - Start)/ 1000);
		}
		else
		{
			time = ((Stop - Start) / 1000);
		}
		return time;
	}*/
	public void build()
	{
		for(int i = 0; i < button.length; i++)
		{
			button[i] = new JButton(buttons[i]);
			button[i].setActionCommand(buttons[i]);
			button[i].addActionListener(this);
		}
		for(int i = 0; i < colorOptions.length;i++)
		{
			colorChanger.add(colorOptions[i]);
			colorChanger.addActionListener(this);
		}
		for(int i = 0; i < fontOptions.length; i++)
		{
			fontChanger.add(fontOptions[i]);
			fontChanger.addActionListener(this);
		}
		east.add(color);
		east.add(colorChanger);
		east.add(font);
		east.add(fontChanger);
		east.setLayout(new GridLayout(4, 1, 60, 1));
		north.add(encryption);
		north.add(encrypt);
		north.add(button[0]);
		north.add(decryption);
		north.add(decrypt);
		north.add(button[1]);
		north.setLayout(new GridLayout(6, 1, 5, 5));
		center.add(result);
		center.setLayout(new GridLayout(1, 1, 30, 50));
		south.add(secs);
		south.add(seconds);
		south.add(milli);
		south.add(milliSeconds);
		south.setLayout(new GridLayout(1, 1, 5, 5));
		win.getContentPane().add(center, "Center");
		win.getContentPane().add(north, "North");
		win.getContentPane().add(south, "South");
		win.getContentPane().add(east, "East");
		win.getContentPane().add(west, "West");
	}
	public void display()
	{
		win.pack();
		win.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		if(command == "Encrypt(Press me you know you want to...)")
		{
			result.setText(null);
			String text = encrypt.getText();
			Start = System.currentTimeMillis();
			result.setText(encode(text));
			Stop = System.currentTimeMillis();
			seconds.setText(String.valueOf((double)(Stop - Start)* 1000));
			milliSeconds.setText(String.valueOf((double)Stop - Start));
		}
		if(command == "Decrypt(No! Press me I have candy!)")
		{
			result.setText(null);
			
			String text = decrypt.getText();
			Start = System.currentTimeMillis();
			result.setText(decode(text));
			Stop = System.currentTimeMillis();
			/*stop();*/
			seconds.setText(String.valueOf((double)(Stop - Start) * 1000));
			milliSeconds.setText(String.valueOf((double)Stop - Start));
		}
		if(colorChanger.getSelectedItem() == "Black")
		{
			result.setForeground(Color.BLACK);
		}
		if(colorChanger.getSelectedItem() == "Blue")
		{
			result.setForeground(Color.BLUE);
		}
		if(colorChanger.getSelectedItem() == "Red")
		{
			result.setForeground(Color.RED);
		}
		if(colorChanger.getSelectedItem() == "Green")
		{
			result.setForeground(Color.GREEN);
		}
		if(colorChanger.getSelectedItem() == "Orange")
		{
			result.setForeground(Color.ORANGE);
		}
		if(fontChanger.getSelectedItem() == "8")
		{
			result.setFont(result.getFont().deriveFont(new Float(8.0)));
		}
		if(fontChanger.getSelectedItem() == "12")
		{
			result.setFont(result.getFont().deriveFont(new Float(12.0)));
		}
		if(fontChanger.getSelectedItem() == "15")
		{
			result.setFont(result.getFont().deriveFont(new Float(15.0)));
		}
		if(fontChanger.getSelectedItem() == "20")
		{
			result.setFont(result.getFont().deriveFont(new Float(20.0)));
		}
		if(fontChanger.getSelectedItem() == "25")
		{
			result.setFont(result.getFont().deriveFont(new Float(25.0)));
		}
		if(fontChanger.getSelectedItem() == "50")
		{
			result.setFont(result.getFont().deriveFont(new Float(50.0)));
		}
	}
	private String encode(String text)
	{
		/*start();*/
		char[] characters = new char[text.length()]; 
		int[] a = new int[characters.length];
		String txt = "";
		for(int i = 0; i < text.length(); i++)
		{
			characters[i] = text.charAt(i);
			a[i] = (int)characters[i] + 50;
			txt += (char)a[i];
		}
		return txt;
	}
	private String decode(String text)
	{
		/*start();*/
	char[] characters =new char[text.length()];
	int[] a = new int[characters.length];
	String txt = "";
		for(int i = 0; i < text.length(); i++)
		{
			characters[i] = text.charAt(i);
			a[i] = (int)characters[i] - 50;
			txt += (char)a[i];
		}
		return txt;
	}
	public static void main(String[] args)
	{
		EncryptionGUI a = new EncryptionGUI();
		a.build();
		a.display();
	}
}