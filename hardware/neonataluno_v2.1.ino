#include <ArduinoJson.h>
#if defined(ARDUINO) && ARDUINO >= 100
#define printByte(args)  write(args);
#else
#define printByte(args)  print(args,BYTE);
#endif

//uint8_t bell[8]  = {0x4,0xe,0xe,0xe,0x1f,0x0,0x4};
//uint8_t note[8]  = {0x2,0x3,0x2,0xe,0x1e,0xc,0x0};
//uint8_t clock[8] = {0x0,0xe,0x15,0x17,0x11,0xe,0x0};
uint8_t heart[8] = {0x0,0xa,0x1f,0x1f,0xe,0x4,0x0};
//uint8_t duck[8]  = {0x0,0xc,0x1d,0xf,0xf,0x6,0x0};
//uint8_t check[8] = {0x0,0x1,0x3,0x16,0x1c,0x8,0x0};
//uint8_t cross[8] = {0x0,0x1b,0xe,0x4,0xe,0x1b,0x0};
//uint8_t retarrow[8] = {  0x1,0x1,0x5,0x9,0x1f,0x8,0x4};
//  
#include <LiquidCrystal_PCF8574.h>
#include <Wire.h>
LiquidCrystal_PCF8574 lcd(0x3F);

#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>
#define DHTPIN 8
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);
long previousMillis2 = 0;        // will store last time LED was updated
long interval2 = 5000;
#include <SoftwareSerial.h>
SoftwareSerial bltSerial(4, 5); //Rx,Tx
void display1(float, int);
/*  Getting_BPM_to_Monitor prints the BPM to the Serial Monitor, using the least lines of code and PulseSensor Library.
    Tutorial Webpage: https://pulsesensor.com/pages/getting-advanced

  --------Use This Sketch To------------------------------------------
  1) Displays user's live and changing BPM, Beats Per Minute, in Arduino's native Serial Monitor.

  2) Print: "ÃƒÂ¢Ã¢â€žÂ¢Ã‚Â¥  A HeartBeat Happened !" when a beat is detected, live.
  2) Learn about using a PulseSensor Library "Object".
  4) Blinks LED on PIN 13 with user's Heartbeat.
  --------------------------------------------------------------------*/
#define USE_ARDUINO_INTERRUPTS true    // Set-up low-level interrupts for most acurate BPM math.
#include <PulseSensorPlayground.h>     // Includes the PulseSensorPlayground Library.   
//  Variables
const int PulseWire = A0;       // PulseSensor PURPLE WIRE connected to ANALOG PIN 0
const int LED13 = 13;          // The on-board Arduino LED, close to PIN 13.
//int Threshold = 550;           // Determine which Signal to "count as a beat" and which to ignore.
// Use the "Gettting Started Project" to fine-tune Threshold Value beyond default setting.
// Otherwise leave the default "550" value.

PulseSensorPlayground pulseSensor;  // Creates an instance of the PulseSensorPlayground object called "pulseSensor"



// Data wire is plugged into pin 2 on the Arduino

/********************************************************************/
// Setup a oneWire instance to communicate with any OneWire devices
// (not just Maxim/Dallas temperature ICs)

/********************************************************************/
// Pass our oneWire reference to Dallas Temperature.

/********************************************************************/

int Threshold = 700;           // Determine which Signal to "count as a beat" and which to ignore.
int myBPM, myBPM1 = 0;
float temp;
int i = 0;


String final_data = "";

int minVal = 265;
int maxVal = 402;

// Creates an instance of the PulseSensorPlayground object called "pulseSensor"
void LCD (int);                               // Otherwise leave the default "550" value. 
  void LCD_1 (int); 
