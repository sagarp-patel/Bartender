package com.sagarpatel.mysmartbartender

import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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

    //Fragment Variables
    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction

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
        menu_array_list.add("No Drinks")
        menu_adapter = ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item,menu_array_list)
        drink_menu.adapter = menu_adapter
        pump_config_frag = Pump_Config()
        add_drink_frag = Add_Drink()
        fragmentManager = getSupportFragmentManager()

        closeMenu()
    }

    // Using this function open or close the FAB menu
    fun fab_onClick(view:View){
        if(isMenuOpen){
            closeMenu()
            isMenuOpen = false
        }else{
            openMenu()
            isMenuOpen = true
        }
    }

    //Open the FAB menu
    fun openMenu(){
        info_about_fab.animate().translationY(-getResources().getDimension(R.dimen.up_55))
        pump_config_fab.animate().translationY(-getResources().getDimension(R.dimen.up_105))
        add_drink_fab.animate().translationY(-getResources().getDimension(R.dimen.up_155))
    }

    // Close the FAB menu (FAB is Floating Action Button)
    fun closeMenu(){
        info_about_fab.animate().translationY(getResources().getDimension(R.dimen.up_55))
        pump_config_fab.animate().translationY(getResources().getDimension(R.dimen.up_105))
        add_drink_fab.animate().translationY(getResources().getDimension(R.dimen.up_155))
    }

    // Make Drink Function. Sends a signal to the RPi to make a drink selected from the spinner object
    fun pour_Drink(view: View) {
        Snackbar.make(view, "Function is still in development", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    // Add Drink Function when Add Drink FAB is clicked on
    fun add_Drink(view:View){
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,add_drink_frag)
        fragmentTransaction.commit()
    }

    // Change Pump Configuration Function when change pump config FAB is clicked on
    fun change_pump_Config(view:View){
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,pump_config_frag)
        fragmentTransaction.commit()
    }

}