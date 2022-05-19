import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BOARD)
GPIO.setup(29, GPIO.IN)
estado_anterior = False
estado_actual = False
time.sleep(4)

while True:
    time.sleep(1)
    estado_previo = estado_actual
    estado_actual = GPIO.input(29)
    print(estado_actual)
