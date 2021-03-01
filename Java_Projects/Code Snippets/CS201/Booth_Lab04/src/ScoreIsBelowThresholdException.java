public class ScoreIsBelowThresholdException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int threshold;
	public int score;
	public ScoreIsBelowThresholdException(int Score, int Threshold) 
	{
		score = Score;
		threshold = Threshold;		
	}
	public String getMessage()
	{
		return score + " is below " + threshold;
	}
}
