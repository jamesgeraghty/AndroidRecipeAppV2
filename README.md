# Android Recipe Version 2  App by James Geraghty

Mobile Application Development (Higher Diploma in Computer Science) completed From November to December 2021.

This Recipes app is designed to allow users sign up and login into their account. New recipes can be added and stored in a database. The recipes can also be updated and removed. The key difference between this app and the first version is that Model,View,ViewModel separation pattern. I have also used fragments instead of separate activities. As opposed to an activity that allows the user to interact with the app, fragments work within activities and offer reusable components. This report outlines the steps through how some of the most important updates to the application from a technical viewpoint have been implemented. 


## Features

-	Receive Recipes via a text input field using fragments. 
-	The user can log in using their account.
-	Google Authentication allows the user to create.
-	Firebase Realtime Database storage. 
-	Recipes are displayed in a list that can be easily viewed even after they end the session. Persistence allows all details to be stored in a database.
-	The Navigation menu allows the user to easily navigate through the application.
-	 Swipe Controls allow the user to update and delete recipes.
-	MVVM – allows for code reuse, binding makes data easier to handle and the code is easy to maintain because the separation of the key elements of the app offers structure and uniformity to the code. 
-	Navigation Component structure including the use of SafeArgs.

## MVVM Flowchart Design
![mvvm](https://user-images.githubusercontent.com/59435031/147878452-5e987f93-a957-4c2e-9292-efc4de36fc88.png)


  
## Navigation Components

![App Screenshot](https://github.com/jamesgeraghty/AndroidRecipeAppV2/blob/master/androidversion2.png)



![App Screenshot](https://github.com/jamesgeraghty/AndroidRecipeAppV2/blob/master/navdrawer.JPG)

## References
Exposed Drop-Down Menu - Forget about Spinner | Android Studio Tutorial
https://www.youtube.com/watch?v=741l_fPKL3Y

Android MVVM Design Pattern
https://www.journaldev.com/20292/android-mvvm-design-pattern

Adapter Tutorial With Example In Android Studio
https://abhiandroid.com/ui/adapter

Get started with Cloud Storage on Web
https://firebase.google.com/docs/storage/web/start

Firebase  QuickStart-android
https://github.com/firebase/quickstart-android

Google Developer training.
https://google-developer-training.github.io/android-developer-advanced-course-concepts/unit-1-expand-the-user-experience/lesson-1-fragments/1-1-c-fragments/1-1-c-fragments.html

MVVM (Model View ViewModel) Architecture Pattern in Android
https://www.geeksforgeeks.org/mvvm-model-view-viewmodel-architecture-pattern-in-android/



