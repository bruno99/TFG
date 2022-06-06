#include <Wire.h>
#include <SoftwareSerial.h>
SoftwareSerial BT(10,11); // 10 RX, 11 TX.

int ENB = 5;
int IN3 = 7;
int IN4 = 6;

char DataBluetooth = ' ';

void setup()  
{
  Serial.begin(9600); 
  BT.begin(9600); 
  Serial.println("");
  Serial.println("Iniciando Control ...");

  pinMode (ENB, OUTPUT);
 pinMode (IN3, OUTPUT);
 pinMode (IN4, OUTPUT);
}
 void Adelante ()
{

 digitalWrite (IN3, HIGH);
 digitalWrite (IN4, LOW);
 analogWrite (ENB, 255); //Velocidad motor B
}
void Parar ()
{

 digitalWrite (IN3, LOW);
 digitalWrite (IN4, LOW);
 analogWrite (ENB, 0); //
}
void loop()
{     
    // Si hay datos disponibles en el módulo bluetooth HC-05
    if(BT.available())
    {
      DataBluetooth = BT.read();
      Serial.print(DataBluetooth);

      if(DataBluetooth == 'b')
      {
        Adelante();
        BT.println("b"); 
        Serial.println("");       
      }
      
      if(DataBluetooth == 'a')
      {
        Parar();
        BT.println("a"); 
        Serial.println("");       
      }

    }

    if(Serial.available())
    {
      DataBluetooth = Serial.read();
      BT.write(DataBluetooth);
    } 
}
