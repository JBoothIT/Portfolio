public class ScoreIsAboveThresholdException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int threshold;
	private int score;
	public ScoreIsAboveThresholdException(int Score, int Threshold) 
	{
		score = Score;
		threshold = Threshold;		
	}
	public String getMessage()
	{
		return score + " is Above " + threshold;
	}
}
