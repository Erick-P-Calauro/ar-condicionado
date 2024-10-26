// Setting routes
void initServer() {

  // "API ROUTES"

  server.on("/command", HTTP_GET, [](AsyncWebServerRequest *request){
    
    Serial.println("[Request] => /command");
    String comando = request->getParam("comando")->value();
    String modelo = request->getParam("modelo")->value();
    Serial.print("[Request] => ");
    Serial.print(comando);
    if(comando.length() < 50){
      int len = comando.length();
      uint64_t code = convertStringToU64 (comando);
      
      // addLog(code);
      irsend.sendLG(code, len*4);
    } else {
      int n = countNumbers(comando);
      Serial.printf("Tamanho Raw: %d\n", n);
      uint16_t rawData[n];
      convertStringVector(comando, rawData);
      
      // addLog(rawData);
      irsend.sendRaw(rawData, n, 38000); //Valor correto é 38000 hz
    }

    request->send(200, "text/plain", WiFi.macAddress() );
  });
  
  // Starting server
  server.begin();
  Serial.print("\n[Server] Listening... !");
}
