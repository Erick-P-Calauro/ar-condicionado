void pingServer() {
  WiFiClient wifiClient;
  
  Serial.print("[Ping] Trying to ping...");
  http.begin(wifiClient, urlServer);
  http.addHeader("content-type", "application/x-www-form-urlencoded");

  String macAddress = WiFi.macAddress();
  String ipv4 = WiFi.localIP().toString();
  String body = "mac="+macAddress+"&ip="+ipv4;

  int responseCode = http.POST(body);
  String responseText = http.getString();

  Serial.print("[Ping] => response :"+responseText);
  http.end();
}

