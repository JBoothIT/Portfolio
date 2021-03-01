public class ComparePlayers 
{
 public static void main(String[] args) 
 {
  Player player1 = new Player("Ronnie Brown", "Auburn", 23);
  Player player2 = new Player("Ronnie Brown", "Auburn", 23);
  Player player3 = player1;

  if (player1.equals(player2)) 
  {
   System.out.println("player1 and player2 are equal");
  } else 
  {
   System.out.println("player1 and player2 not are equal");
  }

  if (player1.equals(player3)) 
  {
   System.out.println("player1 and player3 are equal");
  }
  else 
  {
   System.out.println("player1 and player3 not are equal");
  }
 }
}
