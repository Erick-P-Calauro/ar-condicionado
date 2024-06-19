// Create new File
bool writeFile(String path){
  File file = LittleFS.open(path, "w");
  if(!file){
      Serial.println("[File] => failed to open file for writing");
      return false;
  }

  file.print("");
  file.close();

  return true;
}

// Append text in a already created file
void appendFile(String path, String message){
    File file = LittleFS.open(path, "a");
    if(!file){
        Serial.println("[File] => failed to open file for appending");
        return;
    }
    
    if(file.print(message)){
        Serial.println("[File] => file append");
    } else {
        Serial.println("[File] => append failed");
    }
    
    file.close();
}

// Read a file text content
void readFile(String path){
    
    File file = LittleFS.open(path, "r");
    if(!file || file.isDirectory()){
        Serial.println("- failed to open file for reading");
        return;
    }

    Serial.println("\n"+path+" :");
    while(file.available()){
        Serial.printf("%c", file.read());
    }
    file.close();
}

String getWiFiList() {
  String list;
  String row;
  char c;
  
  File file = LittleFS.open("/root/wifiList.txt", "r");
  if(!file || file.isDirectory()){
      Serial.println("- failed to open file for reading");
      return "error404";
  }
  
  while(file.available()){
      char c = file.read();
      
      if(c == '\n') {
        row+="\n";
        list+=row;
        row="";
      }else {
        row +=c;
      }
  }
  file.close();
  return list;
}

