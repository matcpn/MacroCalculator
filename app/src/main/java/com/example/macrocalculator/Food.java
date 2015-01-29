package com.example.macrocalculator;

import android.os.Parcel;
import android.os.Parcelable;


public class Food implements Parcelable 
{
    public String name;
	public double calorieCount, proteinCount, carbCount, fatCount; 
	
	public Food(String name, double calorieCount, double proteinCount, double carbCount, double fatCount)
	{
		this.name = name;
		this.calorieCount = calorieCount;
		this.proteinCount = proteinCount;
		this.carbCount = carbCount;
		this.fatCount = fatCount;
	}
	public Food(String name, double proteinCount, double carbCount, double fatCount)
	{
		this.name = name;
		this.proteinCount = proteinCount;
		this.carbCount = carbCount;
		this.fatCount = fatCount;
		this.calorieCount = fatCount*9 + carbCount*4 + proteinCount*4;
	}

    public Food(Parcel in) {
		this.name = in.readString();
		this.calorieCount = in.readDouble();
		this.proteinCount = in.readDouble();
		this.carbCount = in.readDouble();
		this.fatCount = in.readDouble();
    }

	public Food(String food)
	{
		food = food.trim();
		
		int index0 = 0;
		int index1 = food.indexOf(",", 0);
		int index2 = food.indexOf(",", index1+1);
		int index3 = food.indexOf(",", index2+1);
		int index4 = food.indexOf(",", index3+1);
		
		
		this.name = food.substring(index0, index1);
		this.calorieCount = Double.parseDouble(food.substring(index1+1, index2));
		this.proteinCount = Double.parseDouble(food.substring(index2+1, index3));
		this.carbCount = Double.parseDouble(food.substring(index3+1, index4));
		this.fatCount = Double.parseDouble(food.substring(index4+1, food.length()));
	}
	
    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(calorieCount);
        dest.writeDouble(proteinCount);
        dest.writeDouble(carbCount);
		dest.writeDouble(fatCount);
    }
	
    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() 
	{

        public Food createFromParcel(Parcel in) 
		{
            return new Food(in);
        }

        public Food[] newArray(int size) 
		{
            return new Food[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	/////////////////////////////////////////////////////////////////////////
    public double getCalorieCount() {
        return calorieCount;
    }

    public void setCalorieCount(double calorieCount) {
        this.calorieCount = calorieCount;
    }
	/////////////////////////////////////////////////////////////////////////
    public double getProteinCount() {
        return proteinCount;
    }

    public void setProteinCount(double proteinCount) {
        this.proteinCount = proteinCount;
    }
	/////////////////////////////////////////////////////////////////////////
	public double getCarbCount() {
        return carbCount;
    }

    public void setCarbCount(double carbCount) {
        this.carbCount = carbCount;
    }
	/////////////////////////////////////////////////////////////////////////
	public double getFatCount() {
        return fatCount;
    }

    public void setFatCount(double fatCount) {
        this.fatCount = fatCount;
    }
	/////////////////////////////////////////////////////////////////////////
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Food) { // thats pretty cool
            Food toCompare = (Food) obj;
            return (this.name.equalsIgnoreCase(toCompare.getName()));
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (this.getName()).hashCode();
    }
    
    public String toString()
    {
    	return this.name + "," + this.calorieCount + "," + this.proteinCount + "," + this.carbCount + "," + this.fatCount + "\n";
    }

}