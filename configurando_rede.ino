#include <ESPAsyncWebServer.h>
#include <ESP8266WiFi.h>
#include <LittleFS.h>
#include <ESP8266HTTPClient.h>
#include <IRremoteESP8266.h>
#include <IRsend.h>

// Global Variables
AsyncWebServer server(80);
HTTPClient http;

static String wifiSSID;
static String wifiPassword;
static String urlServer = "http://192.168.1.31:8080/ping";
static String availableNetworks = "";

// 0 => dirError => Restart LittleFS
// 1 => connectWifi => run tryConnectWifi and read wifiList
// 2 => initAP => Initialize an acess point
// 3 => ping => ping request to server
static int state;

void setup() { 
  Serial.begin(115200);
  wifiScan();
  
  Serial.print("\n[Wifi] Scanning complete...");
  // state => dirError/connectWifi
  initDir();
  Serial.print("\n[DIR] => Initialized");
  // state => ping/initAP;
  tryConnectWifi();
  Serial.print("\n[WiFi] => Initialized");
  // state => connectWifi/nothing 
  initServer();    
  Serial.print("\n[Server] => Initialized");
}

void loop() {
  switch(state) {
    case 0:
      Serial.print("\n[State] => 0");
      initDir();
      break;
    case 1:
      Serial.print("\n[State] => 1");
      tryConnectWifi();
      break;
    case 2:
      Serial.print("\n[State] => 2");
      initAP();
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
