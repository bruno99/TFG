int in = 2; 
int out = 13;  
int state = HIGH;  
int r;           
int p = LOW;    
long time = 0;       
long debounce = 200;   
void setup()
{
  pinMode(in, INPUT);
  pinMode(out, OUTPUT);
}
void loop()
{
  r = digitalRead(in);
  if (r == HIGH && p == LOW && millis() - time > debounce) {
    if (state == HIGH)
      state = LOW;
    else 
      state = HIGH;
    time = millis();    
  }
  digitalWrite(out, state);
  p = r;
}
