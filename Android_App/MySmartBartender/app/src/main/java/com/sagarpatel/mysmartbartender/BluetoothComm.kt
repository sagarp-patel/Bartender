package com.sagarpatel.mysmartbartender

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Log

import java.io.IOException
import java.util.UUID


class MyBluetoothService(){

    lateinit var pairedDevices:Set<BluetoothDevice>
    private lateinit var connectToBartender:ConnectToBartender
    val REQUEST_ENABLE_BLUETOOTH = 1
    lateinit var pairedRpi:BluetoothDevice

    companion object{
        var bluetoothAdapter: BluetoothAdapter? = null
        lateinit var address:String
        val device_UUID: UUID = UUID.fromString("b95deb0c-a1d7-11e9-b9b5-ffba58186197") // Still Need to Add the UUID from Raspberry Pi
        var app_bluetooth_socket:BluetoothSocket?=null
        lateinit var connectionProgress:ProgressDialog
        var isConnectedToBartender:Boolean = false
    }

    init {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        address = MainActivity.EXTRA_ADDRESS
    }

     fun sendCommand(input:String){
        if(app_bluetooth_socket != null){
            try{
                app_bluetooth_socket!!.outputStream.write(input.toByteArray())
            }catch(e:IOException){
                e.printStackTrace()
            }
        }
    }

    private fun disconnect(){
        if(app_bluetooth_socket !=null){
            try {
                app_bluetooth_socket!!.close()
                isConnectedToBartender = false
                app_bluetooth_socket = null
            }catch(e:IOException){
                e.printStackTrace()
            }
        }
    }

    fun pairedDevices(){
        var isRPiPaired:Boolean = false
        pairedDevices = bluetoothAdapter!!.bondedDevices
        pairedDevices!!.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            if(deviceName == "raspberrypi"){
                isRPiPaired = true
                address = deviceHardwareAddress
                pairedRpi = device
            }
        }

        if(isRPiPaired){
            Log.e("RPiPaired","The Raspberyy Pi is paired \n\n\n")
            println("RPi is within the bonded list of devices")
        }
    }

    fun startConnectionToBartender(context:Context){
        connectToBartender = ConnectToBartender(context)
        connectToBartender.execute()
    }
    
    // Uses AsyncTask to connect to a device
    private class ConnectToBartender(context: Context):AsyncTask<Void,Void,String>(){
        private var connectionResult: Boolean = true
        private val context:Context
        init{
            this.context = context
        }


        override fun onPreExecute() {
            super.onPreExecute()
            connectionProgress = ProgressDialog.show(context,"Connecting","please wait")
        }

        override fun doInBackground(vararg params: Void?): String {
            try{
                if(app_bluetooth_socket == null || !isConnectedToBartender){
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val Device:BluetoothDevice = bluetoothAdapter!!.getRemoteDevice(address)
                    app_bluetooth_socket = Device.createInsecureRfcommSocketToServiceRecord(device_UUID) // Connect to the Device we want to
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery() // Stop looking for other devices
                    // Now Connect to the Bartender RPi
                    app_bluetooth_socket!!.connect()
                }
            }catch(e:IOException){
                connectionResult = false
                e.printStackTrace()
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(!connectionResult){
                Log.i("BluetoothAsyncTask","Unable to Connect to the Raspberry Pi")
                println("Unable to Connect to Raspberry Pi")
            }else{
                isConnectedToBartender = true
            }
            connectionProgress.dismiss()
        }

    }
}
