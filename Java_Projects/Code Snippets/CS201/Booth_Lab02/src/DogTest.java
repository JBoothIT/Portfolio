public class DogTest 
{
 public static void main(String[] args) 
 {
	 Dog dog;
	 dog = new Labrador("lab1", "black");
	 System.out.println(dog.getName() + " says " + dog.speak() + " Avg. Weight: " + dog.avgBreedWeight());
	 
	 dog = new Yorkshire("York1", "black");
	 System.out.println(dog.getName() + " says " + dog.speak() + " Avg. Weight: " + dog.avgBreedWeight());
 }
}