void setup()
{
  lcd.begin(16, 2); 
  Serial.begin(9600);          // For Serial Monitor
  bltSerial.begin(9600); // Bluetooth connected Software Serial begin
  dht.begin();
  //  lcd.begin(16, 2);
  //   lcd.setBacklight(255);
  delay(1000);
 lcd.createChar(3, heart);

  // Configure the PulseSensor object, by assigning our variables to it.
  pulseSensor.analogInput(PulseWire);
  pulseSensor.blinkOnPulse(LED13);       //auto-magically blink Arduino's LED with heartbeat.
  pulseSensor.setThreshold(Threshold);


  // Double-check the "pulseSensor" object was created and "began" seeing a signal.
  if (pulseSensor.begin()) {
    Serial.println("We created a pulseSensor Object !");  //This prints one time at Arduino power-up,  or on Arduino reset.
  }

  //   Turn on Interrupts for each mode (1 == ON, 0 == OFF)


  //attachInterrupt(digitalPinToInterrupt(interruptPin), ADXL_ISR, RISING);   // Attach Interrupt
}
void loop()
{
   
   lcd.setBacklight(255);
   lcd.home();
   lcd.clear();
  Serial.println("started");

  Serial.println();
  
  delay(400);

  /* bltSerial.println("Accelerometer ");
    bltSerial.println("x");
    bltSerial.print(x);
    bltSerial.print("y");
    bltSerial.print(y);
    bltSerial.print("z");
    bltSerial.print(z);
    bltSerial.println(" ");*/
  // delay(1000);

  myBPM = pulseSensor.getBeatsPerMinute();  // Calls function on our pulseSensor object that returns BPM as an "int".
  delay(1500);
  Serial.print("BPM: ");                        // Print phrase "BPM: "
  Serial.println(myBPM);
  int i = 0;
  temp = dht.readTemperature();

  if (pulseSensor.sawStartOfBeat())
  {

    if ((myBPM >= 60) && (myBPM <= 120))
    {
      // "myBPM" hold this BPM value now.                                           // "myBPM" hold this BPM value now.
      // Constantly test to see if "a beat happened".

      Serial.println("A HeartBeat Happened ! "); // If test is "true", print a message "a heartbeat happened".
      Serial.print("BPM:0 ");                        // Print phrase "BPM: "
      Serial.println(myBPM);
      myBPM1 = myBPM;
      LCD(myBPM1);
      /*  bltSerial.println("HeartBPM Invalid");

        delay(200);
        // bltSerial.println(myBPM);
      */
    }
    else
    {
//      Serial.println("Place finger on sensor");
//      delay(200);
//      Serial.println("invalid Heart Beat");
//      delay(200);
      myBPM=0;
      myBPM1=myBPM;
        LCD_1(myBPM1);

      // considered best practice in a simple sketch.
    }

  }
  StaticJsonBuffer<100> jsonBuffer;
  JsonObject & root = jsonBuffer.createObject();
  root["Heartbeat"] = myBPM1;
  root["temp"] = temp;
  String Jsons; 
  String data;
  root.printTo(Jsons);

  data = '<' + Jsons + '>';
  Serial.println(data);
  delay(1000);
  display1(temp,myBPM1) ;
  delay(200);
  //////////////////////////////
  ///////////////////////////
//   lcd.setBacklight(255);
//  lcd.setCursor(0, 0);
//  lcd.print("TempC=        ");
//  lcd.setCursor(7, 0);
//  lcd.print(temp);
//  delay(100);
//   lcd.setCursor(0, 1);
//  lcd.print("HeartBeat=");
//  lcd.setCursor(11, 1);
//  lcd.print(myBPM1);
//  lcd.print("BPM        ");
//  delay(1000);



  Serial.println("Temperature C");
  Serial.println(temp);

   Serial.println("myBPM");
  Serial.println(myBPM);
   Serial.println("Heart Beat");
  Serial.println(myBPM1);
  delay(1000);
    
  //    unsigned long currentMillis2 = millis();
  //    if ((currentMillis2 - previousMillis2 > interval2))
  //    {
  //      previousMillis2 = currentMillis2;


  final_data += '@';
  final_data += myBPM1;
  final_data += '-';
  final_data += /*(int)*/temp;
  final_data += '-';
   final_data += '$';
  Serial.print(final_data);
  delay(200);
  bltSerial.print(final_data);
  delay(1000);
  final_data = "";

  /////////////////////////
  //    }

}
void LCD(int myBPM1)
{
  lcd.setCursor(0, 0);
  lcd.print("A");
  lcd.printByte(3);
  lcd.print("Beat Happened!");
   lcd.setCursor(0, 1);
    delay(10);
 // lcd.setCursor(0, 1);
  lcd.print("urBPM Val.is:");
   delay(10);
  lcd.print(myBPM1);
  delay(500);
}
void LCD_1(int myBPM1)
{
    //lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Place finger on ");
//  lcd.printByte(3);
  lcd.setCursor(0, 1);
  delay(10);
  lcd.print("ur ");
  lcd.printByte(3);
  lcd.print("for BPM Val.");
  delay(20);
  lcd.print(myBPM1);
  delay(500);
}

  
 void display1(float tem,int BPM)
 {
  lcd.setBacklight(255);
  lcd.setCursor(0, 0);
  lcd.print("TempC=        ");
  lcd.setCursor(7, 0);
  lcd.print(temp);
  delay(100);
//   lcd.setCursor(0, 1);
//  lcd.print("HBeat=");
//  lcd.setCursor(7, 1);
//  lcd.print(myBPM1);
//  lcd.setCursor(11, 1);
//  lcd.print("BPM  ");
  delay(600);

//   delay(1400);
 }
