package com.example.petpals.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Pet implements Parcelable {

    // Enum for Sex
    public enum Sex {
        MALE, FEMALE
    }

    private String id;
    private String name;
    private String dob;
    private Sex sex;
    private String imageUri;
    private ArrayList<VetVisit> vetVisits = null;
    private ArrayList<WalkingDay> walkingDays = null;
    private ArrayList<String> walkingTimes = null;

    // Constructor
    public Pet() {
    }

    // Parcelable constructor
    protected Pet(Parcel in) {
        id = in.readString();
        name = in.readString();
        dob = in.readString();
        sex = Sex.valueOf(in.readString()); // Reading the sex field
        imageUri = in.readString();
        vetVisits = in.createTypedArrayList(VetVisit.CREATOR);
        walkingDays = in.createTypedArrayList(WalkingDay.CREATOR);
        walkingTimes = in.createStringArrayList();
    }

    // Parcelable Creator
    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // Writing the object’s data to a Parcel
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(dob);
        dest.writeString(sex.name()); // Writing the sex field as a String
        dest.writeString(imageUri);
        dest.writeTypedList(vetVisits);
        dest.writeTypedList(walkingDays);
        dest.writeStringList(walkingTimes);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public Pet setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    public String getDob() {
        return dob;
    }

    public Pet setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public Sex getSex() {
        return sex;
    }

    public Pet setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public ArrayList<VetVisit> getVetVisits() {
        return vetVisits;
    }

    public Pet setVetVisits(ArrayList<VetVisit> vetVisits) {
        this.vetVisits = vetVisits;
        return this;
    }

    public ArrayList<WalkingDay> getWalkingDays() {
        return walkingDays;
    }

    public Pet setWalkingDays(ArrayList<WalkingDay> walkingDays) {
        this.walkingDays = walkingDays;
        return this;
    }

    public ArrayList<String> getWalkingTimes() {
        return walkingTimes;
    }

    public Pet setWalkingTimes(ArrayList<String> walkingTimes) {
        this.walkingTimes = walkingTimes;
        return this;
    }

    public int getAge() {
        LocalDate birthDate = LocalDate.parse(dob);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String getImageUri() {
        return imageUri;
    }

    public Pet setImageUri(String imageUri) {
        this.imageUri = imageUri;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Pet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", sex=" + sex +
                ", imageUri='" + imageUri + '\'' +
                ", vetVisits=" + vetVisits +
                ", walkingDays=" + walkingDays +
                ", walkingTimes=" + walkingTimes +
                '}';
    }
}
