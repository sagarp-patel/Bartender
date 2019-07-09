package com.sagarpatel.mysmartbartender

import android.app.Dialog
import android.app.PendingIntent.getActivity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.DialogInterface
import android.content.Intent
import android.icu.lang.UProperty.NAME  //Incase you need to erase bluetooth thingy erase this one
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.util.Log
/*
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_pump_ingredient.*
import kotlinx.android.synthetic.main.fragment_add__drink.*
import kotlinx.android.synthetic.main.fragment_pump__config.*
import java.io.IOException*/

class MainActivity() : AppCompatActivity(), Pump_Ingredient.Pump_Ingredient_Listener,Add_Drink.onPumpConfigChangedListener {

    // Variables
    //(FAB is Floating Action Button)
    //Floating Action Buttons to Change Fragments
    lateinit var fab:View
    lateinit var pump_config_fab:View
    lateinit var add_drink_fab:View
    lateinit var info_about_fab:View
    var isMenuOpen:Boolean = false // Track FAB menu if open or close

    // Drink Menu Variables
    lateinit var drink_menu:Spinner
    lateinit var menu_adapter:ArrayAdapter<CharSequence>
    lateinit var menu_array_list:ArrayList<CharSequence>

    // Fragments
    lateinit var add_drink_frag:Add_Drink
    lateinit var pump_config_frag:Pump_Config
    lateinit var information_frag:Information

    //Fragment Variables
    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction

    // Drink ingredients
    lateinit var pump1_ingredient:String
    lateinit var pump2_ingredient:String
    lateinit var pump3_ingredient:String
    lateinit var pump4_ingredient:String
    lateinit var pump5_ingredient:String
    lateinit var pump6_ingredient:String

    // Drink List
    lateinit var drinkList:ArrayList<Drink>

    //Bluetooth
    var bluetoothAdapter: BluetoothAdapter? = null
    final var REQUEST_ENABLE_BT = 1
    lateinit var bluetoothComm:MyBluetoothService

