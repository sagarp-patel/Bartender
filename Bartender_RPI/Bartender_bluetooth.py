import bluetooth
#from Bartender import Drinks
 
#GPIO.setmode(GPIO.BCM)     #programming the GPIO by BCM pin numbers. (like PIN40 as GPIO21)
#GPIO.setwarnings(False)
#GPIO.setup(LED,GPIO.OUT)  #initialize GPIO21 (LED) as an output Pin
#GPIO.output(LED,0)

class Bartender_Bluetooth():
    def __init__(self):
        self.LED = 21
        
    def listenForRecipe(self,drink_queue):
        server_socket=bluetooth.BluetoothSocket( bluetooth.RFCOMM )
        port = 1
        server_socket.bind(("",port))
        server_socket.listen(1)
        client_socket,address = server_socket.accept()
        print("Accepted connection from ",address)
        while 1:
            data = client_socket.recv(1024)
            print("Received: %s" % data)
            recipe = self.getPump(data.decode("utf-8"))
            drink = Drinks(recipe)
            drink_queue.put(drink)
            print(recipe)
            if (data == "0"):    #if '0' is sent from the Android App, turn OFF the LED
                print ("GPIO 21 LOW, LED OFF")
            if (data == "1"):    #if '1' is sent from the Android App, turn OFF the LED
                print ("GPIO 21 HIGH, LED ON")
            if (data == "q"):
                print ("Quit")
                break
        client_socket.close()
        server_socket.close()
        
    def getPump(self, recipeStr:str):
        recipe = []
        pumps = recipeStr.split("\n")
        for pump in pumps:
            amount = pump.split(":")
            if "{" in amount or "}" in amount:
                continue
        #print(amount)
            recipe.append(int(amount[1]))
        print(recipe)
        return recipe