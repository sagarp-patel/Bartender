package com.sagarpatel.mysmartbartender

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    // Make Drink Function. Sends a signal to the RPi to make a drink selected from the spinner object
    fun make_Drink(view: View){
        //Process
        //1st get the drink selected from spinner,
        //2nd send the signal corresponding to that to RPi
    }

}
