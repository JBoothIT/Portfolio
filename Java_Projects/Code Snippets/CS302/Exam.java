import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
@SuppressWarnings("serial")
public class Exam extends JFrame implements ActionListener
{
	private JTextField display;
	public Exam(String title)
	{
		super(title);
		setSize(300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		display = new JTextField();
		add(display, BorderLayout.NORTH);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,3));
		for(int i = 1; i <= 9; i++)
		{
			JButton b = new JButton("" + i);
			b.addActionListener(this);
			buttonPanel.add(b);
		}
		add(buttonPanel, BorderLayout.CENTER);
	}
	public void actionPerformed(ActionEvent e)
	{
		String buttonName = e.getActionCommand();
		display.setText(display.getText() + buttonName);
	}
	public static void main(String[] args)
	{
		Exam runFrame = new Exam("Exam Program");	
		runFrame.setVisible(true);
	}
}
