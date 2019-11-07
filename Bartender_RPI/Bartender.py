#Bartender
__author__ = "Sagar Patel"

import sys
import RPi.GPIO as GPIO
import json
import threading
import traceback
import time
import Bartender_bluetooth as blth
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
        self.recipe = recipe

class Bartender:
    def __init__(self):
        print('Class in Development')
        self.pump_pins = [ 17, 27, 22, 23, 24, 25 ]
        self.flow_rate = 60.0/80.0
    
    def make(self,drink):
        print('Function still in Developpment')
        pumpThreads = []
        ingredients = drink.ingredients
        i = 0
        for item in ingredients:
            waitTime = item * self.flow_rate
            if item == 0:
                i+=1
                continue
            else:
                pump_i = threading.thread(target = self.startPump, args=(self.pump_pins[i],waitTime))
                pumpThreads.append(pump_i)
        #Start each of the pumps in a different thread
        for thread in pumpThreads:
            thread.start()
        # Wait for the threads to finish
        for thread in pumpThreads:
            thread.join()
    
    def startPump(self,pin,waitTime):
        GPIO.output(pin,GPIO.LOW) # Start Pump
        time.sleep(waitTime)  #Wait for it to finish
        GPIO.output(pin,GPIO.HIGH) # Stop the Pump
        
#Driver Program to run the code 
def driverProgram():
    print('In Development')
    bartender = Bartender()
    drink_queue = queue()
    bluetooth_thread = threading.thread(target = blth.listenForRecipe,  args= (drink_queue))
    bluetooth_thread.start()
    i = 0
    while True:
        i+=1
        if i > 100:
            break
        if len(drink_queue) > 0:
            drink = drink_queue.get()
            bartender.make(drink)