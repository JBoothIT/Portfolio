import java.util.Scanner;

class QuizScore 
{
	private static final int minScore = 0;
	private static final int maxScore = 10;

	public static void printScore(int score) throws Exception 
	{
		if (score < minScore) 
		{
			throw new ScoreIsBelowThresholdException(score, minScore);
		}
		else if (score > maxScore) 
		{
			throw new ScoreIsAboveThresholdException(score, maxScore);
		}
		else 
		{
			System.out.println("score: " + score);
		}
	}

	public static void main(String args[]) 
	{
		int n;
/*hasNextInt returns true only if the next value is an int.
 * nextInt()*/
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNextInt()) 
		{
			try 
			{
				n = scanner.nextInt();
				printScore(n);
			} 
			catch (Exception e)
			{
				// bad score
				System.out.println(e);
			}
		}
	}
}
