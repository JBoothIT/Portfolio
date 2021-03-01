public class Player 
{
    private String name;
    private String team;
    private int jerseyNumber;


 public Player (String name, String team, int jerseyNumber) 
 {
	 this.name = new String (name);
	 this.team = new String (team);
	 this.jerseyNumber = jerseyNumber;
 }
 public void setName(String name)
 {
	 this.name = new String (name);
 }
 public String getName() 
 {
	return name;
 }

 public void setTeam(String team) 
 {
	this.team = new String (team);
 }

 public String getTeam() 
 {
	 return team;
 }
 public void setJerseyNumber(int jerseyNumber) 
 {
	this.jerseyNumber = jerseyNumber;
 }
 public int getJerseyNumber() 
 {
	return jerseyNumber;
 }

 public boolean equals(Object c)
 {
	 if(c instanceof Player)
	 {
		 if(this.team.equals(((Player)c).team))
				 {
			 		if(this.jerseyNumber ==(((Player)c).jerseyNumber))
			 		{
			 			return true;
			 		}
			 		else
			 			return false;
				 }
		 else
			return false;
		 
	 }
	 else
		 return false;
 }
}