    // Database
    lateinit var db:BartenderDatabase
    companion object{
        val EXTRA_ADDRESS: String = "Device_Address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing variables
        pump_config_fab = findViewById(R.id.pump_config_button)
        fab = findViewById(R.id.options_Button)
        add_drink_fab = findViewById(R.id.add_drink_button)
        info_about_fab = findViewById(R.id.info_button)
        drink_menu = findViewById(R.id.drink_menu_spinner)
        menu_array_list = ArrayList<CharSequence>()

        pump_config_frag = Pump_Config()
        add_drink_frag = Add_Drink()
        information_frag = Information()
        fragmentManager = getSupportFragmentManager()
        db = BartenderDatabase(this)
        pump1_ingredient = "Not Set"
        pump2_ingredient = "Not Set"
        pump3_ingredient = "Not Set"
        pump4_ingredient = "Not Set"
        pump5_ingredient = "Not Set"
        pump6_ingredient = "Not Set"
        drinkList = ArrayList<Drink>()
        closeMenu()
        println("Hello there ice to meet you")

        // Bluetooth Functionality
        bluetoothComm = MyBluetoothService()
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        bluetoothComm.pairedDevices()
        /*
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            return
        }else{

        if(!bluetoothAdapter!!.isEnabled){
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent,REQUEST_ENABLE_BT)
        }
        }
        var isRPiPaired:Boolean = false
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            if(deviceName == "raspberrypi"){
                isRPiPaired = true
            }
        }

        if(isRPiPaired){
            Log.e("RPiPaired","The Raspberyy Pi is paired \n\n\n")
            println("RPi is within the bonded list of devices")
        }*/

        // Database stuff
        val cursor = db.getAllDrinks()
        var drink_name_String = ""

        if(cursor != null){ // If we did receive drink data from cursor
            menu_array_list.clear()
            with(cursor){
                while(moveToNext()){
                    drink_name_String = getString(getColumnIndexOrThrow(BartenderDatabase.DRINK_NAME))
                    menu_array_list.add(drink_name_String)
                }
            }
            menu_adapter = ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item,menu_array_list)
            drink_menu.adapter = menu_adapter

        }
        if(menu_array_list.size == 0){
            menu_array_list.add("No Drinks")
            menu_adapter = ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item,menu_array_list)
            drink_menu.adapter = menu_adapter

        }
    }

    // Using this function open or close the FAB menu
    fun fab_onClick(view:View){
        if(isMenuOpen){
            closeMenu()
        }else{
            openMenu()
        }
    }

    //Open the FAB menu
    fun openMenu(){
        if(!isMenuOpen) {
            info_about_fab.animate().translationY(-getResources().getDimension(R.dimen.up_55))
            pump_config_fab.animate().translationY(-getResources().getDimension(R.dimen.up_105))
            add_drink_fab.animate().translationY(-getResources().getDimension(R.dimen.up_155))
            isMenuOpen = true
        }
    }

    // Close the FAB menu (FAB is Floating Action Button)
    fun closeMenu(){
        if(isMenuOpen) {
            info_about_fab.animate().translationY(getResources().getDimension(R.dimen.up_55))
            pump_config_fab.animate().translationY(getResources().getDimension(R.dimen.up_105))
            add_drink_fab.animate().translationY(getResources().getDimension(R.dimen.up_155))
            isMenuOpen = false
        }
    }

    // Make Drink Function. Sends a signal to the RPi to make a drink selected from the spinner object
    fun pour_Drink(view: View) {
        bluetoothComm.startConnectionToBartender(this)
        bluetoothComm.sendCommand("a")
        Snackbar.make(view, "Function is still in development", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    // Add Drink Function when Add Drink FAB is clicked on
    fun add_Drink(view:View){
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,add_drink_frag)
        fragmentTransaction.commit()
        //updatePumpConfig()
        closeMenu()
    }

    fun show_info(view:View){
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,information_frag)
        fragmentTransaction.commit()
        closeMenu()
    }

    // Change Pump Configuration Function when change pump config FAB is clicked on
    fun change_pump_Config(view:View){
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,pump_config_frag)
        fragmentTransaction.commit()
        closeMenu()
    }

    override fun getIngredientName(name: String, num:String) {
        when(num){
            "1"->pump1_ingredient=name
            "2"->pump2_ingredient=name
            "3"->pump3_ingredient=name
            "4"->pump4_ingredient=name
            "5"->pump5_ingredient=name
            "6"->pump6_ingredient=name
            else->print("It Did not Work!!!!")
        }
        Snackbar.make(findViewById(R.id.pump_config_button), "Pump is Set!!!", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    override fun updatePumpConfig() {// When the user updates the pump config change them out here
        //First get the correct Fragment
        val addDrinkFrag = add_drink_frag
        if(addDrinkFrag != null){ // Make sure its not null
            var updatedPumpConfig:ArrayList<CharSequence> = ArrayList<CharSequence>()
            updatedPumpConfig.add(pump1_ingredient)
            updatedPumpConfig.add(pump2_ingredient)
            updatedPumpConfig.add(pump3_ingredient)
            updatedPumpConfig.add(pump4_ingredient)
            updatedPumpConfig.add(pump5_ingredient)
            updatedPumpConfig.add(pump6_ingredient)
            addDrinkFrag.updateItemList(updatedPumpConfig) // Send the new Pump Configuration
        }
    }

    // Brings the Drink and its Recipe from Add Drink Fragment to here
    override fun sendDrinktoMain(drink:Drink) {
        if(menu_array_list[0] == "No Drinks") {
            menu_array_list[0] = drink.getName()
            menu_adapter = ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, menu_array_list)
        }else{
            menu_array_list.add(drink.getName())
            menu_adapter = ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item,menu_array_list)
        }
        db.addDrink(drink)
        drink_menu.adapter = menu_adapter
        drinkList.add(drink)
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is Add_Drink) {
            fragment.setPumpConfigChangedListener(this)
        }
    }



}