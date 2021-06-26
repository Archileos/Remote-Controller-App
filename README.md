# Remote-Controller-App
Advanced Programming 2 Project

### Summary
This app provides accessible control options to a user wishing to control the flight simulator known as flight gear remotely, by connecting to the flight simulator using a socket and sending appropriate commands to the flight simulator server. The app presents a joystick as well as slide bars to adjust the flight by changing the aileron, elevator, rudder or throttle values of the current flight by utilizing control setting provided by the simulator itself.

### App Features
- [x] An option to connect to any ip and port in order to access the flight gear server with those particular network configurations.
- [x] Throttle and Rudder slide bars, which can be adjusted in order to adjust the flight as well as give visual information as to the state of these variables.
- [x] A joystick which adjusts the Aileron and the Elevator, which can be used to easily guide the plane in the desired direction. Once the users stops moving the joystick, it resets to it's default possition as well as setting Aileron and Elevator to their default values.
- [x] An option to connect to a different run after the first connection by typing in a new pair of ip and port and connecting again.

### Folder Structure
```

```

### Required installations
* FlightFear 2020.3.8 (For windows 7,8,10)
* Android Studio 4.2.1 or higher

### Compiling and Running
1. Download this repository.
2. Open FlightSimulator.exe.
3. Go to setting and add the following configuration line: "--telnet=socket,in,10,127.0.0.1,6400,tcp".
4. Run the code on an Android emulator through Android Studio.
Alternatively, open the "Build" section in Android Studio then click "Build APK(s)" then run the APK on your phone using developer tools.

### Additional Links
Tba UML
Tba Video
