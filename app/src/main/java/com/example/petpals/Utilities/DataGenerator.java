package com.example.petpals.Utilities;

import com.example.petpals.Models.Pet;
import com.example.petpals.Models.PetList;
import com.example.petpals.Models.VetVisit;
import com.example.petpals.Models.WalkingDay;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.UUID;

public class DataGenerator {

    public static PetList generatePetList() {

        PetList petList = new PetList();

        petList.setName("My Pets")
                .addPet(
                        new Pet()
                                .setId(generateUniqueId())  // Set unique ID
                                .setName("Coco")
                                .setDob("2015-05-01")
                                .setSex(Pet.Sex.MALE)
                                .setVetVisits(new ArrayList<>(Arrays.asList(
                                        new VetVisit().setVisitDate("2023-03-02").setVisitTime("19:00"),
                                        new VetVisit().setVisitDate("2023-06-05").setVisitTime("15:00")
                                )))
                                .setWalkingDays(new ArrayList<>(Arrays.asList(
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.MONDAY),
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.WEDNESDAY),
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.FRIDAY)
                                )))
                                .setWalkingTimes(new ArrayList<>(Arrays.asList(
                                        "08:00",
                                        "18:00"
                                )))
                                .setImageUri("https://firebasestorage.googleapis.com/v0/b/pet-pals-100e1.appspot.com/o/pets_images%2Fcoco.jpeg?alt=media&token=6349e768-6e8a-4bab-94ef-e1b5aef71d36")
                ).addPet(
                        new Pet()
                                .setId(generateUniqueId())  // Set unique ID
                                .setName("Belle")
                                .setDob("2022-05-10")
                                .setSex(Pet.Sex.FEMALE)
                                .setVetVisits(new ArrayList<>(Arrays.asList(
                                        new VetVisit().setVisitDate("2023-04-10").setVisitTime("10:30")
                                )))
                                .setWalkingDays(new ArrayList<>(Arrays.asList(
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.TUESDAY),
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.THURSDAY)
                                )))
                                .setWalkingTimes(new ArrayList<>(Arrays.asList(
                                        "07:30",
                                        "17:30"
                                )))
                                .setImageUri("https://firebasestorage.googleapis.com/v0/b/pet-pals-100e1.appspot.com/o/pets_images%2Fbelle.jpeg?alt=media&token=cb5d4a02-2ea9-40d3-89c0-805f0a900634")
                ).addPet(
                        new Pet()
                                .setId(generateUniqueId())  // Set unique ID
                                .setName("Mamusha")
                                .setDob("2010-05-15")
                                .setSex(Pet.Sex.FEMALE)
                                .setVetVisits(new ArrayList<>(Arrays.asList(
                                        new VetVisit().setVisitDate("2023-05-15").setVisitTime("11:00")
                                )))
                                .setWalkingDays(new ArrayList<>(Arrays.asList(
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.MONDAY),
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.FRIDAY)
                                )))
                                .setWalkingTimes(new ArrayList<>(Arrays.asList(
                                        "06:30",
                                        "17:00"
                                )))
                                .setImageUri("https://firebasestorage.googleapis.com/v0/b/pet-pals-100e1.appspot.com/o/pets_images%2Fmamusha.jpeg?alt=media&token=448348c0-3881-4af2-ae50-08db9f47d470")
                ).addPet(
                        new Pet()
                                .setId(generateUniqueId())  // Set unique ID
                                .setName("Yoko")
                                .setDob("2019-09-01")
                                .setSex(Pet.Sex.FEMALE)
                                .setVetVisits(new ArrayList<>(Arrays.asList(
                                        new VetVisit().setVisitDate("2023-02-20").setVisitTime("09:45")
                                )))
                                .setWalkingDays(new ArrayList<>(Arrays.asList(
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.WEDNESDAY),
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.SATURDAY)
                                )))
                                .setWalkingTimes(new ArrayList<>(Arrays.asList(
                                        "08:15",
                                        "18:45"
                                )))
                                .setImageUri("https://firebasestorage.googleapis.com/v0/b/pet-pals-100e1.appspot.com/o/pets_images%2Fyoko.jpeg?alt=media&token=36026efe-63c6-4e30-b1f2-a3f043b37b1a")
                ).addPet(
                        new Pet()
                                .setId(generateUniqueId())  // Set unique ID
                                .setName("Timon")
                                .setDob("2022-04-18")
                                .setSex(Pet.Sex.MALE)
                                .setVetVisits(new ArrayList<>(Arrays.asList(
                                        new VetVisit().setVisitDate("2023-03-18").setVisitTime("16:00")
                                )))
                                .setWalkingDays(new ArrayList<>(Arrays.asList(
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.SUNDAY),
                                        new WalkingDay().setDay(WalkingDay.DayOfWeek.THURSDAY)
                                )))
                                .setWalkingTimes(new ArrayList<>(Arrays.asList(
                                        "07:00",
                                        "19:00"
                                )))
                                .setImageUri("https://firebasestorage.googleapis.com/v0/b/pet-pals-100e1.appspot.com/o/pets_images%2Ftimon.jpeg?alt=media&token=3763de90-2b25-4355-b975-0cd9fef2237a")
                );

        return petList;
    }

    // Generate unique IDs for each pet
    private static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }


}
