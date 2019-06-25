package com.sagarpatel.mysmartbartender

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View

class MainActivity : AppCompatActivity() {

    lateinit var fab:View
    lateinit var pump_config_fab:View
    lateinit var add_drink_fab:View
    var isMenuOpen:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pump_config_fab = findViewById(R.id.pump_config_button)
        fab = findViewById(R.id.options_Button)
        add_drink_fab = findViewById(R.id.add_drink_button)
        //openMenu()
        closeMenu()
    }

    fun fab_onClick(view:View){
        if(isMenuOpen){
            closeMenu()
            isMenuOpen = false
        }else{
            openMenu()
            isMenuOpen = true
        }

    }

    fun openMenu(){
        pump_config_fab.animate().translationY(-getResources().getDimension(R.dimen.up_55))
        add_drink_fab.animate().translationY(-getResources().getDimension(R.dimen.up_105))
    }

    fun closeMenu(){
        pump_config_fab.animate().translationY(getResources().getDimension(R.dimen.up_105))
        add_drink_fab.animate().translationY(getResources().getDimension(R.dimen.up_105))
    }

    // Make Drink Function. Sends a signal to the RPi to make a drink selected from the spinner object
    fun pour_Drink(view: View) {
        //Process
        //1st get the drink selected from spinner,
        //2nd send the signal corresponding to that to RPi
        Snackbar.make(view, "Function is still in development", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()

    }

}