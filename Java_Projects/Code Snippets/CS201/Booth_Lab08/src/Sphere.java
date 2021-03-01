import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Sphere extends JPanel implements Runnable
{
	private int r, x, y;
	private int xA, yA;
	public Sphere()
	{
		r = 10;
		xA = 10;
		yA = 10;
		x = r;
		y = r;
	}
	 public Dimension getPreferredSize() 
	 {
	        Dimension layoutSize = super.getPreferredSize();
	        int max = Math.max(layoutSize.width,layoutSize.height);
	        return new Dimension(max+100,max+100);
	 }
	 protected void paintComponent(Graphics g) 
	 {
		
	 }
	public void run()
	{
		while(true)
		{
			repaint();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}