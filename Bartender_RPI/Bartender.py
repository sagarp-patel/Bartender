#Bartender
__author__ = "Sagar Patel"

import sys
import RPi.GPIO as GPIO
import json
from threading import Thread
import traceback
import time
from Bartender_bluetooth import Bartender_Bluetooth
import queue
'''
'''
#blth.listenForRecipe()

'''
Threads:
    UI
    Bluetooth Communication
    Making Drinks

UI will run on the touch screen, maybe using an arduino maybe not. 
UI:
    Menu -> Drinks Page -> Make Drinks
    Settings -> Clean -> Cleans Bartender
    Edit/Delete -> Edit Drinks
                -> Delete Drinks

Bluetooth Communication: 
        Runs in Background waiting for Pump Config from Android App
        Once it receives that it will call the make drink function and give it the pump data 
        
UI Thread <==> Main Thread with Queue <==> Bluetooth Thread 
    
'''
class Drinks:
    def __init__(self, recipe):
        self.ingredients = recipe

class Bartender:
    def __init__(self):
        print('Class in Development')
        self.pump_pins = [ 17, 27, 22, 23, 24, 25 ]
        self.flow_rate = 60.0/80.0
        self.make_next = True
    
    def make(self,drink):
        self.make_next = False
        print('Function still in Developpment')
        pumpThreads = []
        ingredients = drink.ingredients
        i = 0
        #We will create all the threads that we need to run in this forloop
        for item in ingredients:
            waitTime = item * self.flow_rate
            if item == 0:
                i+=1
                continue
            else:
                pump_i = Thread(target = self.startPump, args=(self.pump_pins[i],waitTime))
                pumpThreads.append(pump_i)
                i+=1
        #Start each of the pumps in a different thread
        for thread in pumpThreads:
            thread.start()
        # Wait for the threads to finish
        for thread in pumpThreads:
            thread.join()
        self.make_next = True
    
    def startPump(self,pin,waitTime):
        GPIO.output(pin,GPIO.LOW) # Start Pump
        time.sleep(waitTime)  #Wait for it to finish
        GPIO.output(pin,GPIO.HIGH) # Stop the Pump
        print(pin,end='Stopped\n')
        
#Driver Program to run the code 
def driverProgram():
    print('In Development')
    bartender = Bartender()
    GPIO.setmode(GPIO.BCM)     #programming the GPIO by BCM pin numbers. (like PIN40 as GPIO21)
    #GPIO.setwarnings(False)
    GPIO.setup(bartender.pump_pins,GPIO.OUT)  #initialize GPIO21 (LED) as an output Pin
    #GPIO.output(LED,0)
    blth = Bartender_Bluetooth()
    drink_queue = queue.Queue(maxsize=20)
    bluetooth_thread = Thread(target = blth.listenForRecipe, args=(drink_queue,))
    bluetooth_thread.start()
    i = 0
    while True:
        i+=1
        '''
        if i > 100:
            break
            '''
        if drink_queue.qsize() > 0 and bartender.make_next:
            drink = Drinks(drink_queue.get())
            bartender.make(drink)

driverProgram()