public class DoMath implements Runnable 
{
	private int i;
	public DoMath(int i) 
	{
		this.i = i;
	}
	public void run() 
	{
		SimpleMath.sqrCube(i);
	}
}
