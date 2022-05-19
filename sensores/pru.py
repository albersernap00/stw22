import RPi.GPIO as GPIO
import time
 
GPIO.setmode(GPIO.BOARD)
GPIO.setwarnings(False)
PIR_PIN = 29
GPIO.setup(PIR_PIN, GPIO.IN)

print('Starting up the PIR Module (click on STOP to exit)')
time.sleep(1)
print ('Ready')

while True:
  if GPIO.input(PIR_PIN):
    print('Movimiento detectado')
    time.sleep(1)  
