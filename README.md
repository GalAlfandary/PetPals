# PetPals

PetPals is an Android application designed to help pet owners manage and organize their pets' daily routines, vet visits, and other important activities. The app offers a user-friendly interface where users can add, edit, and delete pets, as well as manage walking schedules and vet appointments. 

## Features
----------------

- **Add/Edit/Delete Pets:** Users can add new pets with details like name, date of birth, gender, and a profile picture.
- **Manage Walking Schedules:** Set up daily walking schedules with options to choose specific days and times. Users can add, update, or delete walking times.
- **Vet Appointments:** Schedule vet visits with date and time, and manage them through the app.
- **Image Uploading:** Upload pet images using the Image Picker library with options to crop and adjust the image.
- **Notifications:** Get notified about upcoming vet visits and walking times.
- **Intuitive UI:** Easy-to-navigate interface with Material Design components.

## Libraries Used
------------------

- **Glide**: Used for efficient image loading and caching. It handles the image loading in the pet profile and ensures smooth performance.
  - **Usage**: 
    - Loading images from a URL.
    - Caching images for better performance.
- **Image Picker by Dhaval2404**: Simplifies the process of selecting images from the gallery or camera and provides cropping features.
  - **Usage**:
    - Used to upload pet images during the addition or editing of pet profiles.
- **Popup Dialog by Saad Ahmed**: Provides customizable pop-up dialogs for various user interactions.
  - **Usage**:
    - Confirming actions like deleting a pet profile.
- **Firebase Realtime Database**: Stores all pet data, including profiles, vet visits, and walking schedules, in real-time.
  - **Usage**:
    - CRUD operations for managing pet data.
- **Firebase Storage**: Used for storing pet profile images.
  - **Usage**:
    - Uploading and retrieving images linked to each pet.

  
## Screenshots
------------------
**Login screen**

<img src="https://github.com/user-attachments/assets/9f354706-a4ac-465c-babb-02d41775d924" alt="Login" width="300">

**Main menu**

<img src="https://github.com/user-attachments/assets/a226e9b8-5823-45b6-a45d-f12b1e9f63cc" alt="menu" width="300">

**Pet info screen**

<img src="https://github.com/user-attachments/assets/3d419aee-3988-4842-8e29-df75af40bd99" alt="menu" width="300">



## Usage
----------------

1. **Adding a Pet:**
  - Navigate to the "Add Pet" screen.
  - Enter the pet’s details including name, date of birth, and gender.
  - Upload a profile picture using the image picker.
  - Save the pet profile.

2. **Managing Walking Schedules:**
  - Go to the "Edit Walking Schedule" screen.
  - Select days of the week and specific times for walks.
  - Add multiple walking times for a single day.
  - Save changes to update the schedule.

3. **Scheduling Vet Visits**
  - Navigate to the "Vet Visits" section.
  - Add a new vet visit by selecting a date and time.
  - View, edit, or delete existing vet visits.

4. **Notifications**
-   The app will send notifications for upcoming vet visits and walking times, ensuring that you never miss an important event.

   
