// WiFi connection logic
void tryConnectWifi(){
  WiFi.mode(WIFI_STA);
  
  // Trying to connect
  WiFi.begin(wifiSSID, wifiPassword);
  Serial.print("[WiFi] Trying to connect on ");
  Serial.print(wifiSSID+"\n");

  // 10 Seconds to connect
  for(int x = 0; x < 1000; x++){
    Serial.print(".");
    delay(10);
  }
  Serial.print("\n");
    
  if(WiFi.status() != WL_CONNECTED) {
    Serial.print("Connection failed");
    state = 1;
  }else {
    state = 3;
  }

  Serial.print("\n[WiFi] Connected on ");
  Serial.print(WiFi.SSID()+" ");
  Serial.print(WiFi.localIP());
}

