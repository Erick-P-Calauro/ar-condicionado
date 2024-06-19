// Initializing an Acess Point
void initAP() {
      
 String APNAME = "ESP_NAME";
 String APPASSWORD = "ESP_PASSWORD";
  
 WiFi.mode(WIFI_AP);
 WiFi.softAP(APNAME, APPASSWORD);

 Serial.println("[WiFi] Routing WiFi on ESP_NAME, ESP_PASSWORD");
 Serial.print("[WiFi] Acces Point IP => ");
 Serial.print(WiFi.softAPIP());

 // Default State
 state = -1;
}
