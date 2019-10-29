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
        
#Driver Program to run the code 
def driverProgram():
    print('In Development')
    bartender = Bartender()
    drink_queue = queue()
    bluetooth_thread = threading.thread(target = blth.listenForRecipe,  args= (drink_queue))
    bluetooth_thread.start()
    while True:
        if len(drink_queue) > 0:
            print(drink_queue.get())