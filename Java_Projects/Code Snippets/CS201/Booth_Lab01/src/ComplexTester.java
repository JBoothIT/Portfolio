/**@author Jeremy Booth
* Date: 8/26/2012
* Tester class for testing the Complex class.
*/

import java.util.Scanner;
import java.text.DecimalFormat;

public class ComplexTester {
	public static void main(String[] args) {
		Complex c1, c2;
		Scanner scan = new Scanner(System.in);

		System.out.println("Test constructor Complex(): ");
		c1 = new Complex();
		System.out.println("c1 = " + c1);
		System.out.println();

		System.out.println("Test constructor Complex(double a, double b): ");
		System.out.print("Enter the real and imaginary parts: ");

		double r1 = scan.nextDouble();
		double i1 = scan.nextDouble();

		c1 = new Complex(r1, i1);
		System.out.println("c1 = " + c1);
		System.out.println();

		System.out.println("Test constructor Complex(double a, double b): ");
		System.out.print("Enter the real and imaginary parts: ");

		double r2 = scan.nextDouble();
		double i2 = scan.nextDouble();

		c2 = new Complex(r2, i2);
		System.out.println("c2 = " + c2);
		System.out.println();

		System.out.println("Test getReal() and getImag(): ");
		System.out.println("The real part of c1 is: " + c1.getReal());
		System.out.println("The imag part of c1 is: " + c1.getImag() + "i");
		System.out.println();

		System.out.println("Test add(): ");
		System.out.println("c1 + c2 = " + c1.add(c2));
		System.out.println();

		System.out.println("Test subtract(): ");
		System.out.println("c1 - c2 = " + c1.subtract(c2));
		System.out.println();

		System.out.println("Test multiply(): ");
		System.out.println("c1 * c2 = " + c1.multiply(c2));
		System.out.println();

		System.out.println("Test divide(): ");
		System.out.println("c1 / c2 = " + c1.divide(c2));
		System.out.println();

		System.out.println("Test abs(): ");
		DecimalFormat fmt = new DecimalFormat("0.000");
		System.out.println("c1 abs: " + fmt.format(c1.abs()));
		System.out.println("c2 abs: " + fmt.format(c2.abs()));
		System.out.println();

		System.out.println("Test equals(): ");

		if (c1.equals(c2, 1E-6)) {
			System.out.println("c1 is equal to c2");
		} else {
			System.out.println("c1 is not equal to c2");
		}
	}
}