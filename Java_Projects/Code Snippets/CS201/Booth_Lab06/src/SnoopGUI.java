import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class SnoopGUI implements ActionListener
{
	public JFrame win;
	public int width = 500;
	public int height = 400;
	public JTextField txt;
	public JTextArea txtArea;
	
	public SnoopGUI()
	{	
		this.win = new JFrame("CS 302 Lab 6 In-Class P2 - MedivalJ");
		this.txt = new JTextField();
		this.txtArea = new JTextArea();
	}
	public void build()
	{
		txt.addActionListener(this);
		this.win.setPreferredSize(new Dimension(width, height));
		this.win.getContentPane().add(txt, BorderLayout.NORTH);
		this.win.getContentPane().add(txtArea, BorderLayout.CENTER);
		this.txtArea.setEditable(false);
	}
	public void display()
	{
		win.pack();
		win.setVisible(true);
	}
	public void actionPerformed(KeyEvent e)
	{
		String text = txt.getText();
		txtArea.setText(text);	
	}
	public void actionPerformed(ActionEvent e)
	{
	}
	public static void main(String[] args)
	{
		SnoopGUI win = new SnoopGUI();
		win.build();
		win.display();
	}
}
