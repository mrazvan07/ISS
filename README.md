## Move reminder


### Project description
---
The objective of this project is to create a reminder device that encourages physical activity by beeping at set intervals until the user moves for a certain amount of time. The device utilizes an Arduino board to control the timing and triggering of the beep, as well as a movement detector to sense when the user is moving.

To use the device, the user selects a time interval between 0 and 9 minutes using buttons. Once the interval is set, the device begins a countdown and, after the specified time has elapsed, starts beeping continuously until the user begins moving.

### Schematics
---
![Project schematics](images/my_sketch.png "schematics")

### Hardware pre-requisites
---

* Arduino UNO R3 Board - [specifications here](https://docs.arduino.cc/hardware/uno-rev3). 

* Passive Infrared (PIR) sensor: This sensor will detect motion and trigger the countdown timer. 
Any PIR sensor that operates at 5V and outputs a digital signal can be used, I used the [HC-SR501 PIR sensor](https://components101.com/sensors/hc-sr501-pir-sensor).

* Digit display: display to show the current time interval and countdown status. 
[7 segment display](https://components101.com/displays/7-segment-display-pinout-working-datasheet).

* [Buzzer](https://components101.com/misc/buzzer-pinout-working-datasheet) to emit the audible signal when the user is supposed to move.  

* 470 $\Omega$ resistors. [Specification here](https://components101.com/resistors/resistor). 

* [Breadboard](https://components101.com/misc/breadboard-connections-uses-guide).  
* [Jumper wires](https://store.arduino.cc/collections/cables-wires/products/breadboard-jumper-wire-pack-200mm-100mm). MM and MF wires.


### Hardware pre-requisites
---

* [Arduino IDE](https://docs.arduino.cc/learn/starting-guide/the-arduino-software-ide) - for writing and uploading the code on the Arduino.
* [Fritzing](https://fritzing.org/) - for diagram drawing.

### Setup and build
---

Connecting and writing the code for the single digit screen.  
After connecting the screen and wires as in the diagram [diagram](images/my_sketch.png "schematics") I wrote the following function to display the numbers from 0 to 9 on the screen.

```c++
const int displayPins[7] = {2, 3, 4, 5, 6, 7, 8};

byte displayLEDs[11][7] = { 
        { 0,0,0,0,0,0,1 },  // = 0
        { 1,0,0,1,1,1,1 },  // = 1
        { 0,0,1,0,0,1,0 },  // = 2
        { 0,0,0,0,1,1,0 },  // = 3
        { 1,0,0,1,1,0,0 },  // = 4
        { 0,1,0,0,1,0,0 },  // = 5
        { 0,1,0,0,0,0,0 },  // = 6
        { 0,0,0,1,1,1,1 },  // = 7
        { 0,0,0,0,0,0,0 },  // = 8
        { 0,0,0,0,1,0,0 },  // = 9 
        { 1,1,1,1,1,1,1 }   // = None 
        }; 


void writeDigit(int digit) {
  for (int i = 0; i < 7; i++)
    digitalWrite(displayPins[i], !displayLEDs[digit][i]);
}
```

In the `setup` function, the mode of the used pins must be changed, in my case 2-8, after which we can call `write_digit(digit)` to display the numbers from 0 to 9 on the display.

```c++
  for (int i = 0; i < 7; i++)
    pinMode(displayPins[i], OUTPUT);
```

The next step is to connect the buttons to the Arduino board and write the code in such a way that at the start of the program a number between 1 and 9 can be set representing the number of minutes at which the alarm will start. The second button has the role of blocking the user's decision.

```c++
void setTimeInterval() {
  writeDigit(minutes);
  while(true) {
    if (digitalRead(BUTTON2)) {
      blink(3);
      minutes_passed = minutes;
      start = millis();
      return;
    }
    int up = digitalRead(BUTTON1);
    minutes += up;
    if (minutes > 9)
      minutes = 0;
    writeDigit(minutes);
    delay(150);
  }
}
```

For this, I use the `setTimeInterval()` function, which is called only if the set number is 0. In this function, the number of minutes is changed, after which, when the second button is pressed, the number of minutes is chosen and an animation appears when the function is called `blink(3)`.

Now it's the turn of the buzzer, which is connected to the Arduino board, and the logic is to be written so that it rings only after the number of minutes selected by the user has passed.

After this part works, I added the motion sensor that doesn't let the 10 seconds elapse if no movement is detected.
To do this, I added this code to the `startBuzzer()` function that handles the buzzer.

```c++
void startBuzzer() {
  writeDigit(9);
  digitalWrite(BUZZER, HIGH);
  while (!digitalRead(MOTION_SENSOR));
  for (int i = 9; i >= 0; i--) {
    while (!digitalRead(MOTION_SENSOR));
    writeDigit(i);
    delay(1000);
  }
  digitalWrite(BUZZER, LOW);
}
```

### Runinng

When it starts, the number 0 is displayed on the screen, meaning that no duration is selected.  
By pressing button 1 (from the left), the number is increased by 1. In the case of a number that exceeds 9, it starts again from 0. 

![selecting_number](gifs/selecting_minutes.gif)  

After choosing the number of minutes, press button 2, which locks the chosen number that remains on the screen.
From this moment, when a minute passes, the screen will show 1 number less until it reaches 0.  
When the counter reaches 0, the beeper starts and the screen displays the number 9, which does not change until the motion sensor detects something. When it starts to detect movement on the screen, it starts decreasing the numbers until they reach 0.  


If during the decrease the sensor no longer detects movement, the timer stops and restarts when movement is detected again.    
When the timer reaches 0, the beeper stops and the number of minutes is reset to what it was initially.  
If you want to change the number of minutes, press and hold button number two until the screen displays the number 0, then follow the steps from the beginning.

### Demo

[Here's a short demo](video/demo.mp4)

![Here's a short demo](video/demo.mp4)
