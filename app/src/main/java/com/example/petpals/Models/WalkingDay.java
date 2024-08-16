package com.example.petpals.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class WalkingDay implements Parcelable {
    public enum DayOfWeek {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

    private DayOfWeek day;

    public WalkingDay() {
    }

    protected WalkingDay(Parcel in) {
        day = DayOfWeek.valueOf(in.readString());
    }

    public static final Creator<WalkingDay> CREATOR = new Creator<WalkingDay>() {
        @Override
        public WalkingDay createFromParcel(Parcel in) {
            return new WalkingDay(in);
        }

        @Override
        public WalkingDay[] newArray(int size) {
            return new WalkingDay[size];
        }
    };

    public DayOfWeek getDay() {
        return day;
    }

    public WalkingDay setDay(DayOfWeek day) {
        this.day = day;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day.name());
    }
}

