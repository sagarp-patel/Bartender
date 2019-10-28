#Bartender
__author__ = "Sagar Patel"

import sys
import RPi.GPIO as GPIO
import json
import threading
import traceback
import time
import Bartender_bluetooth as blth
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
'''