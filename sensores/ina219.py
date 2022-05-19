import time
import board
import busio
import adafruit_ina219
i2c = busio.I2C(board.SCL, board.SDA)
sensor = adafruit_ina219.INA219(i2c)
while True:
  print("Bus Voltage:    V" + str(sensor.bus_voltage))
  print("Shunt Voltage:  mV" + str(sensor.shunt_voltage / 1000))
  print("Current:        mA" + str(sensor.current) + "\n\n") 
  time.sleep(2)
