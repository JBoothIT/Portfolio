public class Yorkshire extends Dog
{
	 private String color;
	 private int breedWeight = 50;
	 
	 public Yorkshire(String name, String color)
	 {
		 super(name);
		 this.color = color;
	 }
	 public String speak() 
	 {
	  return "woof woof";
	 }
	 public int avgBreedWeight()
	 {
		return breedWeight;
	 }
}
