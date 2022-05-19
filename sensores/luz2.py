# SPDX-FileCopyrightText: 2020 Bryan Siepert, written for Adafruit Industries

# SPDX-License-Identifier: Unlicense
import time
import os
import board
import adafruit_bh1750

i2c = board.I2C()
sensor = adafruit_bh1750.BH1750(i2c)

while True:
    luz =  sensor.lux
    print("%.2f Lux" % sensor.lux)
    if luz < 50:
      os.system("mosquitto_pub -h 192.168.43.40 -u stw -P stweb -t /stw/stwAR/cmnd/POWER -m 1 ")
    else:
      os.system("mosquitto_pub -h 192.168.43.40 -u stw -P stweb -t /stw/stwAR/cmnd/POWER -m 0 ")
    time.sleep(1)

