public class Labrador extends Dog 
{
 private String color; // black, yellow, chocolate
 private int breedWeight = 75;
 
 public Labrador(String name, String color)
 {
	super(name);
	this.color = color;
 }

 public String speak() 
 {
  return "WOOF";
 }

 public int avgBreedWeight() 
 {
  return breedWeight;
 }
}
