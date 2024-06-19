// WiFi connection logic
void tryConnectWifi(){
  WiFi.mode(WIFI_STA);
  File file = LittleFS.open("/root/wifiList.txt", "r");
  if(!file || file.isDirectory() || !file.available()){
      Serial.println("\n[File] => failed to open wifiList for reading.");
      state = 2;
      return;
  }

  // WiFi logic main loop
  while(WiFi.status() != WL_CONNECTED) {
    String wSSID;
    String wPassword;
    
    // 0 => SSID - 1 => Password - 2 => ',';
    int localState = 0;
    
    Serial.println("[WiFi] => Entering wifi list reading...");
    while(file.available()){
      char content = file.read();

      // End of file
      if(content == EOF) {
       state = 2;
       return;
      }
      
      // End of SSID
      if(content == ',') {
        localState = 2;
      }

      // End of line
      if(content == '\n') {
        break;
      }

      // Appending content 
      if(localState == 0) {
        wSSID += content;
      }else if(localState == 1) {
        wPassword += content; 
      }else {
        localState = 1;
      }
    }

    // Trying to connect
    WiFi.begin(wSSID, wPassword);
    Serial.print("[WiFi] Trying to connect on ");
    Serial.print(wSSID+"\n");

    // 10 Seconds to connect
    for(int x = 0; x < 1000; x++){
      Serial.print(".");
      delay(10);
    }
    Serial.print("\n");
    
    if(WiFi.status() != WL_CONNECTED) {
      Serial.print("Connection failed");
    }else {
      state = 3;
    }
  }
  file.close();

  Serial.print("\n[WiFi] Connected on ");
  Serial.print(WiFi.SSID()+" ");
  Serial.print(WiFi.localIP());
}

