# ReqRes Client

This is a simple Android application that interacts with the ReqRes API. The application fetches data from the API, saves it locally using Room, and displays it in a RecyclerView. It also allows basic CRUD operations on the fetched data.

## Features

- Fetch user data from the ReqRes API
- Save data locally using Room
- Display users in a RecyclerView with images
- Perform CRUD (Create, Read, Update, Delete) operations on users
- MVVM (Model-View-ViewModel) architecture
- Uses ViewBinding for UI components

## Libraries Used

- Retrofit for API calls
- Room for local data storage
- Glide for image loading
- ViewBinding for UI components
- RecyclerView for displaying lists

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/AwesomeSapir/ReqRes-Client.git
cd ReqRes-Client
```

### Open the Project in Android Studio

Open Android Studio.
Click on File > Open... and navigate to the directory where you cloned the repository.
Select the ReqRes-Client folder and click OK.

### Build the Project

After the project is loaded in Android Studio, click on Build > Make Project or use the Ctrl+F9 shortcut.
Android Studio will download all required dependencies and build the project.

### Run the Application

Ensure that an Android device (physical or emulator) is connected and configured.
Click on Run > Run 'app' or use the Shift+F10 shortcut.
Select the device where you want to run the application.
