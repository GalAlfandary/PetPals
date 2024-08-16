package com.example.petpals.Models;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PetList {
    private String name;
    private ArrayList<Pet> pets;



    public PetList() {
        this.pets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public PetList setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Pet> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }

    public PetList addPet(Pet pet){
        this.pets.add(pet);
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "PetList{" +
                "name='" + name + '\'' +
                ", pets=" + pets +
                '}';
    }
}
