package com.example.petpals.Utilities;

import com.example.petpals.Models.Pet;
import com.example.petpals.Models.PetList;
import com.example.petpals.Models.VetVisit;
import com.example.petpals.Models.WalkingDay;

import java.util.Arrays;
import java.util.ArrayList;

public class DataGenerator {

    public static PetList generatePetList() {

        PetList petList = new PetList();

        petList.setName("My Pets")
                .addPet(
                        new Pet()
                                .setName("Simba")
                                .setDob("2020-03-02")
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
                                .setImageUri("https://www.vidavetcare.com/wp-content/uploads/sites/234/2022/04/golden-retriever-dog-breed-info.jpeg")
                ).addPet(
                        new Pet()
                                .setName("Belle")
                                .setDob("2021-04-10")
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
                                .setImageUri("https://news.harvard.edu/wp-content/uploads/2014/10/hello-kitty-wallpaper-37_605.jpg")
                ).addPet(
                        new Pet()
                                .setName("Max")
                                .setDob("2019-05-15")
                                .setSex(Pet.Sex.MALE)
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
                                .setImageUri("https://static.wikia.nocookie.net/dog-stories/images/8/84/Max_the_secret_life_of_pets.png/revision/latest?cb=20160612214816")
                ).addPet(
                        new Pet()
                                .setName("Luna")
                                .setDob("2021-02-20")
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
                                .setImageUri("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgIYFJzZER35zycVwgCkqEIG35LH15h--B1g&s")
                ).addPet(
                        new Pet()
                                .setName("Charlie")
                                .setDob("2018-03-18")
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
                                .setImageUri("https://m.media-amazon.com/images/I/61RDVpQrIJL._AC_UF1000,1000_QL80_.jpg")
                );

        return petList;
    }
}
