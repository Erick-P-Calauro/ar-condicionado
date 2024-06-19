void pingServer() {
  WiFiClient wifiClient;
  
  Serial.print("[Ping] Trying to ping...");
  http.begin(wifiClient, urlServer);
  http.addHeader("content-type", "application/x-www-form-urlencoded");

  String mac = WiFi.macAddress();
  String ip = WiFi.localIP().toString();
  String body = "mac="+mac+"&ip="+ip;

  int responseCode = http.POST(body);
  String responseText = http.getString();

  Serial.print("[Ping] => response :"+responseText);
  http.end();
}

