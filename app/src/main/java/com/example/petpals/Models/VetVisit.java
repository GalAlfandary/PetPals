package com.example.petpals.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class VetVisit implements Parcelable {
    private String visitDate;
    private String visitTime;

    public VetVisit() {
    }

    protected VetVisit(Parcel in) {
        visitDate = in.readString();
        visitTime = in.readString();
    }

    public static final Creator<VetVisit> CREATOR = new Creator<VetVisit>() {
        @Override
        public VetVisit createFromParcel(Parcel in) {
            return new VetVisit(in);
        }

        @Override
        public VetVisit[] newArray(int size) {
            return new VetVisit[size];
        }
    };

    public String getVisitDate() {
        return visitDate;
    }

    public VetVisit setVisitDate(String visitDate) {
        this.visitDate = visitDate;
        return this;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public VetVisit setVisitTime(String visitTime) {
        this.visitTime = visitTime;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "VetVisit{" +
                "visitDate='" + visitDate + '\'' +
                ", visitTime='" + visitTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(visitDate);
        parcel.writeString(visitTime);
    }
}
