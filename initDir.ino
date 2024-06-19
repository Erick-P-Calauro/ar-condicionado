// Initializing LittleFS and directories
/*
  |- root
    |- templates
      |- index.html
      |- wifiManager.html
    |- wifiList.txt
*/
void initDir(){
  LittleFS.begin();

  File file = LittleFS.open("/root", "r");
  if(!file || !file.isDirectory()) {
    Serial.print("[DIR] => Creating root directory...");
    bool result = LittleFS.mkdir("/root");
    
    if(!result) {
      state = 0;
      Serial.print("\n[DIR] => Root creation error...");
      return;
    }
  }

  file = LittleFS.open("/root/wifiList.txt", "r");
  if(!file) {
    bool result = writeFile("/root/wifiList.txt");
    if(!result){
      state = 0;
      Serial.print("\n[DIR] => wifiList creation error...");
      return;
    }
  }
  Serial.print("\n[DIR] => WiFi List directory was created");
  state = 1;  
}
