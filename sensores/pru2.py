import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BCM)
GPIO.setup(14, GPIO.IN, GPIO.PUD_DOWN)
estado_anterior = False
estado_actual = False
time.sleep(4)

while True:
    time.sleep(1)
    estado_previo = estado_actual
    estado_actual = GPIO.input(14)
    print ("estado" + str(estado_actual))
    if estado_actual != estado_previo:
      nuevo_estado = "ALARMA ON" if estado_actual else "ALARMA OFF"
      print(nuevo_estado)
    time.sleep(1)
