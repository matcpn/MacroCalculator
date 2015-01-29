package com.example.macrocalculator;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class ParcelableListHandler implements Parcelable 
{
    String id;
    String ParcelableListHandlerName;
    String ParcelableListHandlerValue;
    boolean isMyParcelableListHandler;
	
    ArrayList<Food> foodList;

    public ParcelableListHandler(String id, String ParcelableListHandlerName, String ParcelableListHandlerValue, boolean isMyParcelableListHandler, ArrayList<Food> foodList) {
        this.id = id;
        this.ParcelableListHandlerName = ParcelableListHandlerName;
        this.ParcelableListHandlerValue = ParcelableListHandlerValue;
        this.isMyParcelableListHandler = isMyParcelableListHandler;
        this.foodList = foodList;
    }

    public ParcelableListHandler(Parcel in) {
        this.id = in.readString();
        this.ParcelableListHandlerName = in.readString();
        this.ParcelableListHandlerValue = in.readString();
        this.isMyParcelableListHandler = (in.readInt() != 0);


        this.foodList = new ArrayList<Food>();
        in.readTypedList(foodList, Food.CREATOR);
    }

    
    
    public int describeContents() {
        return 0;
    }

    
    
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ParcelableListHandlerName);
        dest.writeString(ParcelableListHandlerValue);
        dest.writeInt(isMyParcelableListHandler ? 1 : 0);
        dest.writeTypedList(foodList);
    }


    public static final Parcelable.Creator<ParcelableListHandler> CREATOR = new Parcelable.Creator<ParcelableListHandler>() {

        public ParcelableListHandler createFromParcel(Parcel in) {
            return new ParcelableListHandler(in);
        }

        public ParcelableListHandler[] newArray(int size) {
            return new ParcelableListHandler[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParcelableListHandlerName() {
        return ParcelableListHandlerName;
    }

    public void setParcelableListHandlerName(String ParcelableListHandlerName) {
        this.ParcelableListHandlerName = ParcelableListHandlerName;
    }

    public String getParcelableListHandlerValue() {
        return ParcelableListHandlerValue;
    }

    public void setParcelableListHandlerValue(String ParcelableListHandlerValue) {
        this.ParcelableListHandlerValue = ParcelableListHandlerValue;
    }

    public boolean isMyParcelableListHandler() {
        return isMyParcelableListHandler;
    }

    public void setMyParcelableListHandler(boolean myParcelableListHandler) {
        isMyParcelableListHandler = myParcelableListHandler;
    }


    public ArrayList<Food> getfoodList() {
        return foodList;
    }

    public void setfoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    
    public boolean equals(Object obj) {
        if(obj instanceof ParcelableListHandler)
		{
            ParcelableListHandler toCompare = (ParcelableListHandler) obj;
            return (this.getId().equalsIgnoreCase(toCompare.getId()));
        }
        return false;
    }

    
    public int hashCode() {
        return (this.id).hashCode();
    }

}
