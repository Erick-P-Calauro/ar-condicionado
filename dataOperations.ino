uint64_t convertStringToU64(String str)   // char * preferred
{

  uint64_t val = 0;
  for (int i = 0; i < str.length(); i++)
  {
    char digit = toupper (str [i]);
    val *= 16;
    val += (digit >= '0' && digit <= '9') ? digit - '0' : digit - 'A' + 10;
  }
  return val;
}

int countNumbers(String str){
  uint16_t n = 1; //um número a mais que as vírgulas
  for (int i = 0; i < str.length(); i++)
    if(str[i] == ',') n++;
  return n;
}

void convertStringVector(String str, uint16_t rawData[])
{
  Serial.print(str);
  
  uint16_t val = 0;
  uint16_t j = 0;
  for (int i = 0; i < str.length(); i++){
    char c = toupper (str [i]);
    if(isdigit(c))
      val = val*10+(c - '0');
    if(str[i] == ','){ 
      rawData[j++] = val;
      Serial.print(" ");
      Serial.print(val);
      val = 0;
    }
  }
}
