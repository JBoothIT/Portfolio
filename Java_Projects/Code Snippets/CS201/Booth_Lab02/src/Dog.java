abstract public class Dog 
{
 private String name;

 public Dog(String name)
 {
  this.name = name;
 }
 
 public String getName() 
 {
  return name;
 }
abstract public int avgBreedWeight();

 public String speak() 
 {
  return "Woof";
 }
}