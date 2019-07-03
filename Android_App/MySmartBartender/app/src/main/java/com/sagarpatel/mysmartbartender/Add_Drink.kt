package com.sagarpatel.mysmartbartender

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.res.TypedArrayUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner

//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class Add_Drink : Fragment() {

    lateinit var itemList:ArrayList<CharSequence>
    lateinit var ingredient_spinner_adapter:ArrayAdapter<CharSequence>
    lateinit var itemSpinner: Spinner
    internal lateinit var callback:onPumpConfigChangedListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Variables
        var view:View = inflater.inflate(R.layout.fragment_add__drink, container, false)
        var createButton: Button = view.findViewById(R.id.create_drink_button)
        itemList = ArrayList<CharSequence>()
        itemSpinner = view.findViewById(R.id.ingredient_pumps_spinner)
        var recipeListView:ListView = view.findViewById(R.id.Recipe_ListView)
        var recipeItems:Array<String> = arrayOf("No Recipe Yet")
        //Ingredient Spinner Code Here
        itemList.add("Not Set")
        itemList.add("Not Set")
        itemList.add("Not Set")
        itemList.add("Not Set")
        itemList.add("Not Set")
        itemList.add("Not Set")

        ingredient_spinner_adapter = ArrayAdapter<CharSequence>(activity!!.applicationContext,android.R.layout.simple_spinner_dropdown_item,itemList)
        itemSpinner.adapter = ingredient_spinner_adapter
        //callback.updatePumpConfig()
        //Recipe ListView Code Here
        var recipelist_Adapter:ArrayAdapter<String> = ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,recipeItems)
        recipeListView.adapter = recipelist_Adapter
        // Create Drink Button for when user creates a drink
        //Code for said Button goes here
        createButton.setOnClickListener{
            Snackbar.make(view.findViewById(R.id.create_drink_button), "Creating Drink still in Development", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
        // Inflate the layout for this fragment
        return view
    }


    interface onPumpConfigChangedListener{
        fun updatePumpConfig()// This Function will be called from Main Activity to update the pump config
    }


    fun setPumpConfigChangedListener(callback:onPumpConfigChangedListener){
        this.callback = callback
    }

    fun updateItemList(pumps:ArrayList<CharSequence>){// This Function updates the ItemList to most current Pump Configurations
        if(itemList != null) {
            itemList.clear()
            itemList = pumps
            ingredient_spinner_adapter = ArrayAdapter<CharSequence>(
                activity!!.applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                itemList
            )
            itemSpinner.adapter = ingredient_spinner_adapter
        }
    }



}