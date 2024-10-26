#include <ESPAsyncWebServer.h>
#include <ESP8266WiFi.h>
#include <LittleFS.h>
#include <ESP8266HTTPClient.h>
#include <IRremoteESP8266.h>
#include <IRsend.h>

// Global Variables
AsyncWebServer server(80);
HTTPClient http;

const uint16_t kIrLed = 4;
IRsend irsend(kIrLed);

static String wifiSSID = "JOCA&ROSE 2.4G";
static String wifiPassword = "Hsc0129Epc&";
static String urlServer = "http://192.168.0.159:8080/ping";
//static String availableNetworks = "";

// 0 => dirError => Restart LittleFS
// 1 => connectWifi => run tryConnectWifi and read wifiList
// 2 => initAP => Initialize an acess point
// 3 => ping => ping request to server
static int state;

void setup() { 
  Serial.begin(115200);
  irsend.begin();
  
  tryConnectWifi();
  Serial.print("\n[WiFi] => Initialized");
  
  initServer();    
  Serial.print("\n[Server] => Initialized");
}

void loop() {
  switch(state) {
    case 1:
      Serial.print("\n[State] => 1");
      tryConnectWifi();
      break;
    case 3:
      Serial.print("\n[State] => 3");
      pingServer();
      break;
    default:
      break;
  }
  delay(1000);
}
