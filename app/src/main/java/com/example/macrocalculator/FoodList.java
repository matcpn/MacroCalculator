package com.example.macrocalculator;

import java.util.ArrayList;
import java.util.Iterator;

public class FoodList 
{
	private ArrayList<Food> foodList;
	public FoodList()
	{
		foodList = new ArrayList<Food>();
	}
	
	public void addFood(Food newFood)
	{
		this.foodList.add(newFood);
	}

    public ArrayList<Food> getFoodList() {
        return foodList;
    }
	
	public void addFood(String name, double proteinCount, double carbCount, double fatCount)
	{
		Food newFood = new Food(name, proteinCount, carbCount, fatCount);
		this.foodList.add(newFood);
	}
	
	public float getProteinCount()
	{
		float proteinCount = 0;
		Iterator<Food> itr = foodList.iterator();
		while(itr.hasNext())
		{
			proteinCount+= itr.next().proteinCount;
		}
		return proteinCount;
	}
	public float getCarbCount()
	{
		float carbCount = 0;
		Iterator<Food> itr = foodList.iterator();
		while(itr.hasNext())
		{
			carbCount+= itr.next().carbCount;
		}
		return carbCount;
	}
	public float getFatCount()
	{
		float fatCount = 0;
		Iterator<Food> itr = foodList.iterator();
		while(itr.hasNext())
		{
			fatCount+= itr.next().fatCount;
		}
		return fatCount;
	}
	public float getCalorieCount()
	{
		float calorieCount = 0;
		Iterator<Food> itr = foodList.iterator();
		while(itr.hasNext())
		{
			calorieCount+= itr.next().calorieCount;
		}
		return calorieCount;
	}
	public int getLength()
	{
		return this.foodList.size();
	}
	public String[] getFoodNames()
	{
		String[] foodNames = new String[this.getLength()];
		int counter = 0;
		
		Iterator<Food> itr = this.foodList.iterator();
		while(itr.hasNext())
		{
			foodNames[counter] = itr.next().name;
			counter++;
		}
		return foodNames;
	}
	public Food searchByName(String name)
	{
		Iterator<Food> itr = this.foodList.iterator();
		while(itr.hasNext())
		{
			Food temp = itr.next();
			if(name.equals(temp.name))
			{
				return temp;
			}
		}
		return null;
	}
	public void printList()
	{
		Iterator<Food> itr = this.foodList.iterator();
		while(itr.hasNext())
		{
			System.out.println(itr.next().toString());
		}
	}


}
