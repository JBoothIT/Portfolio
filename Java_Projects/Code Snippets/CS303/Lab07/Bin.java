package Lab07;

import java.util.*;

public class Bin
{
	public int size = 0;
	public double filled = 0;
	public double remaining = 0;
	public LinkedList<Double> item;
	public Bin(int size)
	{
		this.size = size;
		remaining = size;
		item = new LinkedList<Double>();
		if(this.size < 0)
		{
			this.size = 0;
		}
	}
	public boolean fits(double value)
	{
		if(remaining >= value)
		{
			return true;
		}
		return false;
	}
	public boolean insert(Double value)
	{
		if(!fits(value))
		{
			return false;
		}
		item.add(value);
		remaining -= value;
		filled += value;
		
		return true;
	}
	public boolean isFull()
	{
		if(remaining == 0)
		{
			return true;
		}
		return false;
	}
	public double filled()
	{
		return filled;
	}
	public double remaining()
	{
		return remaining;
	}
	public int getSize()
	{
		return size;
	}
	public LinkedList<Double> getItems()
	{
		return item;
	}
	public String toString()
	{
		String out = "{";
		for(int i = 0; i < item.size(); i++)
		{
			out += " " + item.get(i);
		}
		out += "}";
		return out;
	}
	
}
