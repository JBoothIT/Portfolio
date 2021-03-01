import javax.swing.*;
import java.awt.*;
public class Exam1 
{
	public static void main(String args[])
	{
		JFrame win = new JFrame("Sample GUI");
		win.setSize(480,480);
		JPanel left = new JPanel(new FlowLayout());
		left.add(new JButton("Component L1"));
		left.add(new JButton("Component L2"));
		
		JPanel center = new JPanel(new FlowLayout());
		center.add(new JButton("Component C1"));
		center.add(new JButton("Component C2"));
		
		JPanel right = new JPanel(new FlowLayout());
		right.add(new JButton("Component R1"));
		right.add(new JButton("Component R2"));
		
		Container content = win.getContentPane();
		content.setLayout(new GridLayout(3, 0));
		
		content.add(left);
		content.add(center);
		content.add(right);
		
		win.setVisible(true);
	}
}
