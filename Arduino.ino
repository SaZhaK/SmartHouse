#include <Servo.h>

const int buttonPin = 2;
const int piezoPort = 12;
const int redPort = 6;
const int greenPort = 11;
const int bluePort = 3;
#define pinX    A2
#define pinY    A1
#define fanPin A5

int red;
int green;
int blue;
int buttonState = 0;
int angle = 0;
int fan = 0;

Servo servo;

void setup() {
  Serial.begin(9600);
  pinMode(buttonPin, INPUT_PULLUP);
  pinMode(piezoPort, OUTPUT);

  pinMode(redPort, OUTPUT);
  pinMode(greenPort, OUTPUT);
  pinMode(bluePort, OUTPUT);
  
  pinMode(pinX, INPUT);
  pinMode(pinY, INPUT);

  pinMode(fanPin, OUTPUT);

  servo.attach(5);
}

void loop() {
  // Джойстик
  int X = analogRead(pinX);
  int Y = analogRead(pinY);
  String data = String(X) + " " + String(Y);

  // Кнопка
  buttonState = digitalRead(buttonPin);
  data += " " + String(buttonState) + " ";
  delay(50);

  Serial.print(data);

  String command = Serial.readString();

  if (command == "sound") {
    //Пьезоэлемент
    tone (piezoPort, 500);
    delay(50);
  } else if (command[0] == 'r') {
    if (command.length() == 2) red = command[1] - '0';
    else if (command.length() == 3) red = (command[1] - '0') * 10 + command[2] - '0';
    else if (command.length() == 4) red = (command[1] - '0') * 100 + (command[2] - '0') * 10 + command[3] - '0';
    delay(50);
  } else if (command[0] == 'g') {
    if (command.length() == 2) green = command[1] - '0';
    else if (command.length() == 3) green = (command[1] - '0') * 10 + command[2] - '0';
    else if (command.length() == 4) green = (command[1] - '0') * 100 + (command[2] - '0') * 10 + command[3] - '0';
    delay(50);
  } else if (command[0] == 'b') {
    if (command.length() == 2) blue = command[1] - '0';
    else if (command.length() == 3) blue = (command[1] - '0') * 10 + command[2] - '0';
    else if (command.length() == 4) blue = (command[1] - '0') * 100 + (command[2] - '0') * 10 + command[3] - '0';
    delay(50);
  } else if (command == "fan") {
    fan = 10;
    delay(50);
  } else if (command == "stopFan") {
      fan = 0;
      delay(50);
  } else if (command == "stopRed") {
      red = 0;
      delay(50);
  } else if (command == "stopGreen") {
      green = 0;
      delay(50);
  } else if (command == "stopBlue") {
      blue = 0;
      delay(50);
  } else {
    if (command.length() == 1) angle = command[0] - '0';
    else if (command.length() == 2) angle = (command[0] - '0') * 10 + command[1] - '0';
    else if (command.length() == 3) angle = (command[0] - '0') * 100 + (command[1] - '0') * 10 + command[2] - '0';
    servo.write(angle);
    
    noTone(piezoPort);
  }
  
  analogWrite(redPort, red);
  analogWrite(greenPort,green);
  analogWrite(bluePort, blue);
  digitalWrite(fanPin, fan);
  delay(1000);
}
