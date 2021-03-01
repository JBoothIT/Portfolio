/**@author Jeremy Booth
* Date: 8/31/2012
* Creates an object for complex numbers.
*/
public class Complex 
{
	/*Instance variables*/
	public double real;
	public double imag;
	/**@param complex constructor initializes imaginary and real parts of complex numbers*/
	public Complex()
	{
		 real = 0.0;
		 imag = 0.0;
	}
	/**@param complex(double a)(double b) Constructor assigns values to real and imaginary parts*/
	public Complex(double a, double b)
	{
		real = a;
		imag = b;
	}
	/**@param toString() method converts and formats imag and real values
	 * @return returns String values*/
	public String toString()
	{
		if(imag == 0)
		{
			return real + "";
		}
		if(real == 0)
		{
			return imag + "i";
		}
		if(imag < 0)
		{
			
		}
		return real + "+" + imag + "i";
	}
	/**@return returns the double value of the part real.*/
	public double getReal()
	{
		return real;
	}
	/**@return returns the double value of imag*/
	public double getImag()
	{
		return imag;
	}
	/**@param add(Complex c) adds the complex number c to the current complex number
	 * @return returns the sum of two complex numbers as a new complex number*/
	public Complex add(Complex c)
	{
		return new Complex(real + c.real, imag + c.imag);			
	}
	/**@param subtract(Complex c) subtracts two complex numbers
	 * @return returns the value of two subtracted complex numbers as a new complex number*/
	public Complex subtract(Complex c)
	{
		return new Complex(real - c.real, imag - c.imag);			
	}
	/**@param multiply(Complex c) multiplies the real and imag values within two complex numbers
	 * @return returns the multiplied values of two complex numbers as a new complex number*/
	public Complex multiply(Complex c)
	{
		return new Complex(real * c.real - imag * c.imag, real * c.imag + imag * c.real);
	}
	/**@param divide(Complex c) performs division on two complex numbers
	 * @return returns divided values as a new complex number*/
	public Complex divide(Complex c)
	{
		return new Complex((real * c.real + imag * c.imag)/c.sqr(), (imag * c.real - real * c.imag)/c.sqr());
	}
	/**@param abs() finds the absolute value of a complex number
	 * @return return absolute value*/
	public double abs()
	{
		return Math.sqrt(real * real + imag * imag);
	}
	/**@param equals(Complex c, double tolerance) calculated the absolute value of subtracted value of c and determines if c is less than tolerance.
	 * @return boolean value of true or false*/
	public boolean equals(Complex c, double tolerance)
	{
		double equals = subtract(c).abs();
		if(equals < tolerance)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**@param sqr() adds the muliplied values of real and imag
	 * @return (real)^2 + (imag)^2*/
	public double sqr()
	{
		return real * real + imag * imag;
	}
	/**@param print(Complex a, Complex b, double rA, double iA, double rB, double iB, Complex add, Complex sub, Complex mult, Complex div, double abs)*/
	public static void print(Complex a, Complex b, double rA, double iA, double rB, double iB, Complex add, Complex sub, Complex mult, Complex div, double abs)
	{
		System.out.println("Real A: " + rA);
		System.out.println("Imaginary A: " + iA);
		System.out.println("Real B: " + rB);
		System.out.println("Imaginary B: " + iB);
		System.out.println("Addition: " + add.toString());
		System.out.println("Subtraction " + sub.toString());
		System.out.println("Multiply: " + mult.toString());
		System.out.println("Divide: " + div.toString());
		System.out.println("absolute: " + abs);
		if(a.equals(b,1E-6))
		{
			System.out.println("They are equal!");
		}
		else
		{
			System.out.println("They are NOT equal!");
		}
	}
	public static void main(String[] args)
	{
		Complex a = new Complex(1, 2);
		Complex b = new Complex(1, 2);
		double realA = a.getReal();
		double imagA = a.getImag();
		double realB = b.getReal();
		double imagB = b.getImag();
		Complex add = b.add(a);
		Complex sub = b.subtract(a);
		Complex mult = b.multiply(a);
		Complex div = b.divide(a);
		double abs = a.abs();
		print(a, b, realA, imagA, realB, imagB, add, sub, mult, div, abs);
	}
}	