// Setting routes
void initServer() {

  // Controllers (Return .html)
  
  // Server return main page if wifi is connected
  server.on("/", HTTP_GET, [](AsyncWebServerRequest *request){
    Serial.print("[Request] => /");
    
    if(WiFi.status() != WL_CONNECTED) {
      request->redirect("/wifi");
    }else {
      request->send(LittleFS, "/root/templates/index.html");
    }
    
  });

  // Server returns the wifi config page
  server.on("/wifi", HTTP_GET, [](AsyncWebServerRequest *request){
    Serial.println("[Request] => /wifi");
    
    request->send(LittleFS, "/root/templates/wifiForm.html");
  });

  // Server returns the wifi list page
  server.on("/getwifilistpage", HTTP_GET, [](AsyncWebServerRequest *request) {
    Serial.println("\n[Request] => /getwifilistpage");
    
    if(WiFi.status() != WL_CONNECTED) {
      request->redirect("/wifi");
    }else {
      request->send(LittleFS, "/root/templates/wifiList.html");
    }
  });

  // "API ROUTES"
  
  // Server defines wifiSSID and wifiPassword 
  server.on("/define", HTTP_GET, [](AsyncWebServerRequest *request){
    Serial.println("[Request] => /define");

    // Receiving SSID
    String ssidParam = request->getParam("ssid")->value();
    Serial.print("[Request] => /define | SSID = ");
    Serial.println(ssidParam);

    // Receiving Password
    String ssidPassword = request->getParam("password")->value();
    Serial.print("[Request] => /define | Password = ");
    Serial.println(ssidPassword);
    
    wifiSSID = ssidParam;
    wifiPassword = ssidPassword;

    String message = wifiSSID+","+wifiPassword+"\n";
    Serial.print("[WiFi] => "+message);
    appendFile("/root/wifiList.txt", message);
    
    state = 1;
    
    request->send(200, "text/plain", "OK");
  });

  // Send WiFiList to javascript
  server.on("/getwifilist", HTTP_GET, [](AsyncWebServerRequest *request){
    Serial.println("[Request] => /listWifi");

    String list = getWiFiList();
    Serial.print("\n[List] =>"+list);

    if(list != "error404") {
      request->send(200, "text/plain", list);
      Serial.print(list); 
    }else {
      request->send(200, "text/plain", "error404");
    }
  });

  server.on("/command", HTTP_GET, [](AsyncWebServerRequest *request){
    Serial.println("[Request] => /command");
    String comando = request->getParam("comando")->value();
    String modelo = request->getParam("modelo")->value();

    if(comando.length() < 50){
      int len = comando.length();
      uint64_t code = convertStringToU64 (comando);
      Serial.print("Code: ");
      Serial.println(code);
      //irsend.sendLG(code, len*4);
    } else {
      int n = countNumbers(comando);
      Serial.printf("Tamanho Raw: %d\n", n);
      uint16_t rawData[n];
      convertStringVector(comando, rawData);
      Serial.print("Deu bom");
      //irsend.sendRaw(rawData, n, 38000); //Valor correto é 38000 hz
    }

    request->send(200, "text/plain", "recebido");
  });

  server.on("/getwifiscan", HTTP_GET, [](AsyncWebServerRequest *request){
    Serial.println("[Request] => /getwifiscan");
    Serial.print("[Request] =>");
    Serial.println(availableNetworks);
    request->send(200, "text/plain", availableNetworks);
  });
  
  // Starting server
  server.begin();
  Serial.print("\n[Server] Listening... !");
}
