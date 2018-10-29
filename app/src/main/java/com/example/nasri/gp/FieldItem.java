package com.example.nasri.gp;

import android.os.Parcel;
import android.os.Parcelable;

public class FieldItem implements Parcelable {

    int fieldId ;
    String fieldSize ;
    String fieldName ;
    String fieldCity ;
    String fieldImg ;
    double lat ;
    double lang;

    public FieldItem (int id , String field , String city , String size ,String img, double lat , double lang){
        fieldName = field ;
        fieldCity = city ;
        fieldId = id ;
        fieldSize = size ;
        this.lang = lang;
        this.lat = lat ;
        this.fieldImg = img;
    }
    public FieldItem (int id , String field , String city , String size , String img){
        fieldName = field ;
        fieldCity = city ;
        fieldId = id ;
        fieldSize = size ;
        fieldImg = img ;
    }
    public FieldItem (String city){
        fieldCity = city ;
    }

    protected FieldItem(Parcel in) {
        fieldId = in.readInt();
        fieldSize = in.readString();
        fieldName = in.readString();
        fieldCity = in.readString();
        fieldImg = in.readString();
        lat = in.readDouble();
        lang = in.readDouble();
    }

    public static final Creator<FieldItem> CREATOR = new Creator<FieldItem>() {
        @Override
        public FieldItem createFromParcel(Parcel in) {
            return new FieldItem(in);
        }

        @Override
        public FieldItem[] newArray(int size) {
            return new FieldItem[size];
        }
    };

    public String getfieldName() {
        return fieldName;
    }
    public String getfieldCity() {
        return fieldCity;
    }
    public String getFieldSize() {
        return fieldSize;
    }
    public int getFieldId() {
        return fieldId;
    }
    public String getFieldImg(){return fieldImg;}
public double getLat() {
        return  this.lat;
}
public double getLang() {
        return this.lang;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(fieldId);
        parcel.writeString(fieldSize);
        parcel.writeString(fieldName);
        parcel.writeString(fieldCity);
        parcel.writeString(fieldImg);
        parcel.writeDouble(lat);
        parcel.writeDouble(lang);
    }
}

