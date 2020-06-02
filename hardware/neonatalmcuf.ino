#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <WiFiServer.h>
#include <WiFiUdp.h>

String apiKey = "3DUMN2UI04YQGHPZ";     

const char *ssid =  "aishu";     
const char *pass =  "aishuaishu";
const char *server = "api.thingspeak.com";
WiFiClient client;
//Variables
float pressureReading;

const int numChars = 100;
char receivedChars[numChars];

boolean newData = false;
void setup()
{
  // put your setup code here, to run once:
Serial.begin(9600);
WiFi.disconnect();
  delay(10);


  //       Serial.println("Connecting to ");
  //       Serial.println(ssid);
  //

  WiFi.begin(ssid, pass);
  //       Serial.println(WiFi.localIP());
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
                Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");


}

void loop()
{
  
 float temp;
 int myBPM1;
 recvWithStartEndMarkers();
 showNewData();
 delay(1000);
 StaticJsonBuffer<100> jsonBuffer1;
 JsonObject &root1 = jsonBuffer1.parseObject(receivedChars);
 
 myBPM1=root1["Heartbeat"];
  temp=root1["temp"];
 String Jsonr;
 root1.printTo(Jsonr);
 Serial.println(Jsonr);
 delay(1000);
 Serial.println("Heartbeat");
 Serial.println(myBPM1);
 Serial.println("Temperature C");
    Serial.println(temp);
   
  if (client.connect(server, 80))  
  {

    String postStr = apiKey;
    postStr += "&field1=";
    postStr += String(temp);
    postStr += "&field2=";
    postStr += String(myBPM1);
    postStr += "\r\n\r\n";

    client.print("POST /update HTTP/1.1\n");
    client.print("Host: api.thingspeak.com\n");
    client.print("Connection: close\n");
    client.print("X-THINGSPEAKAPIKEY: " + apiKey + "\n");
    client.print("Content-Type: application/x-www-form-urlencoded\n");
    client.print("Content-Length: ");
    client.print(postStr.length());
    client.print("\n\n");
    client.print(postStr);

   
  }
  client.stop();
  delay(7000); 
}
void recvWithStartEndMarkers() {
    static boolean recvInProgress = false;
    static byte ndx = 0;
    char startMarker = '<';
    char endMarker = '>';
    char rc;
 
    while (Serial.available() > 0 && newData == false)
    {
        rc = Serial.read();

        if (recvInProgress == true) {
            if (rc != endMarker)
            {
                receivedChars[ndx] = rc;
                ndx++;
                if (ndx >= numChars)
                {
                    ndx = numChars - 1;
                }
            }
            else
            {
                receivedChars[ndx] = '\0'; // terminate the string
                recvInProgress = false;
                ndx = 0;
                newData = true;
            }
        }

        else if (rc == startMarker)
        {
            recvInProgress = true;
        }
    }
}

void showNewData() {
    if (newData == true) {
        Serial.print("This just in ... ");
        Serial.println(receivedChars);
        newData = false;
    }
    
}
